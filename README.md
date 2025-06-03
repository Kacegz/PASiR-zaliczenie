# Zaliczenie - Aplikacja do zarządzania bazą herbat 

## Opis projektu
Backend aplikacji w spring boot, umożliwiający użytkownikom zarządzanie herbatami oraz ich ocenami. 

Aplikacja wykorzystuje JWT do uwierzytelniania i autoryzacji, a dane przechowywane są w bazie danych MongoDB.

## Funkcjonalności
- **Rejestracja i logowanie użytkowników**.
- **Zarządzanie herbatami**:
    - Przeglądanie listy herbat.
    - Pobieranie szczegółów wybranej herbaty.
    - Dodawanie, edytowanie i usuwanie herbat (dla zalogowanych użytkowników).
- **System oceniania**:
    - Dodawanie ocen do herbat.
    - Pobieranie listy ocen dla konkretnej herbaty.
    - Sprawdzanie, czy użytkownik ocenił daną herbatę.

## Technologie
- **Język**: Java
- **Framework**: Spring Boot
- **Baza danych**: MongoDB
- **Zarządzanie zależnościami**: Maven
- **Bezpieczeństwo**: Spring Security, JWT

## Endpointy
### Autentykacja
- **POST** `/api/register` - Rejestracja nowego użytkownika.
- **POST** `/api/login` - Logowanie użytkownika.

### Zarządzanie herbatami
- **GET** `/api/teas` - Pobieranie listy wszystkich herbat.
- **GET** `/api/teas/{id}` - Pobieranie szczegółów wybranej herbaty.
- **POST** `/api/teas` - Dodawanie nowej herbaty (wymaga JWT).
- **PUT** `/api/teas/{id}` - Edytowanie herbaty (wymaga JWT).
- **DELETE** `/api/teas/{id}` - Usuwanie herbaty (wymaga JWT).

### Oceny
- **POST** `/api/teas/{id}/rate` - Dodawanie oceny do herbaty (wymaga JWT).
- **GET** `/api/teas/{id}/ratings` - Pobieranie listy ocen dla herbaty.
- **GET** `/api/teas/{id}/israted` - Sprawdzanie, czy użytkownik ocenił herbatę (wymaga JWT).
## Przykładowe zapytanie
### Dodawanie herbaty
```json
POST /api/teas
Authorization: Bearer {token}
Body:
{
  "name": "Yunnan Gold",
  "description": "Czarna herbata z prowincji Yunnan o miodowym aromacie",
  "type": "Czarna",
  "origin": "Chiny"
}
```