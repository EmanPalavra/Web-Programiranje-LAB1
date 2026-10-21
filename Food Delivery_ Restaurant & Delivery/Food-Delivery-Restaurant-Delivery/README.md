Food Delivery

Lab vježba 1 – Web programiranje

Student: Eman Palavra 518
Student: Emin Nušinović 489

Opis aplikacije

Food Delivery je jednostavna Spring Boot MVC aplikacija za pregled restorana i njihovih dostava. Korisnik može dodati novu dostavu odabranom restoranu.

Modeli

Restaurant – id, name, address, phone, deliveries

Delivery – id, customer, address, status, restaurant

Relacija je 1. Jedan restoran može imati više dostava, a svaka dostava pripada jednom restoranu.

Logički tok izrade

Kreiran je Spring Boot projekat.

Dodane su Spring MVC i Thymeleaf zavisnosti.

Napravljene su klase Restaurant i Delivery.

U DemoData klasi uneseni su početni podaci bez baze podataka.

Kreiran je AppController sa GET i POST rutama.

Napravljene su tri Thymeleaf HTML stranice.

Dodana je forma za unos nove dostave.

Podjela rada

Eman: Restaurant model, ruta i prikaz restorana.

Emin: Delivery model, ruta i prikaz dostava.

Zajednički: DemoData, akcija dodavanja dostave i action.html.

Korišteni izvori

Spring Boot: https://spring.io/projects/spring-boot

Thymeleaf: https://www.thymeleaf.org/documentation.html

Spring MVC: https://docs.spring.io/spring-framework/reference/web/webmvc.html