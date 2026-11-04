# Food Delivery – Lab 2

Spring Boot aplikacija za restorane, jela i narudzbe.

## Clanovi
- Eman Palavra, 518
- Emin Nusinovic, 489

## Pokretanje
Za rad kroz IntelliJ otvoriti folder:
Food Delivery_ Restaurant & Delivery/Food-Delivery-Restaurant-Delivery
i postaviti Project SDK i Maven Runner JRE na JDK 25. Pokrenuti Application.java.
Adresa aplikacije je http://localhost:8080.

Iz foldera s pom.xml moguce je koristiti:
- .\mvnw.cmd spring-boot:run
- .\mvnw.cmd test
- .\mvnw.cmd clean package

Izvrsni JAR: java -jar target/Food-Delivery-Restaurant-Delivery-0.0.1-SNAPSHOT.jar
MySQL servis MySQL80 mora biti pokrenut. Prvi Maven download zahtijeva internet.

## MySQL konfiguracija
- MySQL Server 8.0, lokalni servis MySQL80.
- Host: 127.0.0.1
- Port: 3306
- Username: root
- Password: root
- Schema: food_delivery_db
- spring.jpa.hibernate.ddl-auto=update

application.properties sadrzi JDBC konekciju i automatsko kreiranje baze ako ne postoji.
Hibernate kreira i azurira tabele. Nakon toga data.sql dodaje tri restorana i sest jela.
INSERT upiti provjeravaju postojece podatke: ponovno pokretanje ne duplira pocetne podatke
i ne brise narudzbe. Sve se trajno cuva u MySQL-u; nema DemoData skladista u memoriji.
H2 se koristi iskljucivo u testovima.

## MySQL Workbench
1. Otvoriti instalirani MySQL Workbench iz Start menija.
2. Kreirati novu konekciju, npr. Food Delivery Lab 2.
3. Izabrati Standard TCP/IP, hostname 127.0.0.1, port 3306, username root.
4. Unijeti lozinku root i koristiti Test Connection.
5. Otvoriti konekciju i osvjeziti Schemas. Izabrati food_delivery_db.
6. Po potrebi otvoriti mysql-workbench.sql iz foldera s pom.xml i izvrsiti ga.

mysql-workbench.sql moze kreirati bazu, sve tri tabele i pocetne podatke i prije pokretanja
aplikacije. Moze se ponovo izvrsiti bez dupliranja primjera; ne sadrzi DROP ni brisanje narudzbi.
Aplikacija i Workbench koriste istu bazu. Izmjene sacuvane/commitovane u Workbenchu vide se
nakon osvjezavanja stranice ili novog REST zahtjeva. Kod izmjene quantity direktno u SQL-u
potrebno je azurirati i total; aplikacija ih zajedno racuna pri spremanju forme.

Primjeri pregleda:
SELECT * FROM restaurant;
SELECT * FROM meal;
SELECT * FROM food_orders;

Workbench je klijent; MySQL Server mora raditi i kada je Workbench zatvoren.

## Modeli i relacije
Restaurant ima id, name, address, phone i listu meals.
Meal ima id, name, description, price i restaurant.
FoodOrder je novi model: id, customerName, deliveryAddress, quantity, total i meal.

Restaurant -> Meal je OneToMany, a Meal -> Restaurant je ManyToOne.
FoodOrder -> Meal je ManyToOne: jedna narudzba sadrzi jedno jelo i kolicinu,
a isto jelo moze biti u vise narudzbi. Restoran narudzbe dobija se preko meal.restaurant.
OrderForm je pomocna klasa za validaciju unosa, nije cetvrti entitet.
Tabele: restaurant, meal, food_orders; veze: restaurant_id i meal_id.
Novcani iznosi cuvaju se kao DECIMAL(10,2).

## Kontroleri i funkcionalnosti
AppController prikazuje restorane i jela iz baze i provjerava da izabrano jelo pripada restoranu.
Zadrzane su GET rute /restaurants (/a), /meals (/b) i /restaurants/action/{id} (/a/action/{id}).
Akcija Naruci vodi na novu formu narudzbe. Stara POST akcija zamijenjena je POST /orders/add.

OrderController vraca Thymeleaf stranice:
- GET /orders: lista narudzbi, jela, restorana i ukupnih cijena.
- GET /orders/add: nova forma; opcioni mealId unaprijed bira jelo.
- POST /orders/add: validacija, spremanje i redirect na listu.
- GET /orders/update/{id}: forma s trenutnim vrijednostima.
- POST /orders/update/{id}: izmjena postojeceg zapisa uz isti ID.

OrderRestController vraca JSON:
- GET /api/orders: sve narudzbe.
- GET /api/orders/{id}: jedna narudzba ili HTTP 404.

OrderService ucitava izabrano jelo iz baze i racuna total = price * quantity.
Kolicina je cijeli broj 1–20; kupac, adresa i jelo su obavezni.
ID i ukupna cijena nisu dio korisnickog unosa. Neispravna forma prikazuje greske.
JpaRepository interfejsi obezbjedjuju pristup bazi. JsonIgnore sprjecava kruzni JSON.

## Provjera kroz aplikaciju
1. Otvoriti http://localhost:8080 i izabrati restoran, meni i Naruci.
2. Unijeti kupca, adresu i kolicinu. Margherita x 2 iznosi 20.00 KM.
3. Na listi Narudzbe kliknuti Izmijeni, promijeniti kolicinu i sacuvati.
4. Provjeriti JSON na http://localhost:8080/api/orders.
5. U Workbenchu izvrsiti SELECT * FROM food_orders; za isti zapis.
6. Restartovati aplikaciju: narudzba ostaje sacuvana.

## Testiranje
Testovi se pokrecu komandom `.\mvnw.cmd test` iz foldera s pom.xml.
Koriste odvojenu H2 bazu i obuhvataju prikaze, dodavanje, izmjenu, validaciju i REST rute.

