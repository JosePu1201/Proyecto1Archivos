CREATE DATABASE chapinmarket;
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
CREATE TABLE admin.Administrador(
    usuario VARCHAR (20) NOT NULL,
    contrasene VARCHAR (20) NOT NULL,
    PRIMARY KEY(usuario)
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
    PRIMARY KEY (Usuario),
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
-- agregando tablas al schema factura

CREATE TABLE factura.Venta(
    numeroFactura VARCHAR(10) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    Nit VARCHAR(10) NOT NULL,
    Fecha DATE NOT NULL,
    codigoEmpleado VARCHAR(5) NOT NULL,
    TotalSinDescuento DECIMAL(10,2) NOT NULL,
    TotalConDescuento DECIMAL(10,2) NOT NULL,
    codigoLista VARCHAR(10) NOT NULL,
    PRIMARY KEY (numeroFactura),
    FOREIGN KEY (Nit) REFERENCES clientes.Cliente(Nit),
    FOREIGN KEY (codigoEmpleado) REFERENCES admin.Empleado(Usuario)
);
CREATE TABLE factura.ListaProd(
    codigoLista VARCHAR(10) NOT NULL,
    Precio DECIMAL(10, 2) NOT NULL,
    cantidadVendiad INT NOT NULL,
    codigoProducto VARCHAR(10) NOT NULL,
    numeroFactura VARCHAR(10) NOT NULL,
    PRIMARY KEY (codigoLista),
    FOREIGN KEY (numeroFactura) REFERENCES factura.Venta(numeroFactura),
    FOREIGN KEY (codigoProducto) REFERENCES prodG.Producto(Codigo)
 );





-- Generar 150 productos aleatorios
DO $$ 
DECLARE 
    nombres TEXT[] := ARRAY[
        'Jabón', 'Detergente', 'Pasta de Dientes', 'Azúcar', 'Sal', 
        'Aceite de Oliva', 'Arroz', 'Leche', 'Huevos', 'Cereal',
        'Pan', 'Café', 'Té', 'Galletas', 'Cerveza',
        'Papel Higiénico', 'Champú', 'Acondicionador', 'Pañales',
        'Queso', 'Yogur', 'Papel Toalla', 'Carne de Res', 'Pollo',
        'Pescado', 'Frutas', 'Verduras', 'Refresco', 'Agua',
        'Chocolate', 'Helado', 'Sopa en Lata', 'Mantequilla', 'Vino',
        'Jugo de Naranja', 'Cepillo de Dientes', 'Pasta de Dientes', 'Lavadora', 'Secadora',
        'Laptop', 'Teléfono Móvil', 'Tableta', 'Impresora', 'Televisor',
        'Refrigerador', 'Congelador', 'Microondas', 'Tostadora', 'Batidora',
        'Silla', 'Mesa', 'Sofá', 'Cama', 'Lámpara',
        'Silla de Oficina', 'Escritorio', 'Librero', 'Cajonera', 'Mesa de Centro',
        'Cepillo para el Pelo', 'Secador de Pelo', 'Plancha de Ropa', 'Aspiradora', 'Fregadero',
        'Tenedor', 'Cuchillo', 'Cuchara', 'Cucharita', 'Taza',
        'Plato', 'Vaso', 'Olla', 'Sartén', 'Cazuela',
        'Máquina de Coser', 'Hilo', 'Aguja', 'Tijeras', 'Alicate',
        'Martillo', 'Destornillador', 'Llave Inglesa', 'Sierra', 'Clavos',
        'Pala', 'Rastrillo', 'Manguera de Jardín', 'Cubo', 'Escoba',
        'Reloj de Pared', 'Despertador', 'Radio', 'Altavoces', 'Cámara Digital',
        'Gorra', 'Bufanda', 'Guantes', 'Gafas de Sol', 'Paraguas',
        'Zapatos', 'Bolsa de Mano', 'Sombrero', 'Collar', 'Pulsera',
        'Lapicero', 'Cuaderno', 'Calculadora', 'Libro', 'Revista',
        'Pelota', 'Raqueta de Tenis', 'Bicicleta', 'Patines', 'Balón de Fútbol',
        'Guitarra', 'Batería', 'Violín', 'Flauta', 'Teclado',
        'Dron', 'Binoculares', 'Telescopio', 'Candado', 'Llave',
        'Mascarilla Facial', 'Crema Hidratante', 'Protector Solar', 'Loción Corporal', 'Perfume',
        'DVD', 'Blu-ray', 'Videojuego', 'Consola de Juegos', 'Tarjeta de Regalo'
    ];
    schema_name TEXT := 'prodG'; -- Nombre de tu esquema
    nombre TEXT;
    precio NUMERIC;
    codigo TEXT;
BEGIN
    FOR i IN 1..150 LOOP
        -- Obtener el nombre en orden secuencial
        nombre := nombres[i];

        -- Generar un precio aleatorio entre 1.00 y 100.00
        precio := (random() * 99) + 1;

        -- Generar un código único de 10 caracteres
        codigo := substring(md5(random()::text), 1, 10);

        -- Insertar el producto en la tabla
        IF nombre IS NOT NULL THEN
            EXECUTE 'INSERT INTO ' || schema_name || '.Producto (Nombre, Codigo, Precio) VALUES ($1, $2, $3)'
            USING nombre, codigo, precio;
        END IF;
    END LOOP;
END $$;


-- Insertar la primera sucursal
INSERT INTO admin.Sucursal (Codigo, Nombre, Ingresos, Ubicacion, cantProducto)
VALUES ('001', 'Sucursal Central', 0.00, 'Centro', 100);

-- Insertar la segunda sucursal
INSERT INTO admin.Sucursal (Codigo, Nombre, Ingresos, Ubicacion, cantProducto)
VALUES ('002', 'Sucursal Norte', 0.00, 'Norte', 75);

-- Insertar la tercera sucursal
INSERT INTO admin.Sucursal (Codigo, Nombre, Ingresos, Ubicacion, cantProducto)
VALUES ('003', 'Sucursal Sur', 0.00, 'Sur', 75);

-- Insertar 11 empleados en la sucursal 001
INSERT INTO admin.Empleado (Usuario, Contrasenea, Puesto, codigoSucursal)
VALUES
    ('cajero1', 'cajero1', 'Cajero', '001'),
    ('cajero2', 'cajero2', 'Cajero', '001'),
    ('cajero3', 'cajero3', 'Cajero', '001'),
    ('cajero4', 'cajero4', 'Cajero', '001'),
    ('cajero5', 'cajero5', 'Cajero', '001'),
    ('cajero6', 'cajero6', 'Cajero', '001'),
    ('inventario1', 'inventario1', 'Inventario', '001'),
    ('inventario2', 'invnetario2', 'Inventario', '001'),
    ('inventario3', 'inventario3', 'Inventario', '001'),
    ('inventario4', 'inventario4', 'Inventario', '001'),
    ('bodega1', 'bodega1', 'Bodega', '001');

INSERT INTO admin.Empleado (Usuario, Contrasenea, Puesto, codigoSucursal)
VALUES
    ('cajero7', 'cajero7', 'Cajero', '002'),
    ('cajero8', 'cajero8', 'Cajero', '002'),
    ('cajero9', 'cajero9', 'Cajero', '002'),
    ('cajero10', 'cajero10', 'Cajero', '002'),
    ('cajero11', 'cajero11', 'Cajero', '002'),
    ('inventario5', 'inventario5', 'Inventario', '002'),
    ('inventario6', 'invnetario6', 'Inventario', '002'),
    ('inventario7', 'inventario7', 'Inventario', '002'),
    ('inventario8', 'inventario8', 'Inventario', '002'),
    ('cajero12', 'cajero12', 'Cajero', '002'),
    ('bodega3', 'bodega3', 'Bodega', '002');
INSERT INTO admin.Administrador (usuario,contrasene)
VALUES('admin','admin');




