# Projekt: Monitoring rośliny i otoczenia (Raspberry Pi + Android)

Kompleksowy projekt realizowany w ramach kursu "Systemy Wbudowane" na Politechnice Poznańskiej.

Krótko: system składa się z części sprzętowej (Raspberry Pi Zero z czujnikami, przechowywanie w SQLite i serwer TCP/HTTP) oraz aplikacji klienckiej na Androida napisaną w Kotlinie, która łączy się z Raspberry Pi i prezentuje dane.

## Najważniejsze funkcje

- Odczyt pomiarów z czujników rośliny (np. wilgotność gleby) i z otoczenia (np. temperatura, wilgotność powietrza). 
- Przechowywanie historycznych pomiarów w pliku bazy danych SQLite na Raspberry Pi.
- Prosty serwer uruchomiony na Raspberry Pi udostępniający dane klientowi.
- Aplikacja Android (Kotlin) do pobierania i wizualizacji danych (widoki tygodniowe/miesięczne).

## Zawartość repozytorium

Top-level:

- `app/` – projekt Android (moduł aplikacji). Zawiera kod źródłowy Kotlin, layouty i zasoby.
- `gradle/`, `gradlew`, `build.gradle.kts`, `settings.gradle.kts` – konfiguracja Gradle dla projektu Android.

Wewnątrz `app/` najważniejsze pliki:

- `src/main/java/com/example/roslinki/` – kod aplikacji Android: `MainActivity.kt`, `MonthActivity.kt`, `WeekActivity.kt`, `MonthChart.kt`, `funkcje.kt`.
- `src/main/res/layout/` – layouty ekranów (`activity_main.xml`, `activity_week.xml`, `activity_month.xml`, `activity_month_chart.xml`, `list_item_layout.xml`).
- `src/main/res/values/` – zasoby (napisy, kolory, motywy).

Uwagi: W repo nie ma (w tej chwili) kodu Raspberry Pi (skrypty Python/C lub schematy podłączeń). Dokument opisuje komponenty Pi jako część projektu — jeśli masz kod Pi w innym miejscu, warto go dodać do repo lub wskazać link.

## Wymagania

- Java JDK 11+ (zalecane zgodne z konfiguracją Android Gradle). 
- Android Studio (preferowane) lub Gradle CLI do budowania aplikacji.
- Dla Raspberry Pi: Raspbian/ Raspberry Pi OS, Python 3, biblioteki do odczytu z czujników (opcjonalnie). 

## Jak uruchomić aplikację Android (lokalnie)

1. Otwórz projekt w Android Studio: otwórz katalog `ProjektRosliny`.
2. Pozwól Android Studio zsynchronizować Gradle i pobrać zależności.
3. Podłącz urządzenie z Androidem lub uruchom emulator.
4. Wybierz moduł `app` i uruchom aplikację.

Dodatkowe sprawdzenia (z terminala):

 - Synchronizacja i czyszczenie gradle:

```bash
./gradlew clean
./gradlew assembleDebug
```

 - Uruchom testy jednostkowe (jeśli są):

```bash
./gradlew test
```

Jeśli napotkasz problemy z wersjami Gradle/AGP, sprawdź pliki `build.gradle.kts` i `gradle/wrapper/gradle-wrapper.properties`.

## Jak aplikacja komunikuje się z Raspberry Pi (ogólnie)

1. Raspberry Pi zbiera dane z czujników i zapisuje je w SQLite.
2. Na Pi działa prosty serwer (TCP lub HTTP) nasłuchujący na określonym porcie.
3. Aplikacja Android łączy się z adresem IP Pi (tego samego LAN), wysyła zapytanie i otrzymuje dane (format zależny od implementacji serwera — JSON, CSV itp.).

Jeżeli serwer Pi jest jeszcze niezaimplementowany lub znajduje się poza repozytorium, dodaj tutaj krótkie instrukcje uruchomienia serwera i format danych.

## Struktura i funkcje aplikacji Android

- `MainActivity` – główny ekran, prawdopodobnie zawierający przycisk pobierania danych i listę ostatnich pomiarów.
- `WeekActivity` / `MonthActivity` – ekrany z widokami okresowymi (tydzień / miesiąc).
- `MonthChart` – klasa odpowiedzialna za generowanie wykresu miesięcznego.
- `funkcje.kt` – pomocnicze funkcje używane w aplikacji (np. parsowanie danych, utility).

Sprawdź implementację w `app/src/main/java/com/example/roslinki/` aby dopasować instrukcje integracji z serwerem Pi (np. nazwy endpointów, porty).

## Troubleshooting (częste problemy)

- Problem: Gradle nie synchronizuje się / brak zależności
	- Upewnij się, że masz stabilne połączenie z internetem i kompatybilną wersję JDK. W Android Studio wybierz opcję "Sync Project with Gradle Files".
- Problem: Aplikacja nie łączy się z Raspberry Pi
	- Upewnij się, że telefon i Pi są w tej samej sieci lokalnej.
	- Sprawdź adres IP Pi (`hostname -I` na Pi) i port serwera.
	- Sprawdź zaporę (firewall) na Pi.
