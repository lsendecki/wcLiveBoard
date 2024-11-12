# wcLiveBoard
Live Football World Cup Score Board

Biblioteka narzedziowa do przechowywania informacji o wszystkich aktualnych 
meczach druzyn na mundialu, wraz z bieżącymi wynikami.

ZALOZENIA:
- Druzyne identyfikujemy przez unikalna nazwę. W rozwiazaniu identyfikatorem druzyny jest nazwa państwa 
reprezentowanego przez druzyne

- Obiektem głównym biblioteki jest klasa Scoreboard.

- Utworzenie rozgrywki jest rownoznaczne z dodaniem informacji do obiektu ScoreBoard:
    - druzyna gospodarzy
    - druzyna gosci
    - poczatkowy rezultat ustawiony jako 0:0
    - data i czas rozpoczęcia gry.

- Data i czas rozpoczecia gry moga byc generowane przez algorytm, przy zalozeniu, że chwila dodania meczu 
do ScoreBoard'u jest taka sama, jak chwila rozpoczecia gry. 
To rozwiazanie pozwala przekazac LocalDateTime jako parametr, ale to chyba warto zmienic.

- ROZWIAZANIE AKTUALNE NIE JEST thread-safe - mozna ulepszyc przez wlasna implementacje synchronizacji, 
korutyny (mutex), albo uzycie thread-safe mapy z JDK (ConcurrentHashMap).
lub uzucie biblioteki

- MOZNA ULEPSZYC ROZWIAZANIE pod katem efektywnosci funkcji generujacej summary. Zamiast sortowac, mozna zwrocic 
uporzadkowana juz strukture. Mozna tutaj zapropnowac kolejke priorytetowa (PriorityQueue) - 
w sumie mogę to jeszcze zrobić. Alternatywnie mozna zaimplementowac kopiec (na tablicy lub drzewie binarnym). 
Wtedy wstawianie kolejnych OngoingMatch-ow będzie zawsze w sposób uporządkowany.
 






