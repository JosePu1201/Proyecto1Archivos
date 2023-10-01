
CREATE DATABASE chapinmarket;
\c chapinmarket

-- Crear los esquemas
CREATE SCHEMA admin;
CREATE SCHEMA prodG;
CREATE SCHEMA factura;
CREATE SCHEMA estante;
CREATE SCHEMA inventarioG;
CREATE SCHEMA bodegaS;
CREATE SCHEMA clientes;
CREATE SCHEMA descuentos;

-- Crear la tabla de Clientes en el esquema "clientes"
CREATE TABLE clientes.Cliente (
    Nit VARCHAR(10) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    GastosEnTienda DECIMAL(10, 2),
    GastosReset DECIMAL (10,2),
    PRIMARY KEY(Nit)
);

-- Crear la tabla de Tarjetas en el esquema "descuentos"
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

-- Crear la tabla de productos en el esquema "prodG"
CREATE TABLE prodG.Producto(
    Nombre VARCHAR (50) NOT NULL,
    Codigo SERIAL,
    Precio DECIMAL (10,2) NOT NULL, 
    PRIMARY KEY (Codigo)
);

-- Crear las tablas del esquema "admin"
CREATE TABLE admin.chapinMarket(
    Nombre VARCHAR(10) NOT NULL,
    CodigoComercio VARCHAR(10) NOT NULL,
    PRIMARY KEY (CodigoComercio)
);

CREATE TABLE admin.Administrador(
    usuario VARCHAR (20) NOT NULL,
    contrasena VARCHAR (20) NOT NULL,
    PRIMARY KEY(usuario)
);

-- Crear la tabla Sucursal en el esquema "admin"
CREATE TABLE admin.Sucursal(
    Codigo VARCHAR(3) NOT NULL,
    Nombre VARCHAR (25) NOT NULL,
    Ingresos DECIMAL (10,2) NOT NULL,
    Ubicacion VARCHAR (50) NOT NULL,
    cantProducto INT NOT NULL,
    PRIMARY KEY (Codigo)
);

-- Crear la tabla Empleado en el esquema "admin"
CREATE TABLE admin.Empleado(
    Usuario VARCHAR(20) NOT NULL,
    Contrasena VARCHAR(20) NOT NULL,
    Puesto VARCHAR(10) NOT NULL,
    codigoSucursal VARCHAR (3) NOT NULL,
    PRIMARY KEY (Usuario),
    FOREIGN KEY (codigoSucursal) REFERENCES admin.Sucursal(Codigo)
);

-- Crear la tabla Inventaro en el esquema "inventarioG"
CREATE TABLE inventarioG.Inventaro(
    idInventario SERIAL,
    idSucursal VARCHAR(4) NOT NULL,
    idProducto INTEGER,
    cantidad INT NOT NULL,
    PRIMARY KEY (idInventario),
    FOREIGN KEY (idSucursal) REFERENCES admin.Sucursal(Codigo),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo)
);

-- Crear la tabla Bodega en el esquema "bodegaS"
CREATE TABLE bodegaS.Bodega(
    idBodega VARCHAR(3) NOT NULL,
    idSucursal VARCHAR(3) NOT NULL,
    PRIMARY KEY (idBodega),
    FOREIGN KEY (idSucursal) REFERENCES admin.Sucursal(Codigo)
);

-- Crear la tabla inventario_de_Producto_enBodega en el esquema "bodegaS"
CREATE TABLE bodegaS.inventario_de_Producto_enBodega(
    idBodega VARCHAR(3) NOT NULL,
    idProducto INTEGER,
    cant INT,
    PRIMARY KEY (idProducto),
    FOREIGN KEY (idBodega) REFERENCES bodegaS.Bodega(idBodega),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo)
);

-- Crear la tabla Estanteria en el esquema "estante"
CREATE TABLE estante.Estanteria(
    idEstante SERIAL,
    idBodega VARCHAR(3) NOT NULL,
    capacidad INT NOT NULL,
    NumPasillo INT NOT NULL,
    PRIMARY KEY (idEstante),
    FOREIGN KEY (idBodega) REFERENCES bodegaS.Bodega(idBodega)
);

-- Crear la tabla Producto_En_Estanteria en el esquema "estante"
CREATE TABLE estante.Producto_En_Estanteria(
    idProdEstante SERIAL,
    idEstante INTEGER,
    idProducto INTEGER,
    cant INT NOT NULL,
    PRIMARY KEY (idProdEstante),
    FOREIGN KEY (idEstante) REFERENCES estante.Estanteria(idEstante),
    FOREIGN KEY (idProducto) REFERENCES prodG.Producto(Codigo) 
);

-- Crear las tablas del esquema "factura"
CREATE TABLE factura.Venta(
    numeroFactura SERIAL,
    Nombre VARCHAR(50) NOT NULL,
    Nit VARCHAR(10) NOT NULL,
    Fecha DATE NOT NULL,
    codigoEmpleado VARCHAR(20) NOT NULL,
    TotalSinDescuento DECIMAL(10,2) NOT NULL,
    TotalConDescuento DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (numeroFactura),
    FOREIGN KEY (Nit) REFERENCES clientes.Cliente(Nit),
    FOREIGN KEY (codigoEmpleado) REFERENCES admin.Empleado(Usuario)
);

