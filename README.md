# PFG_Actividad2 — Cajero Web (Spring Boot + Thymeleaf + MySQL)

Aplicación web de cajero que permite **ingresar**, **extraer**, **transferir** y **consultar movimientos** de una cuenta.  
UI moderna con **Bootstrap 5** y vistas **Thymeleaf**.

## ✨ Funcionalidades
- Introducción de cuenta por **ID** y gestión de sesión.
- **Ingresos**, **extracciones** y **transferencias** entre cuentas.
- Listado de **movimientos** con validaciones y formato (+/−, colores).
- Persistencia con **Spring Data JPA** (Hibernate) sobre **MySQL 8**.
- Estructura limpia MVC.

## 🧱 Tecnologías
- Java 21 • Spring Boot 3.x • Spring MVC • Spring Data JPA
- Thymeleaf • Bootstrap 5
- MySQL 8 • Maven

## 🚀 Puesta en marcha

### 1) Requisitos
- Java 21  
- Maven (o el **wrapper** incluido `mvnw/mvnw.cmd`)  
- MySQL 8

### 2) Configura la base de datos
Crea la BD (si no existe):
```sql
CREATE DATABASE IF NOT EXISTS cajero_2024 CHARACTER SET utf8mb4;
USE cajero_2024;

-- Tablas mínimas (ajústalas si ya las tienes)
CREATE TABLE IF NOT EXISTS cuentas (
  id_cuenta INT PRIMARY KEY,
  saldo DOUBLE NOT NULL,
  tipo_cuenta VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS movimientos (
  id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
  id_cuenta INT NOT NULL,
  fecha DATETIME NOT NULL,
  cantidad DOUBLE NOT NULL,
  operacion VARCHAR(90) NOT NULL,
  CONSTRAINT fk_mov_cta FOREIGN KEY (id_cuenta) REFERENCES cuentas(id_cuenta)
);

-- Datos de ejemplo
INSERT INTO cuentas (id_cuenta, saldo, tipo_cuenta) VALUES
(1000, 3728, 'AHORRO') ON DUPLICATE KEY UPDATE saldo=VALUES(saldo), tipo_cuenta=VALUES(tipo_cuenta),
(2000, 11516, 'CORRIENTE') ON DUPLICATE KEY UPDATE saldo=VALUES(saldo), tipo_cuenta=VALUES(tipo_cuenta);
src/
 └─ main/
    ├─ java/cajeroweb/
    │  ├─ controller/HomeController.java
    │  ├─ modelo/dao/...
    │  ├─ modelo/entidades/{Cuenta,Movimiento}.java
    │  └─ modelo/repository/{CuentaRepository,MovimientoRepository}.java
    └─ resources/
       ├─ templates/
       │  ├─ home.html  ├─ FormIntroC.html
       │  ├─ ingresar.html ├─ extraer.html ├─ transferir.html └─ movimientos.html
       └─ static/img/  (recursos estáticos si los usas)
