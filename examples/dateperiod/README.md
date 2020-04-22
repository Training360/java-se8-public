# Period

A `Period` osztály egy dátum szintű hosszt jelöl (pl.: 2 év 6 hónap). Az `of()` különböző variánsaival lehet létrehozni különböző periodusokat. 
*	ofYear(1)
*	of(1,0,7) –egy év hét nap

A `Period` két dátum különbségeként is létrehozható ehhez a `between()` metódust kell használni és két `LocalDate` példányt átadni paraméterül.

A `Period`-ban definiálva van a `toString()` metódus, mely a következő formátumban adja vissza a periódust: P1Y2M3D azaz mutatja, hogy periódusról van szó és a hossza 1 év 2 hónap 3 nap. 

A periódus alapértelmezetten nem normalizált azaz megadható 15 hónap hosszúságú periódus, ennek kiküszöbölésére használható a `normalized()` metódus amely 12 hónapot egy évvé alakít. A `Period` osztály immutable és szálbiztos


#### Műveletek
*	LocalDate és LocalDateTime plus() és minus() metódusaival

`LocalDateTime.now().plus(Period.ofYears(1))`
*	Period addTo() és subtractFrom() metódusaival

	`LocalDateTime localDateTime = (LocalDateTime) Period.ofYears(1).addTo(LocalDateTime.now());`
A `Period` osztály műveletei a `plusXxx()` és a `minusXxx()`, de figyeljünk arra, hogy az osztály immutable. A `plus()` művelettel akár másik `Period` példányt is adhatunk hozzá. 

## Ellenőrző kérdések

* Hogyan hozható létre `Period` objektum? Parse esetén milyen a String szerkezete?
* Milyen a `Period` objektum szerkezete?
* Mi történik a `normalized()` metódus meghívása esetén?
* Mi történik két period "összeadása" esetén?
* Hogyan lehet egy period értékét csökkenteni vagy növelni?
* Hogyan lehet egy `LocalDate` vagy `LocalDateTime` példányt period alapján módosítani?

## Gyakorlati feladat

Nyugdíjszámításhoz szükségünk van egy olyan osztályra, amely segítségével összegezni tudjuk a
nyugdíj jogosultságot adó időtartamokat, egy-egy dátum adta intervallumot módosítani tudunk
(pl. jogosultságot nem szerző napokkal), és egyéb műveleteket is végezhetünk.

### Hibakezelés

A Stringként megadott paraméterek nem lehetnek null vagy üres String értékek.
Ezek esetében dobjon a rendszer `IllegalArgumentException`-t, a megfelelő tájékoztató szöveggel.
A dátum paraméter nem lehet null érték, a megfelelő metódus dobjon `NullPointerException`-t.

### Megvalósítási javaslatok

A `Period` objektum normalizálható, de ez csak az év és hónap mezők "átváltását" biztosítja.
Készítsünk egy teljes mértékben normalizáló metódust, ami a nap és hónap értékek közötti
konverziót is biztosítja 1 hónap = 30 nap átváltás mellett (`Period fullyNormalized(Period period)`).

publikus metódusok:
```java
public void addEmploymentPeriod(Period period)
public Period sumEmploymentPeriods()
public Period modifyByDays(Period period, int days)
public Period getPeriodBetweenDates(LocalDate fromDate, LocalDate toDate)
public Period getPeriodBetweenDates(String fromDate, String toDate, String pattern)
public int calculateTotalDays(Period period)
```

### Tippek

Ahol a visszatérési érték `Period`, mindig a teljes mértékben normalizált objektumot adjuk vissza!
(Ennek létjogosultsága persze vitatható, de legyünk következetesek!)
Nézzük át a dokumentációban a parszolható period String szerkezetét!
A paraméter stringek vizsgálatához készítsünk egy `boolean isEmpty(String str)` metódust!
Az összegyűjtött időtartamokat egy kollekcióban célszerű tárolni, és azt lehet szükség esetén összegezni.

[rating feedback=java-dateperiod-nyugdijszamitas]
