
# Duration

A `Duration` osztály egy idő szintű hosszt jelöl például (2 óra 30 perc). Létrehozása ugyanúgy működik mint a `Period` osztálynál. 

Fontos, hogy a `Duration` osztály alapból normalizálva kerül létrehozásra. 

#### Műveletek dátumokon
*	LocalDate, LocalTime és LocalDateTime plus() és minus() metódusaival

`LocalDateTime.now().add(Duration.parse("PT2M"));`
*	Duration addTo() és subtractFrom() metódusaival

`Duration.parse("PT2M").addTo(LocalDateTime.now());`

`(LocalDateTime) cast`

## Ellenőrző kérdések

* Milyen formában tárolja az adatokat a `Duration` objektum?
* Hogyan lehet `Duration` objektumot létrehozni?
* Hogyan lehet a `Duration` objektummal műveletet végezni?
* Mi jelent a `Duration`, hogy normalizált az adattárolása? Összevetve a `Period` adattárolásával?
* Hogyan lehet `LocalDate`, `LocalTime` és `LocalDateTime` értékekhez `Duration` értéket adni?

## Gyakorlati feladat 1

Programozási munka során rögzíteni kívánjuk, hogy az egyes funkciók kidolgozására mennyi időt fordítottunk.
Készítsünk egy `WorkHoursCalculator` osztályt, amely ezt megvalósítja, az egyes időtartamokat legyünk képesek
többféleképpen is bevinni, a beviteleket a rendszer összegzi, és lekérdezhetjük `Duration` formájában,
megjeleníthetjük String-ként valamint kiszámíthatjuk az összes ráfordított időt munkaórákban.

### Hibakezelés

Parszolás esetén a String nem lehet üres. Ezt kivételkezeléssel biztosítsuk,
 null vagy üres String esetén dobjon `IllegalArgumentException`-t a megfelelő szöveggel.
 Hasonlóképpen, ha a String paraméter nem üres, de a parszolás mégsem hajtható végre,
 dobjon `IllegalArgumentException`-t a megfelelő szöveggel.

### Megvalósítási javaslatok

A `Duration` objektum esetében a nap-óra-perc Stringgé alakításnál próbáljuk a `Duration` saját metódusait használni!

publikus metódusok:
```java
public void addWorkTime(Duration duration)
public void addWorkTime(int days, int hours, int minutes)
public void addWorkTime(String durationString)
public int calculateWorkHours()
public Duration getWorkDuration()
public String toString()
```

[rating feedback=java-dateduration-workhourscalculator]

## Gyakorlati feladat 2

A feladat inkább egy tanulmány, mint tipikus alkalmazás.
Tudjuk, hogy a String objektumok kétféle módon és helyen jöhetnek létre, ezek eltérő időigényűek is.
Próbáljuk ki ezt a gyakorlatban is, a `Duration` és az `Instant` objektumok segítségével.
Hozzuk létre a `StringCreationStudies` osztályt.
Két, azonos felépítésű metódus készül, az egyikben a heap-re,
a másikban a pool-ba gyártunk nagyszámú, azonos String objektumokat.

### Megvalósítási javaslatok

publikus metódusok:
```java
public long measureStringCreationTimeRequiredOnHeap(int objectsCount)
public long measureStringCreationTimeRequiredInPool(int objectsCount)
```

### Megjegyzés

Értékelhető különbség csak nagyszámú objektum esetében jön ki!
A létrehozási idők az oprendszer időosztása miatt nem reprodukálhatóak. A különbség ennek ellenére szignifikáns!
