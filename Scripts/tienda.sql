CREATE DATABASE chapinMarket;
\c chapinMarket;
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
    Numero_Tarjeta VARCHAR(10) NOT NULL,
    Nit_Cliente VARCHAR(10) NOT NULL,
    Nombre VARCHAR(50),
    Descuento INT,
    Puntos INT,
    Tipo VARCHAR(15),
    FOREIGN KEY (Nit_Cliente) REFERENCES clientes.Cliente(Nit),
    PRIMARY KEY (Numero_Tarjeta)
);

-- Crear tabla de productos
CREATE TABLE prodG.Producto(
    Nombre VARCHAR (50) NOT NULL,
    Codigo VARCHAR (10) NOT NULL,
    Precio DECIMAL (10,2) NOT NULL, 
    PRIMARY KEY (Codigo)
);
--Crearcion de tablas en schema admin
-- Crear tablas de administradores
CREATE TABLE admin.chapinMarket(
    Nombre VARCHAR(10) NOT NULL,
    CodigoComercio VARCHAR(10) NOT NULL,
    PRIMARY KEY (CodigoComercio)
);
-- Tabla Sucursal 
CREATE TABLE admin.Sucursal(
    Codigo VARCHAR(3) NOT NULL,
    Nombre VARCHAR (25) NOT NULL,
    Ingresos DECIMAL (10,2) NOT NULL,
    Ubicacion VARCHAR (50) NOT NULL,
    cantProducto INT NOT NULL,
    PRIMARY KEY (Codigo)
);
--Tabla empleado
CREATE TABLE admin.Empleado(
    Usuario VARCHAR(20) NOT NULL,
    Contrasenea VARCHAR(20) NOT NULL,
    Puesto VARCHAR(10) NOT NULL,
    codigoSucursal VARCHAR (3) NOT NULL,
    PRIMARY KEY (Usuario,Contrasenea),
    FOREIGN KEY (codigoSucursal) REFERENCES admin.Sucursal(Codigo)
);
-- Creacion en schema inventaroG
CREATE TABLE inventarioG.Inventaro(
    idInventario VARCHAR(5) NOT NULL,
    idSucursal VARCHAR(4) NOT NULL,
    idProducto VARCHAR (10) NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (idInventario),
    FOREIGN KEY (idSucursal) REFERENCES admin.Sucursal(Codigo),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo)
);
--Schema de bodegaS
--Tabla de bodega
CREATE TABLE bodegaS.Bodega(
    idBodega VARCHAR(3) NOT NULL,
    idInventario VARCHAR(5) NOT NULL,
    PRIMARY KEY (idBodega),
    FOREIGN KEY (idInventario) REFERENCES inventarioG.Inventaro(idInventario) 
);
--Tabla de  inventario_de_Producto_enBodega
CREATE TABLE bodegaS.inventario_de_Producto_enBodega(
    idInventarioB VARCHAR(3) NOT NULL,
    idBodega VARCHAR(3) NOT NULL,
    idProducto VARCHAR(10) NOT NULL,
    cant INT,
    PRIMARY KEY (idInventarioB),
    FOREIGN KEY (idBodega) REFERENCES bodegaS.Bodega(idBodega),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo)
);
-- Crear tablas en el schema  estante
CREATE TABLE estante.Estanteria(
    idEstante VARCHAR (5) NOT NULL,
    idBodega VARCHAR(3) NOT NULL,
    capacidad INT NOT NULL,
    PRIMARY KEY (idEstante),
    FOREIGN KEY (idBodega) REFERENCES bodegaS.Bodega(idBodega)
);
--Crear tabla Producto en estanteria
CREATE TABLE estante.Producto_En_Estanteria(
    idProdEstante VARCHAR(5) NOT NULL,
    idEstante VARCHAR (5) NOT NULL,
    idProducto VARCHAR (10) NOT NULL,
    cant INT NOT NULL,
    PRIMARY KEY (idProdEstante),
    FOREIGN KEY (idEstante) REFERENCES estante.Estanteria(idEstante),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo) 
);