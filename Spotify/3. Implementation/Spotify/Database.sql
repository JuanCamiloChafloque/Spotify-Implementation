--Proyecto Final Entrega 3
--Presentado Por: Juan Camilo Chafloque, Juan Pablo Linares, Juan Manuel Duarte

--DROP DE TABLAS

DROP TABLE Auditoria;
DROP TABLE UsuarioXReproduccion;
DROP TABLE UsuarioLikes;
DROP TABLE CancionXLista;
DROP TABLE CancionXInterprete;
DROP TABLE ListaReproduccion;
DROP TABLE UsuarioC;
DROP TABLE Interprete;
DROP TABLE Cancion;
DROP TABLE Album;
DROP TABLE EmpresaR;
DROP TABLE Genero;
DROP TABLE Suscripcion;
DROP TABLE PaisC;


--CREACIÓN DE TABLAS


--Tabla Auditoria
CREATE TABLE Auditoria (
    codRegistro     NUMBER(4) PRIMARY KEY,
    entidad         VARCHAR2(50) NOT NULL,
    operacion       CHAR(1) NOT NULL,
    fecha           TIMESTAMP,
    CHECK (entidad IN('Artista', 'Album', 'EP', 'Cancion')),
    CHECK (operacion IN('I', 'U', 'D'))
);

--Tabla Empresa
CREATE TABLE EmpresaR (
    codEmpresa      NUMBER(4) PRIMARY KEY,
    nombreEmpresa   VARCHAR2(50) NOT NULL
);

--Tabla Genero
CREATE TABLE Genero (
    codGenero      NUMBER(4) PRIMARY KEY,
    nombreGenero   VARCHAR2(50) NOT NULL
);

--Tabla Pais
CREATE TABLE PaisC (
    codPais      NUMBER(4) PRIMARY KEY,
    nombrePais   VARCHAR2(50) NOT NULL
);

--Tabla Album
CREATE TABLE Album(
    codAlbum             NUMBER(4) PRIMARY KEY,
    nombreAlbum          VARCHAR2(50) NOT NULL,
    tipo                 VARCHAR2(50) NOT NULL,
    fechaLanzamiento     DATE NOT NULL,
    codEmpresa           NUMBER(4) NOT NULL,
    CHECK (tipo IN('Album', 'EP')),
    FOREIGN KEY (codEmpresa) REFERENCES EmpresaR (codEmpresa)
);

--Tabla Cancion
CREATE TABLE Cancion (
    codCancion         NUMBER(4) PRIMARY KEY,
    titulo             VARCHAR2(50) NOT NULL,
    codAlbum           NUMBER(4) NOT NULL,
    codGenero          NUMBER(4) NOT NULL,
    duracion           TIMESTAMP,
    FOREIGN KEY (codGenero) REFERENCES Genero (codGenero),
    FOREIGN KEY (codAlbum) REFERENCES Album (codAlbum)
);

--Tabla Interprete
CREATE TABLE Interprete (
    codInterprete     NUMBER(4) PRIMARY KEY,
    nombreArtistico   VARCHAR2(50),
    nombreReal        VARCHAR2(50) NOT NULL,
    codPais           NUMBER(4) NOT NULL,
    FOREIGN KEY (codPais) REFERENCES PaisC (codPais)
);

--Tabla Suscripcion
CREATE TABLE Suscripcion (
    tipoSuscripcion   VARCHAR2(50) PRIMARY KEY,
    CHECK (tipoSuscripcion IN ('Gratuita', 'Familiar', 'Individual'))
);

--Tabla Usuario
CREATE TABLE UsuarioC (
    codUsuario           NUMBER(4) PRIMARY KEY,
    nombre               VARCHAR2(50) NOT NULL,
    apellido             VARCHAR2(50) NOT NULL,
    nickname             VARCHAR2(50) NOT NULL,
    codPais              NUMBER(4) NOT NULL,
    tipoSuscripcion      VARCHAR2(50) NOT NULL,
    numTarjeta           NUMERIC(16,0)  NULL,
    FOREIGN KEY (codPais) REFERENCES PaisC (codPais),
    FOREIGN KEY (tipoSuscripcion) REFERENCES Suscripcion (tipoSuscripcion)
);


