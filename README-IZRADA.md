# Logicki tok izrade i izvori

1. Uzet je u obzir primjer asistentice Web-Programiranje-Vj2 (Battle Arena).
2. Zadrzani su Spring Boot 3.5.6, Java 25, Spring MVC i Thymeleaf, kao u primjeru.
3. Model Delivery zamijenjen je modelom Meal da odgovara zadanoj temi.
4. Restaurant cuva listu jela, a Meal referencu na restoran: relacija 1:N bez JPA i baze.
5. DemoData je Spring komponenta s pocetnim podacima, privatnim listama i metodama pretrage/dodavanja.
6. AppController prima zahtjev, uzima podatke iz DemoData, stavlja ih u Model i vraca naziv Thymeleaf stranice.
7. Tri stranice prikazuju restorane, meni i narucivanje s potvrdom. Meni se filtrira po restoranu.
8. POST provjerava da jelo pripada restoranu i da je kolicina cijeli broj 1-20. Cijena se uzima iz DemoData, a ne iz forme.
9. Ukupna cijena racuna se pomocu BigDecimal. POST preusmjerava na GET uz flash poruku s potvrdom; neispravna kolicina vraca formu s greskom.
10. Dodane su opcije filtriranja menija, narucivanja, ukupne cijene i neispravnih zahtjeva.
11. Dodani su CSS i screenshot.

## Izvori
Direktno pregledani materijali: Web-Programiranje-Vj2 zadatak od Asistentice


Internet izvori navedeni:
- Spring Boot: https://spring.io/projects/spring-boot
- Thymeleaf: https://www.thymeleaf.org/documentation.html
- Spring MVC: https://docs.spring.io/spring-framework/reference/web/webmvc.html

Maven je preuzimao potrebne zavisnosti.