CREATE TABLE factura.ListaProd(
    codigoLista SERIAL,
    cantidadVendiad INT NOT NULL,
    codigoProducto INTEGER,
    numeroFactura INTEGER,
    subTotal DECIMAL(10,2) NOT NULL,
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
BEGIN
    FOR i IN 1..150 LOOP
        -- Obtener el nombre en orden secuencial
        nombre := nombres[i];

        -- Generar un precio aleatorio entre 1.00 y 100.00
        precio := (random() * 1000) + 1;

        -- Insertar el producto en la tabla
        IF nombre IS NOT NULL THEN
            EXECUTE 'INSERT INTO ' || schema_name || '.Producto (Nombre, Precio) VALUES ($1, $2)'
            USING nombre, precio;
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
INSERT INTO admin.Empleado (Usuario, Contrasena, Puesto, codigoSucursal)
VALUES
    ('cajero1', 'cajero1', 'Cajero', '001'),
    ('cajero2', 'cajero2', 'Cajero', '001'),
    ('cajero3', 'cajero3', 'Cajero', '001'),
    ('cajero4', 'cajero4', 'Cajero', '001'),
    ('cajero5', 'cajero5', 'Cajero', '001'),
    ('cajero6', 'cajero6', 'Cajero', '001'),
    ('inventario1', 'inventario1', 'Inventario', '001'),
    ('inventario2', 'inventario2', 'Inventario', '001'),
    ('inventario3', 'inventario3', 'Inventario', '001'),
    ('inventario4', 'inventario4', 'Inventario', '001'),
    ('bodega1', 'bodega1', 'Bodega', '001');

-- Insertar 11 empleados en la sucursal 002
INSERT INTO admin.Empleado (Usuario, Contrasena, Puesto, codigoSucursal)
VALUES
    ('cajero7', 'cajero7', 'Cajero', '002'),
    ('cajero8', 'cajero8', 'Cajero', '002'),
    ('cajero9', 'cajero9', 'Cajero', '002'),
    ('cajero10', 'cajero10', 'Cajero', '002'),
    ('cajero11', 'cajero11', 'Cajero', '002'),
    ('inventario5', 'inventario5', 'Inventario', '002'),
    ('inventario6', 'inventario6', 'Inventario', '002'),
    ('inventario7', 'inventario7', 'Inventario', '002'),
    ('inventario8', 'inventario8', 'Inventario', '002'),
    ('cajero12', 'cajero12', 'Cajero', '002'),
    ('bodega3', 'bodega3', 'Bodega', '002');

-- Insertar el administrador
INSERT INTO admin.Administrador (usuario, contrasena)
VALUES ('admin', 'admin');

DO $$
DECLARE
    producto_id INTEGER;
    cantidad INTEGER;
BEGIN
    FOR i IN 1..50 LOOP
        -- Genera un producto_id aleatorio entre 1 y 150
        producto_id := i;

        -- Genera una cantidad aleatoria entre 1 y 20
        cantidad := floor(random() * 20) + 1;

        -- Inserta el registro en la tabla inventarioG.Inventaro
        INSERT INTO inventarioG.Inventaro (idSucursal, idProducto, cantidad)
        VALUES ('001', producto_id, cantidad);
    END LOOP;
END $$;

INSERT INTO bodegaS.Bodega (idBodega, idSucursal)
VALUES ('1B', '001');

INSERT INTO bodegaS.inventario_de_Producto_enBodega (idBodega, idProducto, cant)
SELECT '1B', idProducto, cantidad
FROM inventarioG.Inventaro
WHERE idSucursal = '001';


-- Nuevos clientes 
DO $$ 
DECLARE 
    nombres TEXT[] := ARRAY['Juan', 'Pedro', 'Maria', 'Isabel', 'Alejandra', 'Maria', 'Juan', 'Alexander', 'Critobal', 'Luis'];
    nomb_cliente TEXT;
    nit_cliente TEXT;
    gastos_cliente DECIMAL;
BEGIN
    FOR i IN 1..10 LOOP
        -- Obtener el nombre en orden secuencial
        nomb_cliente := nombres[i];

        -- Generar un NIT aleatorio de 10 dígitos
        nit_cliente := floor(random() * 10000000000)::TEXT;

        -- Generar gastos aleatorios entre 0 y 1000
        gastos_cliente := (random() * 1000);

        -- Insertar el cliente en la tabla
        IF nomb_cliente IS NOT NULL THEN
            EXECUTE 'INSERT INTO clientes.Cliente (Nit, Nombre, GastosEnTienda) VALUES ($1, $2, $3)'
            USING nit_cliente, nomb_cliente, gastos_cliente;
        END IF;
    END LOOP;
END $$;

INSERT INTO descuentos.Tarjeta (Numero_Tarjeta,Nit_Cliente,Descuento,Puntos,Tipo)
VALUES ('1000000000','7212621498',5,0,'Comun');

DO $$ 
DECLARE 
    i INT;
    idBodega VARCHAR(3) := '1B';
BEGIN
    FOR i IN 1..10 LOOP
        -- Generar una capacidad aleatoria entre 50 y 200
        DECLARE capacidad INT := 150;

        -- Insertar la estantería en la tabla
        INSERT INTO estante.Estanteria (idBodega, capacidad, NumPasillo) VALUES (idBodega, capacidad, i);
    END LOOP;
END $$;

