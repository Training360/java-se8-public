
# Időzónák és téli-nyári időszámítás

Most az időzónák kezeléséről lesz szó. Az időzónákat a `ZoneId` osztály tartalmazza és ezeket le is kérdezhetjük a `getAvaibleZoneId()` metódussal. Az operációs rendszer által meghatározott időzóna a `ZoneId.systemDefault()` metódussal kérdezhető le (pl.:Europe/Prague).

Időzónát létre is hozhatunk az `of()` metódussal (ZoneId.of(”Europe/Budapest”)).


#### `ZoneDateTime` osztály

Az osztály tárolja a dátumot, időt és már az időzónát is. Létrehozni szintén az `of()` metódussal lehet: 
```java
ZoneDateTime.of(LocalDateTime, ZoneId)
```

 A `toString()` metódusa az oprendszer beállításoktól független. Átváltani másik időzónába a következő képpen lehet:
 
```java
ZonedDateTime localZonedDateTime = ZonedDateTime.now(); 
// 2017-04-05T13:46:26.372+02:00[Europe/Prague]
ZonedDateTime utcZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")); 
// 2017-04-05T11:46:26.372Z[UTC]
```

#### Nyári időszámítás
Nyáron a helyi időt egy órával későbbre állítjuk. Magyarországon 2017-ben március 26-án 2 óráról 3-ra állítottuk az órákat ennek köszönhetők a következő „furcsaságok”: 

```java
ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 26, 1, 59), ZoneId.of("Europe/Budapest"));
  // 2017-03-26T01:59+01:00[Europe/Budapest]
zonedDateTime = zonedDateTime.plus(Duration.ofMinutes(1));
  // 2017-03-26T03:00+02:00[Europe/Budapest]

ZonedDateTime start = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 26, 1, 00),  ZoneId.of("Europe/Budapest"));
  // 2017-03-26T01:00+01:00[Europe/Budapest]
ZonedDateTime end  ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 26, 6, 00),   ZoneId.of("Europe/Budapest"));
  // 2017-03-26T06:00+02:00[Europe/Budapest]
Duration duration = Duration.between(start, end); // PT4H
```

## Ellenőrző kérdések

* Mi a `ZoneId` szerepe?
* Mi a `ZonedDateTime` osztály szerepe?
* Hogyan kell időzónák között váltani?
* Hogyan kapcsolódnak az időzónák és a nyári időszámítás  (DST)?


## Gyakorlati feladat 1

Hosszabb repülőutat tervezünk, kontinenseken keresztül. Az indulás helyi ideje alapján
 akarjuk kiszámolni az érkezés pontos idejét a célállomáson, a repülőút időtartam alapján.

### Megvalósítási javaslatok

A számítás az időzónákon kívül vegye figyelembe a téli-nyári időszámításból (DST) adódó eltéréseket is.

publikus metódusok:

```java
 public FlightInfo(String dateString, String pattern, Locale locale, ZoneId zone)
 public ZonedDateTime getArrivalDateTime(ZoneId zone, int flightHours, int flightMinutes)
 public ZonedDateTime getDepartureDateTime()
```

### Hibakezelés

Ha a String formájában megadott paraméterek üresek (null vagy üres String),
 dobjon `IllegalArgumentException`-t a megfelelő szöveggel.
 Ha a `Locale` értéke null, dobjon `NullPointerException`-t a megfelelő szöveggel

### Megjegyzések

Történetesen az USA március közepén, Európa pedig március végén áll át. Ezért pl. egy március 14.-i indulás esetén
az indulás és érkezés között óra szerint 19 óra van (az eltérő DST átállás miatt az időeltolódás a zónák között 8 óra),
 míg egy március 1.-i indulás esetén 20 óra van (az időeltolódás a zónák között 9 óra). Tessék kipróbálni!

### Tippek

A pattern alapján végzett parszolást és az üres String ellenőrzést célszerű külön metódusokba szervezni.

[rating feedback=java-datedaylight-idozonak]

## Gyakorlati feladat 2

Ki kell számítanunk a pontos, órákban kifejezett időtartamot egy folyamatos (éjjel-nappali) munka dátum szerinti megkezdése
 és befejezése között.

### Megvalósítási javaslatok

publikus metódusok:

```java
 public WorkHoursCalculator(int startYear, Month startMonth, int startDay, int startHour, ZoneId zone)
 public long calculateHours(int year, Month month, int day, int hour)
 public ZonedDateTime getStartDateTime()
```

### Megjegyzések

Mivel a munka kezdeti és végdátuma közé eshet a tavaszi vagy őszi óraátállítás, ezt is vegyük figyelembe!

[rating feedback=java-datedaylight-idotartamszamitas]
