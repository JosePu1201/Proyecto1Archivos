CREATE DATABASE chapinMarket;
\c chapinMarket
CREATE SCHEMA admin;
CREATE SCHEMA prodG;
CREATE SCHEMA factura;
CREATE SCHEMA estante;
CREATE SCHEMA inventarioG;
CREATE SCHEMA bodegaS;
CREATE SCHEMA clientes;
CREATE SCHEMA descuentos;

--Crear la tabla de Clientes
CREATE TABLE clientes.Cliente (
    Nit VARCHAR(10) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    GastosEnTienda DECIMAL(10, 2),
    PRIMARY KEY(Nit)
);
--Crea la tabla de Tarjetas
CREATE TABLE descuentos.Tarjeta (
    Numero_Tarjeta VARCHAR(10) NOT NULL PRIMARY KEY,
    Nit_Cliente VARCHAR(10) NOT NULL,
    Nombre VARCHAR(50),
    Descuento INT,
    Puntos INT,
    Tipo VARCHAR(15),
    FOREIGN KEY (Nit_Cliente) REFERENCES clientes.Cliente(Nit)
);
