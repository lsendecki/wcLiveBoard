# wcLiveBoard
Live Football World Cup Score Board

The project is a utility library for storing information about all the ongoing football 
matches during a World Cup together with their results.

MAIN ASSUMPTIONS
- Teams are identified by a unique name. The solution uses names of countries that a particular 
  team represents.
- A central object in the library is ScoreboardService class.
- The creation of a football game is equivalent to the addition of following information:
    - a team of host (referred to as home team)
    - a team of visitors (referred to as away team)
    - initial match's outcome set to 0:0
    - date and time of when a match is to be started

There is a simulation of data persistence for the purpose of this solution. We put the required 
information into an object kept in memory during the execution of the program. A structure of a mutable 
Set container is used under the hood. Thanks to that we ensure set-theoretic uniqueness of team gatherings 
that are put into memory.

All the stored elements are of OngoingMatch type. They can mimic DB entities as well, however we deliberately 
avoid the existence of an individual Scoreboard object. Instead, there is a component-alike service class 
called ScoreboardService which provides an interface for all the expected operations. 

Date and time of the beginning of a match can easily be generated automatically provided that (assuming)
the match is added to the scoreboard at the same time when a game is started on the pitch.
This solution lets you pass that time manually but there is a default parameter for that as well.

The solutions is not Thread-safe. It can be achieved with the own implementation of synchronization feature, 
if needed, with the use of coroutines and mutex (binary semaphore). One can also think of either using 
ConcurrentHashMap directly from JDK or some dedicated concurrency library.

The solution was enhanced in terms of performance. the function that generates summary runs in O(1) time. 
It just returns already a properly ordered collection of matches. We insert elements (OngoingMatch[s]) 
in a way that we keep the required order of items once an element gets inserted. A tree-based data structure
is used to accomplish the thing.

------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------

Biblioteka narzedziowa do przechowywania informacji o wszystkich aktualnych 
meczach druzyn na mundialu, wraz z bieżącymi wynikami.

ZALOZENIA:
- Druzyne identyfikujemy przez unikalna nazwę. W rozwiazaniu identyfikatorem druzyny jest nazwa państwa 
reprezentowanego przez druzyne

- Obiektem głównym biblioteki jest klasa ScoreboardService

- Utworzenie rozgrywki jest rownoznaczne z dodaniem nastepujacych informacji:
    - druzyna gospodarzy
    - druzyna gosci
    - poczatkowy rezultat ustawiony jako 0:0
    - data i czas rozpoczęcia gry.

W rozwiązaniu wystepuje symulacja utrwalania/zapisu danych poprzez zapis do obiektu trzymanego w pamięci. 
Pod spodem jest struktura mutowalnego zbioru (Set) elementów. Dzieki temu teoretycznie zapewniamy unikalnosc 
zapamiętywanych spotkań miedzy druzynami. 

Przechowywane elementy są typu OngoingMatch. Mogą one symulować encje/rekordy
w DB. Jest też osobny typ Scoreboard, ktorego interfejs implementuje operacje 
opisane w wymaganiach. Użycie obieku jest przedstawione w klasie Main.

- Data i czas rozpoczecia gry moga byc generowane przez algorytm, przy zalozeniu, że chwila dodania meczu 
do ScoreBoard'u jest taka sama, jak chwila rozpoczecia gry. 
To rozwiazanie pozwala przekazac LocalDateTime jako parametr, ale to chyba warto zmienic.

- ROZWIAZANIE AKTUALNE NIE JEST thread-safe - mozna ulepszyc przez wlasna implementacje synchronizacji, 
korutyny (mutex), albo uzycie thread-safe mapy z JDK (ConcurrentHashMap) lub uzycie biblioteki.

- W rozwiazaniu nie ma sortowania, które zostało zastąpione wstawianiem do drzewa binarnego. 
- W ten sposób zwracamy uporzadkowana juz strukture. Użyta została klasa TreeSet, która przechowuje 
porównywalne elementy w porządku drzewiastym.
 






