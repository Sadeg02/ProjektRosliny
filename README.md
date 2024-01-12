# Projekt Systemów Wbudowanych - Monitoring Rośliny i Otoczenia

Projekt realizowany w ramach przedmiotu Systemy Wbudowane na Politechnice Poznańskiej. Celem projektu jest stworzenie kompleksowego systemu monitorowania otoczenia i kondycji rośliny za pomocą Raspberry Pi Zero.

## Opis Projektu

Projekt zakłada użycie Raspberry Pi Zero, do którego podłączone są dwa czujniki - jeden zbierający dane od rośliny, a drugi od otoczenia. Zebrane informacje są przesyłane do Raspberry Pi, gdzie są składowane w bazie danych SQLite. Na Raspberry Pi uruchomiony jest również serwer, który nasłuchuje na odpowiednim porcie. W razie próby połączenia klienta, serwer przekazuje odpowiednie dane.

## Funkcje

- Zbieranie danych od czujników rośliny i otoczenia.  
- Przechowywanie danych w bazie SQLite na Raspberry Pi.  
- Serwer na Raspberry Pi nasłuchujący, gotowy do komunikacji z klientami.  
- Aplikacja kliencka w Kotlinie na Androida.  

## Konfiguracja

TO DO  

## Struktura Projektu

TO DO  
## Instrukcje dla Aplikacji Klienckiej

1. Uruchom aplikację na urządzeniu z Androidem.  
2. Połącz się z siecią WiFi, na której działa Raspberry Pi.  
3. Kliknij przycisk "Pobierz Dane" i sprawdź wyniki dotyczące otoczenia i kondycji rośliny.

## Autorzy
TO DO