--Tabla ListaReproduccion
CREATE TABLE ListaReproduccion (
    codLista             NUMBER(4) PRIMARY KEY,
    nombreLista          VARCHAR2(50) NOT NULL,
    visibilidad          VARCHAR2(50) NOT NULL,
    orden                VARCHAR2(50) NOT NULL,
    codUsuario           NUMBER(4) NOT NULL,
    CHECK (visibilidad IN('Publica', 'Privada')),
    CHECK (orden IN ('Titulo', 'Album', 'Genero')),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario)
);

--Tabla CancionXInterprete
CREATE TABLE CancionXInterprete (
    codCancion         NUMBER(4) NOT NULL,
    codInterprete      NUMBER(4) NOT NULL,
    codPrincipal       NUMBER(4) NOT NULL,
    PRIMARY KEY (codCancion, codInterprete),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion),
    FOREIGN KEY (codPrincipal) REFERENCES Interprete (codInterprete),
    FOREIGN KEY (codInterprete) REFERENCES Interprete (codInterprete)
);

--Tabla CancionXLista
CREATE TABLE CancionXLista (
    codRegistro          NUMBER(4) PRIMARY KEY,
    codLista             NUMBER(4) NOT NULL,
    codCancion           NUMBER(4) NOT NULL,
    FOREIGN KEY (codLista) REFERENCES ListaReproduccion (codLista),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion)
);

--Tabla Likes
CREATE TABLE UsuarioLikes (
    codRegistro       NUMBER(4) NOT NULL,
    codUsuario        NUMBER(4) NOT NULL,
    codCancion        NUMBER(4) NOT NULL,
    fechaLike         DATE,
    PRIMARY KEY (codRegistro),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion)
);

COMMIT;
--Tabla UsuarioXReproduccion
CREATE TABLE UsuarioXReproduccion (
    codRegistro              NUMBER(4) NOT NULL,
    codUsuario               NUMBER(4) NOT NULL,
    codCancion               NUMBER(4) NOT NULL,
    PRIMARY KEY (codRegistro),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion)
);


--INSERTS DE INFORMACIÓN (LOS INSERTS QUE VAN POR DEFECTO DESDE EL PRINCIPIO DEL SISTEMA, YA QUE SON NECESARIOS PARA AGREGAR Y GUARDAR INFORMACIÓN)

--Tabla Suscripción
INSERT INTO Suscripcion VALUES('Gratuita');
INSERT INTO Suscripcion VALUES('Familiar');
INSERT INTO Suscripcion VALUES('Individual');

--Tabla PaisC
INSERT INTO PaisC VALUES(100, 'Estados Unidos');
INSERT INTO PaisC VALUES(101, 'Colombia');
INSERT INTO PaisC VALUES(102, 'Inglaterra');
INSERT INTO PaisC VALUES(103, 'Australia');
INSERT INTO PaisC VALUES(104, 'Francia');
INSERT INTO PaisC VALUES(105, 'Suecia');
INSERT INTO PaisC VALUES(106, 'Republica Dominicana');
INSERT INTO PaisC VALUES(107, 'España');

--Tabla Empresa
INSERT INTO EmpresaR VALUES(200, 'EMI');
INSERT INTO EmpresaR VALUES(201, 'Universal Music Group');
INSERT INTO EmpresaR VALUES(202, 'Sony Music');
INSERT INTO EmpresaR VALUES(203, 'Warner. Bros Records');
INSERT INTO EmpresaR VALUES(204, 'Polygram');
INSERT INTO EmpresaR VALUES(205, 'BMG Music');
INSERT INTO EmpresaR VALUES(206, 'Discos Fuentes');
INSERT INTO EmpresaR VALUES(207, 'DECCA Label Group');

--Tabla Genero
INSERT INTO Genero VALUES (400, 'Bachata');
INSERT INTO Genero VALUES (401, 'Rock');
INSERT INTO Genero VALUES (402, 'Hip Hop');
INSERT INTO Genero VALUES (403, 'Rap');
INSERT INTO Genero VALUES (404, 'Pop');
INSERT INTO Genero VALUES (405, 'Reggaeton');
INSERT INTO Genero VALUES (406, 'RockNBlues');
INSERT INTO Genero VALUES (407, 'Post Hardcore');
INSERT INTO Genero VALUES (408, 'Funk');
INSERT INTO Genero VALUES (409, 'Metal');
INSERT INTO Genero VALUES (410, 'Electronica');
INSERT INTO Genero VALUES (411, 'House');
INSERT INTO Genero VALUES (412, 'Indie');

COMMIT;