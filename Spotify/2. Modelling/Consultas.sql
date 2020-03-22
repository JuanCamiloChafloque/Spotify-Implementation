--Proyecto Final Entrega 2
--Presentado Por: Juan Camilo Chafloque, Juan Pablo Linares, Juan Manuel Duarte

--DROP DE TABLAS

DROP TABLE UsuarioXReproduccion;
DROP TABLE UsuarioXSalto;
DROP TABLE UsuarioLikes;
DROP TABLE CancionXLista;
DROP TABLE UsuarioXSeguidores;
DROP TABLE IdiomaXGenero;
DROP TABLE CancionXInterprete;
DROP TABLE ListaReproduccion;
DROP TABLE UsuarioC;
DROP TABLE Interprete;
DROP TABLE Cancion;
DROP TABLE Album;
DROP TABLE EmpresaR;
DROP TABLE Genero;
DROP TABLE Suscripcion;
DROP TABLE Idioma;
DROP TABLE PaisC;


--CREACIÓN DE TABLAS

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

--Tabla Idioma
CREATE TABLE Idioma (
    codIdioma      NUMBER(4) PRIMARY KEY,
    nombreIdioma   VARCHAR2(50) NOT NULL
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
    fechaInicio       DATE,
    fechaRenovacion   DATE,
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

--Tabla IdiomaXGenero
CREATE TABLE IdiomaXGenero (
    codGenero   NUMBER(4) NOT NULL,
    codIdioma   NUMBER(4) NOT NULL,
    PRIMARY KEY (codGenero, codIdioma),
    FOREIGN KEY (codGenero) REFERENCES Genero (codGenero),
    FOREIGN KEY (codIdioma) REFERENCES Idioma (codIdioma)
);

--Tabla Seguidores
CREATE TABLE UsuarioXSeguidores (
    codUsuarioSeguidor    NUMBER(4) NOT NULL,
    codUsuarioSeguido     NUMBER(4) NOT NULL,
    PRIMARY KEY (codUsuarioSeguidor, codUsuarioSeguido),
    FOREIGN KEY (codUsuarioSeguidor) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codUsuarioSeguido) REFERENCES UsuarioC (codUsuario)
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
    codUsuario        NUMBER(4) NOT NULL,
    codRegistro       NUMBER(4) NOT NULL,
    fechaLike         DATE,
    PRIMARY KEY (codUsuario, codRegistro),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codRegistro) REFERENCES CancionXLista (codRegistro)
);

--Tabla UsuarioXReproduccion
CREATE TABLE UsuarioXReproduccion (
    codUsuario               NUMBER(4) NOT NULL,
    codCancion               NUMBER(4) NOT NULL,
    momentoReproduccion      TIMESTAMP NOT NULL,
    PRIMARY KEY (codUsuario, codCancion, momentoReproduccion),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion)
);

--Tabla UsuarioXSalto
CREATE TABLE UsuarioXSalto (
    codUsuario        NUMBER(4) NOT NULL,
    codCancion        NUMBER(4) NOT NULL,
    momentoSalto      TIMESTAMP NOT NULL,
    PRIMARY KEY (codUsuario, codCancion),
    FOREIGN KEY (codUsuario) REFERENCES UsuarioC (codUsuario),
    FOREIGN KEY (codCancion) REFERENCES Cancion (codCancion)
);

--INSERTS DE INFORMACIÓN

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

--Tabla Album
INSERT INTO Album VALUES(300, 'Unorthodox JukeBox', 'Album', to_date('06/12/2012','dd/mm/yyyy'), 200);
INSERT INTO Album VALUES(301, '24K Magic', 'Album', to_date('18/11/2016','dd/mm/yyyy'), 201);
INSERT INTO Album VALUES(302, 'Recovery', 'Album', to_date('18/06/2010','dd/mm/yyyy'), 202);
INSERT INTO Album VALUES(303, 'Revival', 'EP', to_date('15/12/2017','dd/mm/yyyy'), 203);
INSERT INTO Album VALUES (304, 'Black Diamonds', 'Album', to_date('13/11/2012','dd/mm/yyyy'), 204);
INSERT INTO Album VALUES (305, 'Diamond Dreams', 'EP', to_date('18/11/2014','dd/mm/yyyy'), 205);
INSERT INTO Album VALUES (306, 'Unimagine', 'Album', to_date('23/07/2013','dd/mm/yyyy'), 206);
INSERT INTO Album VALUES (307, 'ReImagine', 'EP', to_date('12/09/2014','dd/mm/yyyy'), 207);
INSERT INTO Album VALUES (308, 'Band On The Run', 'Album', to_date('07/12/1973','dd/mm/yyyy'), 200);
INSERT INTO Album VALUES (309, 'Venus And Mars', 'Album', to_date('30/05/1975','dd/mm/yyyy'), 201);
INSERT INTO Album VALUES (310, 'Faith', 'Album', to_date('27/10/1987','dd/mm/yyyy'), 202);
INSERT INTO Album VALUES (311, 'Older', 'EP', to_date('14/05/1996','dd/mm/yyyy'), 203);
INSERT INTO Album VALUES (312, 'Nothing But The Beat', 'EP', to_date('26/08/2011','dd/mm/yyyy'), 204);
INSERT INTO Album VALUES (313, 'Listen', 'Album', to_date('24/11/2014','dd/mm/yyyy'), 205);
INSERT INTO Album VALUES (314, 'Wolfgang Amadeus Phoenix', 'Album', to_date('25/05/2009','dd/mm/yyyy'), 206);
INSERT INTO Album VALUES (315, 'Ti Amo', 'EP', to_date('09/06/2017','dd/mm/yyyy'), 207);
INSERT INTO Album VALUES (316, 'Stories', 'Album', to_date('02/10/2015','dd/mm/yyyy'), 200);
INSERT INTO Album VALUES (317, 'P.A.R.C.E', 'Album',to_date('07/12/2010','dd/mm/yyyy'), 201);
INSERT INTO Album VALUES (318, 'Loco De Amor', 'Album', to_date('11/03/2014','dd/mm/yyyy'), 202);
INSERT INTO Album VALUES (319, 'El Negocio', 'EP', to_date('25/03/2011','dd/mm/yyyy'), 203);
INSERT INTO Album VALUES (320, 'Energía', 'Album', to_date('24/06/2014','dd/mm/yyyy'), 204);
INSERT INTO Album VALUES (321, 'A Son De Guerra', 'Album', to_date('08/06/2010','dd/mm/yyyy'), 205);
INSERT INTO Album VALUES (322, 'A La Mar', 'EP', to_date('08/06/2016','dd/mm/yyyy'), 206);
INSERT INTO Album VALUES (323, 'El Alma al Aire', 'EP', to_date('26/09/2000','dd/mm/yyyy'), 207);

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

--Tabla Idioma
INSERT INTO Idioma VALUES(500, 'Español');
INSERT INTO Idioma VALUES(501, 'Ingles');
INSERT INTO Idioma VALUES(502, 'Frances');
INSERT INTO Idioma VALUES(503, 'Italiano');
INSERT INTO Idioma VALUES(504, 'Alemán');
INSERT INTO Idioma VALUES(505, 'Portugues');

--Tabla Interprete
INSERT INTO Interprete VALUES (600, 'Bruno Mars', 'Peter Gene Hernandez', 100);
INSERT INTO Interprete VALUES (601, 'Eminem', 'Marshall Bruce Mathers', 100);
INSERT INTO Interprete VALUES (602, 'Issues', 'Tyler Carter', 100);
INSERT INTO Interprete VALUES (603, 'Hands Like Houses', 'Trenton Woodley', 103);
INSERT INTO Interprete VALUES (604, 'Paul McCartney', 'Paul McCartney', 102);
INSERT INTO Interprete VALUES (605, 'George Michael', 'Georgios Kyriacos Panayiotou', 102);
INSERT INTO Interprete VALUES (606, 'David Guetta', 'Pierre David Guetta', 104);
INSERT INTO Interprete VALUES (607, 'Phoenix', 'Thomas Mars', 104);
INSERT INTO Interprete VALUES (608, 'Avicii', 'Tim Bergling', 105);
INSERT INTO Interprete VALUES (609, 'Juanes', 'Juan Esteban Aristizábal', 101);
INSERT INTO Interprete VALUES (610, 'JBalvin', 'José Álvaro Osorio', 101);
INSERT INTO Interprete VALUES (611, 'Juan Luis Guerra', 'Juan Luis Guerra', 106);
INSERT INTO Interprete VALUES (612, 'Vicente Garcia', 'Vicente Luis García', 106);
INSERT INTO Interprete VALUES (613, 'Alejandro Sanz', 'Alejandro Sánchez Pizarro', 107);

--Tabla Canción
INSERT INTO Cancion VALUES (700, 'Locked Out of Heaven', 300, 406, TO_TIMESTAMP('02:45', 'MI:SS'));
INSERT INTO Cancion VALUES (701, 'When I Was Your Man', 300, 404, TO_TIMESTAMP('03:12', 'MI:SS'));
INSERT INTO Cancion VALUES (702, 'Treasure', 300, 404, TO_TIMESTAMP('02:30', 'MI:SS'));
INSERT INTO Cancion VALUES (703, 'Gorilla', 300, 408, TO_TIMESTAMP('03:21', 'MI:SS'));
INSERT INTO Cancion VALUES (704, 'Moonshine', 300, 402, TO_TIMESTAMP('04:21', 'MI:SS'));
INSERT INTO Cancion VALUES (705, 'Young Girls', 300, 406, TO_TIMESTAMP('03:45', 'MI:SS'));
INSERT INTO Cancion VALUES (706, '24K Magic', 301, 404, TO_TIMESTAMP('04:55', 'MI:SS'));
INSERT INTO Cancion VALUES (707, 'Thats What I Like', 301, 404, TO_TIMESTAMP('03:32', 'MI:SS'));
INSERT INTO Cancion VALUES (708, 'Versace On The Floor', 301, 404, TO_TIMESTAMP('03:43', 'MI:SS'));
INSERT INTO Cancion VALUES (709, 'Chunky', 301, 406, TO_TIMESTAMP('03:23', 'MI:SS'));
INSERT INTO Cancion VALUES (710, 'Finesse', 301, 408, TO_TIMESTAMP('02:43', 'MI:SS'));
INSERT INTO Cancion VALUES (711, 'No Love', 302, 403, TO_TIMESTAMP('04:44', 'MI:SS'));
INSERT INTO Cancion VALUES (712, 'NO LoVe', 302, 403, TO_TIMESTAMP('03:56', 'MI:SS'));
INSERT INTO Cancion VALUES (713, 'Not Afraid', 302, 402, TO_TIMESTAMP('03:12', 'MI:SS'));
INSERT INTO Cancion VALUES (714, 'Love The Way You Lie', 302, 402, TO_TIMESTAMP('03:02', 'MI:SS'));
INSERT INTO Cancion VALUES (715, 'Space Bound', 302, 402, TO_TIMESTAMP('03:12', 'MI:SS'));
INSERT INTO Cancion VALUES (716, 'Walk on Water', 303, 403, TO_TIMESTAMP('03:17', 'MI:SS'));
INSERT INTO Cancion VALUES (717, 'River', 303, 402, TO_TIMESTAMP('03:58', 'MI:SS'));
INSERT INTO Cancion VALUES (718, 'RiVeR', 303, 402, TO_TIMESTAMP('03:11', 'MI:SS'));
INSERT INTO Cancion VALUES (719, 'Nowhere Fast', 303, 403, TO_TIMESTAMP('03:32', 'MI:SS'));
INSERT INTO Cancion VALUES (720, 'Believe', 303, 403, TO_TIMESTAMP('03:24', 'MI:SS'));
INSERT INTO Cancion VALUES (721, 'Black Diamonds', 304, 409, TO_TIMESTAMP('03:53', 'MI:SS'));
INSERT INTO Cancion VALUES (722, 'King Of Amarillo', 304, 409, TO_TIMESTAMP('03:21', 'MI:SS'));
INSERT INTO Cancion VALUES (723, 'The Worst Of Them', 304, 407, TO_TIMESTAMP('04:01', 'MI:SS'));
INSERT INTO Cancion VALUES (724, 'Princeton Ave', 304, 407, TO_TIMESTAMP('03:09', 'MI:SS'));
INSERT INTO Cancion VALUES (725, 'King Of Amarillo', 305, 401, TO_TIMESTAMP('03:48', 'MI:SS'));
INSERT INTO Cancion VALUES (726, 'Princeton Ave', 305, 401, TO_TIMESTAMP('04:29', 'MI:SS'));
INSERT INTO Cancion VALUES (727, 'Hooligans', 305, 401, TO_TIMESTAMP('03:36', 'MI:SS'));
INSERT INTO Cancion VALUES (728, 'The Worst Of Them', 305, 401, TO_TIMESTAMP('03:33', 'MI:SS'));
INSERT INTO Cancion VALUES (729, 'Developments', 306, 407, TO_TIMESTAMP('03:10', 'MI:SS'));
INSERT INTO Cancion VALUES (730, 'Introduced Species', 306, 407, TO_TIMESTAMP('03:25', 'MI:SS'));
INSERT INTO Cancion VALUES (731, 'ShapeShifters', 306, 407, TO_TIMESTAMP('04:01', 'MI:SS'));
INSERT INTO Cancion VALUES (732, 'No Parallels', 306, 407, TO_TIMESTAMP('03:15', 'MI:SS'));
INSERT INTO Cancion VALUES (733, 'A Tale of Outer Suburbia', 306, 407, TO_TIMESTAMP('03:26', 'MI:SS'));
INSERT INTO Cancion VALUES (734, 'Recollect', 307, 401, TO_TIMESTAMP('04:25', 'MI:SS'));
INSERT INTO Cancion VALUES (735, 'Revive', 307, 401, TO_TIMESTAMP('03:01', 'MI:SS'));
INSERT INTO Cancion VALUES (736, 'Rediscover', 307, 401, TO_TIMESTAMP('04:14', 'MI:SS'));
INSERT INTO Cancion VALUES (737, 'Release', 307, 401, TO_TIMESTAMP('03:11', 'MI:SS'));
INSERT INTO Cancion VALUES (738, 'Reflect', 307, 401, TO_TIMESTAMP('03:14', 'MI:SS'));
INSERT INTO Cancion VALUES (739, 'Band On The Run', 308, 401, TO_TIMESTAMP('03:26', 'MI:SS'));
INSERT INTO Cancion VALUES (740, 'Jet', 308, 401, TO_TIMESTAMP('02:20', 'MI:SS'));
INSERT INTO Cancion VALUES (741, 'Helen Wheels', 308, 401, TO_TIMESTAMP('03:35', 'MI:SS'));
INSERT INTO Cancion VALUES (742, 'Mrs Vandebilt', 308, 401, TO_TIMESTAMP('03:30', 'MI:SS'));
INSERT INTO Cancion VALUES (743, 'Rock Show', 309, 401, TO_TIMESTAMP('02:03', 'MI:SS'));
INSERT INTO Cancion VALUES (744, 'Venus And Mars', 309, 401, TO_TIMESTAMP('03:59', 'MI:SS'));
INSERT INTO Cancion VALUES (745, 'Love In Song', 309, 401, TO_TIMESTAMP('03:52', 'MI:SS'));
INSERT INTO Cancion VALUES (746, 'Letting Go', 309, 401, TO_TIMESTAMP('03:51', 'MI:SS'));
INSERT INTO Cancion VALUES (747, 'Hard Play', 310, 404, TO_TIMESTAMP('02:23', 'MI:SS'));
INSERT INTO Cancion VALUES (748, 'Father Figure', 310, 404, TO_TIMESTAMP('03:27', 'MI:SS'));
INSERT INTO Cancion VALUES (749, 'One More Try', 310, 404, TO_TIMESTAMP('03:48', 'MI:SS'));
INSERT INTO Cancion VALUES (750, 'Monkey', 310, 408, TO_TIMESTAMP('03:44', 'MI:SS'));
INSERT INTO Cancion VALUES (751, 'Kissing A Fool', 310, 408, TO_TIMESTAMP('04:45', 'MI:SS'));
INSERT INTO Cancion VALUES (752, 'Jesus To A Child', 311, 404, TO_TIMESTAMP('03:55', 'MI:SS'));
INSERT INTO Cancion VALUES (753, 'FastLove', 311, 404, TO_TIMESTAMP('03:38', 'MI:SS'));
INSERT INTO Cancion VALUES (754, 'Spinning The Wheel', 311, 404, TO_TIMESTAMP('03:48', 'MI:SS'));
INSERT INTO Cancion VALUES (755, 'Star People', 311, 404, TO_TIMESTAMP('03:58', 'MI:SS'));
INSERT INTO Cancion VALUES (756, 'Titanium', 312, 410, TO_TIMESTAMP('04:18', 'MI:SS'));
INSERT INTO Cancion VALUES (757, 'Without You', 312, 410, TO_TIMESTAMP('03:08', 'MI:SS'));
INSERT INTO Cancion VALUES (758, 'Just One Last Time', 312, 410, TO_TIMESTAMP('03:28', 'MI:SS'));
INSERT INTO Cancion VALUES (759, 'Play Hard', 312, 411, TO_TIMESTAMP('03:45', 'MI:SS'));
INSERT INTO Cancion VALUES (760, 'Shot Me Down', 313, 410, TO_TIMESTAMP('03:54', 'MI:SS'));
INSERT INTO Cancion VALUES (761, 'BAD', 313, 411, TO_TIMESTAMP('03:23', 'MI:SS'));
INSERT INTO Cancion VALUES (762, 'Sun Goes Down', 313, 411, TO_TIMESTAMP('03:32', 'MI:SS'));
INSERT INTO Cancion VALUES (763, 'Dangerous', 313, 411, TO_TIMESTAMP('03:40', 'MI:SS'));
INSERT INTO Cancion VALUES (764, '1901', 314, 401, TO_TIMESTAMP('03:04', 'MI:SS'));
INSERT INTO Cancion VALUES (765, 'Liztomania', 314, 401, TO_TIMESTAMP('03:36', 'MI:SS'));
INSERT INTO Cancion VALUES (766, 'Girlfriend', 314, 412, TO_TIMESTAMP('04:53', 'MI:SS'));
INSERT INTO Cancion VALUES (767, 'Lasso', 314, 412, TO_TIMESTAMP('03:14', 'MI:SS'));
INSERT INTO Cancion VALUES (768, 'Rome', 314, 401, TO_TIMESTAMP('04:41', 'MI:SS'));
INSERT INTO Cancion VALUES (769, 'J-Boy', 315, 404, TO_TIMESTAMP('03:18', 'MI:SS'));
INSERT INTO Cancion VALUES (770, 'Ti Amo', 315, 404, TO_TIMESTAMP('03:51', 'MI:SS'));
INSERT INTO Cancion VALUES (771, 'TI AMo', 315, 404, TO_TIMESTAMP('03:22', 'MI:SS'));
INSERT INTO Cancion VALUES (772, 'Lovelife', 315, 401, TO_TIMESTAMP('03:34', 'MI:SS'));
INSERT INTO Cancion VALUES (773, 'Telefono', 315, 412, TO_TIMESTAMP('03:54', 'MI:SS'));
INSERT INTO Cancion VALUES (774, 'Waiting For Love', 316, 410, TO_TIMESTAMP('03:11', 'MI:SS'));
INSERT INTO Cancion VALUES (775, 'Broken Arrows', 316, 411, TO_TIMESTAMP('03:11', 'MI:SS'));
INSERT INTO Cancion VALUES (776, 'For A Better Day', 316, 411, TO_TIMESTAMP('03:37', 'MI:SS'));
INSERT INTO Cancion VALUES (777, 'Pure Grinding', 316, 410, TO_TIMESTAMP('03:53', 'MI:SS'));
INSERT INTO Cancion VALUES (778, 'PURE GrinDing', 316, 410, TO_TIMESTAMP('03:17', 'MI:SS'));
INSERT INTO Cancion VALUES (779, 'Yerbatero', 317, 401, TO_TIMESTAMP('03:28', 'MI:SS'));
INSERT INTO Cancion VALUES (780, 'Y No Regresas', 317, 401, TO_TIMESTAMP('03:29', 'MI:SS'));
INSERT INTO Cancion VALUES (781, 'Regalito', 317, 404, TO_TIMESTAMP('03:31', 'MI:SS'));
INSERT INTO Cancion VALUES (782, 'La Soledad', 317, 401, TO_TIMESTAMP('03:32', 'MI:SS'));
INSERT INTO Cancion VALUES (783, 'La Luz', 318, 401, TO_TIMESTAMP('03:10', 'MI:SS'));
INSERT INTO Cancion VALUES (784, 'Mil Pedazos', 318, 404, TO_TIMESTAMP('03:01', 'MI:SS'));
INSERT INTO Cancion VALUES (785, 'Una Flor', 318, 404, TO_TIMESTAMP('03:48', 'MI:SS'));
INSERT INTO Cancion VALUES (786, 'La Verdad', 318, 401, TO_TIMESTAMP('04:32', 'MI:SS'));
INSERT INTO Cancion VALUES (787, 'Seguire Subiendo', 319, 405, TO_TIMESTAMP('03:13', 'MI:SS'));
INSERT INTO Cancion VALUES (788, 'Un Sueño', 319, 405, TO_TIMESTAMP('03:55', 'MI:SS'));
INSERT INTO Cancion VALUES (789, 'Pandereta', 319, 405, TO_TIMESTAMP('03:26', 'MI:SS'));
INSERT INTO Cancion VALUES (790, 'Abrazame', 319, 405, TO_TIMESTAMP('04:52', 'MI:SS'));
INSERT INTO Cancion VALUES (791, 'Ginza', 320, 405, TO_TIMESTAMP('04:19', 'MI:SS'));
INSERT INTO Cancion VALUES (792, 'Bobo', 320, 405, TO_TIMESTAMP('03:51', 'MI:SS'));
INSERT INTO Cancion VALUES (793, 'Safari', 320, 405, TO_TIMESTAMP('03:25', 'MI:SS'));
INSERT INTO Cancion VALUES (794, 'Sigo Extrañandote', 320, 404, TO_TIMESTAMP('03:52', 'MI:SS'));
INSERT INTO Cancion VALUES (795, 'Bachata en Fukuoka', 321, 400, TO_TIMESTAMP('03:34', 'MI:SS'));
INSERT INTO Cancion VALUES (796, 'Mi Bendición', 321, 400, TO_TIMESTAMP('03:43', 'MI:SS'));
INSERT INTO Cancion VALUES (797, 'La Guagua', 321, 400, TO_TIMESTAMP('03:21', 'MI:SS'));
INSERT INTO Cancion VALUES (798, 'La Calle', 321, 400, TO_TIMESTAMP('03:12', 'MI:SS'));
INSERT INTO Cancion VALUES (799, 'A La Mar', 322, 400, TO_TIMESTAMP('02:14', 'MI:SS'));
INSERT INTO Cancion VALUES (800, 'La Paloma', 322, 400, TO_TIMESTAMP('03:35', 'MI:SS'));
INSERT INTO Cancion VALUES (801, 'Mal de Amore', 322, 400, TO_TIMESTAMP('04:53', 'MI:SS'));
INSERT INTO Cancion VALUES (802, 'Carmesí', 322, 400, TO_TIMESTAMP('03:27', 'MI:SS'));
INSERT INTO Cancion VALUES (803, 'Cuando Nadie Me Ve', 323, 406, TO_TIMESTAMP('03:44', 'MI:SS'));
INSERT INTO Cancion VALUES (804, 'Qusiera Ser', 323, 404, TO_TIMESTAMP('02:24', 'MI:SS'));
INSERT INTO Cancion VALUES (805, 'El Alma al Aire', 323, 404, TO_TIMESTAMP('03:32', 'MI:SS'));
INSERT INTO Cancion VALUES (806, 'Llegó, llegó soledad', 323, 406, TO_TIMESTAMP('03:23', 'MI:SS'));

--Tabla Usuario
INSERT INTO UsuarioC VALUES (900, 'Juan Camilo', 'Chafloque', 'Juan Ca', 100, 'Individual', TO_DATE('14/04/2016','dd/mm/yyyy'), TO_DATE('22/10/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (901, 'Juan Pablo', 'Linares', 'Juan Pa', 101, 'Individual', TO_DATE('23/03/2015','dd/mm/yyyy'), TO_DATE('04/11/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (902, 'Juan Manuel', 'Duarte', 'Juan Ma', 102, 'Gratuita', NULL, NULL);
INSERT INTO UsuarioC VALUES (903, 'Nicolas', 'Martinez', 'Nico', 103, 'Individual', TO_DATE('19/11/2015','dd/mm/yyyy'), TO_DATE('16/11/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (904, 'Sebastian', 'Sanchez', 'Sebas', 104, 'Individual', TO_DATE('01/07/2015','dd/mm/yyyy'), TO_DATE('23/10/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (905, 'Carolina', 'Hernandez', 'Caro', 104, 'Gratuita', NULL, NULL);
INSERT INTO UsuarioC VALUES (906, 'Maria', 'Rodriguez', 'Mari', 106, 'Individual', TO_DATE('07/01/2017','dd/mm/yyyy'), TO_DATE('12/12/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (907, 'Carlos', 'Jimenez', 'Charlie', 102, 'Familiar', TO_DATE('16/09/2018','dd/mm/yyyy'), TO_DATE('02/11/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (908, 'Luis', 'Rojas', 'Lucho', 100, 'Familiar', TO_DATE('19/06/2018','dd/mm/yyyy'), TO_DATE('09/11/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (909, 'Antonio', 'Ricaurte', 'Toño', 105, 'Individual', TO_DATE('20/12/2015','dd/mm/yyyy'), TO_DATE('20/10/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (910, 'Andrea', 'Suarez', 'Andre', 103, 'Individual', TO_DATE('12/10/2015','dd/mm/yyyy'), TO_DATE('17/11/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (911, 'Natalia', 'Blanco', 'Nati', 106, 'Gratuita', NULL, NULL);
INSERT INTO UsuarioC VALUES (912, 'Jorge', 'Vasquez', 'Jorge', 103, 'Familiar', TO_DATE('04/04/2016','dd/mm/yyyy'), TO_DATE('24/10/2018','dd/mm/yyyy'));
INSERT INTO UsuarioC VALUES (913, 'Andres', 'Castillo', 'Andres', 103, 'Gratuita', NULL, NULL);

--Tabla ListaReproduccion
INSERT INTO ListaReproduccion VALUES (1000, 'Mis Canciones', 'Publica', 'Album', 900);
INSERT INTO ListaReproduccion VALUES (1001, 'Mis Favoritas', 'Publica', 'Titulo', 900);
INSERT INTO ListaReproduccion VALUES (1002, 'Mi Lista', 'Privada', 'Genero', 901);
INSERT INTO ListaReproduccion VALUES (1003, 'Lista Viajes', 'Publica', 'Genero', 902);
INSERT INTO ListaReproduccion VALUES (1004, 'Rumba', 'Publica', 'Album', 903);
INSERT INTO ListaReproduccion VALUES (1005, 'Playlist Gimnasio', 'Privada', 'Titulo', 904);
INSERT INTO ListaReproduccion VALUES (1006, 'Playlist Favoritas', 'Publica', 'Genero', 904);
INSERT INTO ListaReproduccion VALUES (1007, 'Canciones', 'Privada', 'Album', 905);
INSERT INTO ListaReproduccion VALUES (1008, 'Mis Favs', 'Publica', 'Titulo', 905);
INSERT INTO ListaReproduccion VALUES (1009, 'Playlist Rumba', 'Privada', 'Album', 906);
INSERT INTO ListaReproduccion VALUES (1010, 'Playlist Deporte', 'Publica', 'Genero', 906);
INSERT INTO ListaReproduccion VALUES (1011, 'Lista Reproduccion 1', 'Publica', 'Titulo', 907);
INSERT INTO ListaReproduccion VALUES (1012, 'Lista Reproduccion 2', 'Publica', 'Album', 907);
INSERT INTO ListaReproduccion VALUES (1013, 'Canciones Favoritas', 'Privada', 'Genero', 908);
INSERT INTO ListaReproduccion VALUES (1014, 'Mi Lista 1', 'Publica', 'Titulo', 909);
INSERT INTO ListaReproduccion VALUES (1015, 'Mi Lista 2', 'Publica', 'Genero', 910);
INSERT INTO ListaReproduccion VALUES (1016, 'Playlist Dormir', 'Privada', 'Titulo', 911);
INSERT INTO ListaReproduccion VALUES (1017, 'Mi Playlist', 'Privada', 'Genero', 912);
INSERT INTO ListaReproduccion VALUES (1018, 'Lista Rep', 'Privada', 'Titulo', 913);

--Tabla IdiomaXGenero
INSERT INTO IdiomaXGenero VALUES (400, 500);
INSERT INTO IdiomaXGenero VALUES (400, 501);
INSERT INTO IdiomaXGenero VALUES (400, 502);
INSERT INTO IdiomaXGenero VALUES (400, 503);
INSERT INTO IdiomaXGenero VALUES (400, 504);
INSERT INTO IdiomaXGenero VALUES (400, 505);
INSERT INTO IdiomaXGenero VALUES (401, 500);
INSERT INTO IdiomaXGenero VALUES (401, 501);
INSERT INTO IdiomaXGenero VALUES (401, 502);
INSERT INTO IdiomaXGenero VALUES (401, 503);
INSERT INTO IdiomaXGenero VALUES (402, 500);
INSERT INTO IdiomaXGenero VALUES (402, 501);
INSERT INTO IdiomaXGenero VALUES (402, 503);
INSERT INTO IdiomaXGenero VALUES (402, 505);
INSERT INTO IdiomaXGenero VALUES (403, 500);
INSERT INTO IdiomaXGenero VALUES (403, 501);
INSERT INTO IdiomaXGenero VALUES (404, 500);
INSERT INTO IdiomaXGenero VALUES (404, 502);
INSERT INTO IdiomaXGenero VALUES (404, 504);
INSERT INTO IdiomaXGenero VALUES (404, 505);
INSERT INTO IdiomaXGenero VALUES (405, 500);
INSERT INTO IdiomaXGenero VALUES (405, 501);
INSERT INTO IdiomaXGenero VALUES (405, 503);
INSERT INTO IdiomaXGenero VALUES (406, 500);
INSERT INTO IdiomaXGenero VALUES (406, 502);
INSERT INTO IdiomaXGenero VALUES (407, 503);
INSERT INTO IdiomaXGenero VALUES (407, 504);
INSERT INTO IdiomaXGenero VALUES (407, 505);
INSERT INTO IdiomaXGenero VALUES (408, 502);
INSERT INTO IdiomaXGenero VALUES (409, 502);
INSERT INTO IdiomaXGenero VALUES (409, 504);
INSERT INTO IdiomaXGenero VALUES (410, 500);
INSERT INTO IdiomaXGenero VALUES (410, 501);
INSERT INTO IdiomaXGenero VALUES (410, 502);
INSERT INTO IdiomaXGenero VALUES (410, 504);
INSERT INTO IdiomaXGenero VALUES (411, 500);
INSERT INTO IdiomaXGenero VALUES (411, 502);
INSERT INTO IdiomaXGenero VALUES (411, 505);
INSERT INTO IdiomaXGenero VALUES (412, 500);

--Tabla UsuarioXSeguidores USUARIO 900 - 913
INSERT INTO UsuarioXSeguidores VALUES (901, 900);
INSERT INTO UsuarioXSeguidores VALUES (903, 900);
INSERT INTO UsuarioXSeguidores VALUES (905, 900);
INSERT INTO UsuarioXSeguidores VALUES (907, 900);
INSERT INTO UsuarioXSeguidores VALUES (911, 900);
INSERT INTO UsuarioXSeguidores VALUES (912, 900);
INSERT INTO UsuarioXSeguidores VALUES (900, 901);
INSERT INTO UsuarioXSeguidores VALUES (902, 901);
INSERT INTO UsuarioXSeguidores VALUES (903, 901);
INSERT INTO UsuarioXSeguidores VALUES (904, 901);
INSERT INTO UsuarioXSeguidores VALUES (905, 901);
INSERT INTO UsuarioXSeguidores VALUES (906, 901);
INSERT INTO UsuarioXSeguidores VALUES (900, 902);
INSERT INTO UsuarioXSeguidores VALUES (905, 902);
INSERT INTO UsuarioXSeguidores VALUES (906, 902);
INSERT INTO UsuarioXSeguidores VALUES (907, 902);
INSERT INTO UsuarioXSeguidores VALUES (913, 902);
INSERT INTO UsuarioXSeguidores VALUES (902, 903);
INSERT INTO UsuarioXSeguidores VALUES (904, 903);
INSERT INTO UsuarioXSeguidores VALUES (901, 904);
INSERT INTO UsuarioXSeguidores VALUES (909, 904);
INSERT INTO UsuarioXSeguidores VALUES (912, 904);
INSERT INTO UsuarioXSeguidores VALUES (904, 905);
INSERT INTO UsuarioXSeguidores VALUES (906, 905);
INSERT INTO UsuarioXSeguidores VALUES (907, 905);
INSERT INTO UsuarioXSeguidores VALUES (908, 906);
INSERT INTO UsuarioXSeguidores VALUES (911, 906);
INSERT INTO UsuarioXSeguidores VALUES (913, 906);
INSERT INTO UsuarioXSeguidores VALUES (902, 907);
INSERT INTO UsuarioXSeguidores VALUES (903, 907);
INSERT INTO UsuarioXSeguidores VALUES (904, 908);
INSERT INTO UsuarioXSeguidores VALUES (905, 908);
INSERT INTO UsuarioXSeguidores VALUES (906, 908);
INSERT INTO UsuarioXSeguidores VALUES (911, 908);
INSERT INTO UsuarioXSeguidores VALUES (910, 909);
INSERT INTO UsuarioXSeguidores VALUES (911, 909);
INSERT INTO UsuarioXSeguidores VALUES (913, 909);
INSERT INTO UsuarioXSeguidores VALUES (906, 910);
INSERT INTO UsuarioXSeguidores VALUES (907, 910);
INSERT INTO UsuarioXSeguidores VALUES (908, 910);
INSERT INTO UsuarioXSeguidores VALUES (909, 910);
INSERT INTO UsuarioXSeguidores VALUES (900, 911);
INSERT INTO UsuarioXSeguidores VALUES (901, 911);
INSERT INTO UsuarioXSeguidores VALUES (905, 911);
INSERT INTO UsuarioXSeguidores VALUES (909, 911);
INSERT INTO UsuarioXSeguidores VALUES (901, 912);
INSERT INTO UsuarioXSeguidores VALUES (908, 912);
INSERT INTO UsuarioXSeguidores VALUES (910, 912);
INSERT INTO UsuarioXSeguidores VALUES (911, 912);
INSERT INTO UsuarioXSeguidores VALUES (913, 912);
INSERT INTO UsuarioXSeguidores VALUES (903, 913);
INSERT INTO UsuarioXSeguidores VALUES (904, 913);
INSERT INTO UsuarioXSeguidores VALUES (905, 913);
INSERT INTO UsuarioXSeguidores VALUES (907, 913);

--Tabla CancionXInterprete
INSERT INTO CancionXInterprete VALUES (700, 600, 601);
INSERT INTO CancionXInterprete VALUES (701, 600, 602);
INSERT INTO CancionXInterprete VALUES (702, 600, 603);
INSERT INTO CancionXInterprete VALUES (703, 600, 604);
INSERT INTO CancionXInterprete VALUES (704, 600, 605);
INSERT INTO CancionXInterprete VALUES (705, 600, 606);
INSERT INTO CancionXInterprete VALUES (706, 600, 607);
INSERT INTO CancionXInterprete VALUES (707, 600, 608);
INSERT INTO CancionXInterprete VALUES (708, 600, 609);
INSERT INTO CancionXInterprete VALUES (709, 600, 610);
INSERT INTO CancionXInterprete VALUES (710, 600, 611);
INSERT INTO CancionXInterprete VALUES (711, 601, 600);
INSERT INTO CancionXInterprete VALUES (712, 601, 602);
INSERT INTO CancionXInterprete VALUES (713, 601, 604);
INSERT INTO CancionXInterprete VALUES (714, 601, 605);
INSERT INTO CancionXInterprete VALUES (715, 601, 608);
INSERT INTO CancionXInterprete VALUES (716, 601, 609);
INSERT INTO CancionXInterprete VALUES (717, 601, 610);
INSERT INTO CancionXInterprete VALUES (718, 601, 611);
INSERT INTO CancionXInterprete VALUES (719, 601, 612);
INSERT INTO CancionXInterprete VALUES (720, 601, 613);
INSERT INTO CancionXInterprete VALUES (721, 602, 600);
INSERT INTO CancionXInterprete VALUES (722, 602, 601);
INSERT INTO CancionXInterprete VALUES (723, 602, 602);
INSERT INTO CancionXInterprete VALUES (724, 602, 602);
INSERT INTO CancionXInterprete VALUES (725, 602, 604);
INSERT INTO CancionXInterprete VALUES (726, 602, 602);
INSERT INTO CancionXInterprete VALUES (727, 602, 607);
INSERT INTO CancionXInterprete VALUES (728, 602, 610);
INSERT INTO CancionXInterprete VALUES (729, 603, 610);
INSERT INTO CancionXInterprete VALUES (730, 603, 613);
INSERT INTO CancionXInterprete VALUES (731, 603, 602);
INSERT INTO CancionXInterprete VALUES (732, 603, 605);
INSERT INTO CancionXInterprete VALUES (733, 603, 607);
INSERT INTO CancionXInterprete VALUES (734, 603, 603);
INSERT INTO CancionXInterprete VALUES (735, 603, 603);
INSERT INTO CancionXInterprete VALUES (736, 603, 605);
INSERT INTO CancionXInterprete VALUES (737, 603, 605);
INSERT INTO CancionXInterprete VALUES (738, 603, 605);
INSERT INTO CancionXInterprete VALUES (739, 604, 604);
INSERT INTO CancionXInterprete VALUES (740, 604, 604);
INSERT INTO CancionXInterprete VALUES (741, 604, 608);
INSERT INTO CancionXInterprete VALUES (742, 604, 607);
INSERT INTO CancionXInterprete VALUES (743, 604, 608);
INSERT INTO CancionXInterprete VALUES (744, 604, 605);
INSERT INTO CancionXInterprete VALUES (745, 604, 607);
INSERT INTO CancionXInterprete VALUES (746, 604, 607);
INSERT INTO CancionXInterprete VALUES (747, 605, 604);
INSERT INTO CancionXInterprete VALUES (748, 605, 608);
INSERT INTO CancionXInterprete VALUES (749, 605, 611);
INSERT INTO CancionXInterprete VALUES (750, 605, 612);
INSERT INTO CancionXInterprete VALUES (751, 605, 612);
INSERT INTO CancionXInterprete VALUES (752, 605, 605);
INSERT INTO CancionXInterprete VALUES (753, 605, 605);
INSERT INTO CancionXInterprete VALUES (754, 605, 607);
INSERT INTO CancionXInterprete VALUES (755, 605, 609);
INSERT INTO CancionXInterprete VALUES (756, 606, 609);
INSERT INTO CancionXInterprete VALUES (757, 606, 610);
INSERT INTO CancionXInterprete VALUES (758, 606, 606);
INSERT INTO CancionXInterprete VALUES (759, 606, 606);
INSERT INTO CancionXInterprete VALUES (760, 606, 612);
INSERT INTO CancionXInterprete VALUES (761, 606, 611);
INSERT INTO CancionXInterprete VALUES (762, 606, 606);
INSERT INTO CancionXInterprete VALUES (763, 606, 607);
INSERT INTO CancionXInterprete VALUES (764, 607, 601);
INSERT INTO CancionXInterprete VALUES (765, 607, 608);
INSERT INTO CancionXInterprete VALUES (766, 607, 607);
INSERT INTO CancionXInterprete VALUES (767, 607, 607);
INSERT INTO CancionXInterprete VALUES (768, 607, 602);
INSERT INTO CancionXInterprete VALUES (769, 607, 605);
INSERT INTO CancionXInterprete VALUES (770, 607, 607);
INSERT INTO CancionXInterprete VALUES (771, 607, 610);
INSERT INTO CancionXInterprete VALUES (772, 607, 610);
INSERT INTO CancionXInterprete VALUES (773, 607, 610);
INSERT INTO CancionXInterprete VALUES (774, 608, 609);
INSERT INTO CancionXInterprete VALUES (775, 608, 607);
INSERT INTO CancionXInterprete VALUES (776, 608, 603);
INSERT INTO CancionXInterprete VALUES (777, 608, 604);
INSERT INTO CancionXInterprete VALUES (778, 608, 608);
INSERT INTO CancionXInterprete VALUES (779, 609, 608);
INSERT INTO CancionXInterprete VALUES (780, 609, 609);
INSERT INTO CancionXInterprete VALUES (781, 609, 609);
INSERT INTO CancionXInterprete VALUES (782, 609, 609);
INSERT INTO CancionXInterprete VALUES (783, 609, 609);
INSERT INTO CancionXInterprete VALUES (784, 609, 612);
INSERT INTO CancionXInterprete VALUES (785, 609, 613);
INSERT INTO CancionXInterprete VALUES (786, 609, 609);
INSERT INTO CancionXInterprete VALUES (787, 610, 612);
INSERT INTO CancionXInterprete VALUES (788, 610, 612);
INSERT INTO CancionXInterprete VALUES (789, 610, 610);
INSERT INTO CancionXInterprete VALUES (790, 610, 610);
INSERT INTO CancionXInterprete VALUES (791, 610, 610);
INSERT INTO CancionXInterprete VALUES (792, 610, 605);
INSERT INTO CancionXInterprete VALUES (793, 610, 607);
INSERT INTO CancionXInterprete VALUES (794, 610, 603);
INSERT INTO CancionXInterprete VALUES (795, 611, 602);
INSERT INTO CancionXInterprete VALUES (796, 611, 602);
INSERT INTO CancionXInterprete VALUES (797, 611, 604);
INSERT INTO CancionXInterprete VALUES (798, 611, 607);
INSERT INTO CancionXInterprete VALUES (799, 612, 612);
INSERT INTO CancionXInterprete VALUES (800, 612, 612);
INSERT INTO CancionXInterprete VALUES (801, 612, 601);
INSERT INTO CancionXInterprete VALUES (802, 612, 600);
INSERT INTO CancionXInterprete VALUES (803, 613, 608);
INSERT INTO CancionXInterprete VALUES (804, 613, 608);
INSERT INTO CancionXInterprete VALUES (805, 613, 603);
INSERT INTO CancionXInterprete VALUES (806, 613, 609);

--Tabla CancionXLista
INSERT INTO CancionXLista VALUES (2000, 1000, 708);
INSERT INTO CancionXLista VALUES (2001, 1000, 765);
INSERT INTO CancionXLista VALUES (2002, 1000, 789);
INSERT INTO CancionXLista VALUES (2003, 1000, 712);
INSERT INTO CancionXLista VALUES (2004, 1000, 734);
INSERT INTO CancionXLista VALUES (2005, 1000, 700);
INSERT INTO CancionXLista VALUES (2006, 1000, 778);
INSERT INTO CancionXLista VALUES (2007, 1000, 721);
INSERT INTO CancionXLista VALUES (2008, 1000, 797);
INSERT INTO CancionXLista VALUES (2009, 1000, 711);
INSERT INTO CancionXLista VALUES (2010, 1000, 800);
INSERT INTO CancionXLista VALUES (2011, 1001, 745);
INSERT INTO CancionXLista VALUES (2012, 1001, 732);
INSERT INTO CancionXLista VALUES (2013, 1001, 789);
INSERT INTO CancionXLista VALUES (2014, 1001, 803);
INSERT INTO CancionXLista VALUES (2015, 1001, 767);
INSERT INTO CancionXLista VALUES (2016, 1001, 739);
INSERT INTO CancionXLista VALUES (2017, 1001, 740);
INSERT INTO CancionXLista VALUES (2018, 1001, 701);
INSERT INTO CancionXLista VALUES (2019, 1001, 788);
INSERT INTO CancionXLista VALUES (2020, 1001, 724);
INSERT INTO CancionXLista VALUES (2021, 1001, 801);
INSERT INTO CancionXLista VALUES (2022, 1002, 755);
INSERT INTO CancionXLista VALUES (2023, 1002, 739);
INSERT INTO CancionXLista VALUES (2024, 1002, 721);
INSERT INTO CancionXLista VALUES (2025, 1002, 733);
INSERT INTO CancionXLista VALUES (2026, 1002, 724);
INSERT INTO CancionXLista VALUES (2027, 1002, 727);
INSERT INTO CancionXLista VALUES (2028, 1002, 799);
INSERT INTO CancionXLista VALUES (2029, 1003, 700);
INSERT INTO CancionXLista VALUES (2030, 1003, 701);
INSERT INTO CancionXLista VALUES (2031, 1003, 787);
INSERT INTO CancionXLista VALUES (2032, 1003, 749);
INSERT INTO CancionXLista VALUES (2033, 1003, 757);
INSERT INTO CancionXLista VALUES (2034, 1003, 722);
INSERT INTO CancionXLista VALUES (2035, 1003, 745);
INSERT INTO CancionXLista VALUES (2036, 1003, 777);
INSERT INTO CancionXLista VALUES (2037, 1003, 790);
INSERT INTO CancionXLista VALUES (2038, 1004, 743);
INSERT INTO CancionXLista VALUES (2039, 1004, 744);
INSERT INTO CancionXLista VALUES (2040, 1004, 778);
INSERT INTO CancionXLista VALUES (2041, 1004, 799);
INSERT INTO CancionXLista VALUES (2042, 1004, 804);
INSERT INTO CancionXLista VALUES (2043, 1005, 711);
INSERT INTO CancionXLista VALUES (2044, 1005, 715);
INSERT INTO CancionXLista VALUES (2045, 1005, 764);
INSERT INTO CancionXLista VALUES (2046, 1005, 781);
INSERT INTO CancionXLista VALUES (2047, 1005, 792);
INSERT INTO CancionXLista VALUES (2048, 1005, 803);
INSERT INTO CancionXLista VALUES (2049, 1006, 708);
INSERT INTO CancionXLista VALUES (2050, 1006, 756);
INSERT INTO CancionXLista VALUES (2051, 1006, 732);
INSERT INTO CancionXLista VALUES (2052, 1006, 744);
INSERT INTO CancionXLista VALUES (2053, 1006, 799);
INSERT INTO CancionXLista VALUES (2054, 1006, 702);
INSERT INTO CancionXLista VALUES (2055, 1006, 703);
INSERT INTO CancionXLista VALUES (2056, 1007, 704);
INSERT INTO CancionXLista VALUES (2057, 1007, 715);
INSERT INTO CancionXLista VALUES (2058, 1007, 766);
INSERT INTO CancionXLista VALUES (2059, 1008, 756);
INSERT INTO CancionXLista VALUES (2060, 1008, 733);
INSERT INTO CancionXLista VALUES (2061, 1008, 794);
INSERT INTO CancionXLista VALUES (2062, 1008, 749);
INSERT INTO CancionXLista VALUES (2063, 1008, 711);
INSERT INTO CancionXLista VALUES (2064, 1008, 708);
INSERT INTO CancionXLista VALUES (2065, 1009, 727);
INSERT INTO CancionXLista VALUES (2066, 1009, 745);
INSERT INTO CancionXLista VALUES (2067, 1009, 739);
INSERT INTO CancionXLista VALUES (2068, 1009, 726);
INSERT INTO CancionXLista VALUES (2069, 1010, 780);
INSERT INTO CancionXLista VALUES (2070, 1010, 803);
INSERT INTO CancionXLista VALUES (2071, 1010, 776);
INSERT INTO CancionXLista VALUES (2072, 1010, 711);
INSERT INTO CancionXLista VALUES (2073, 1010, 701);
INSERT INTO CancionXLista VALUES (2074, 1011, 800);
INSERT INTO CancionXLista VALUES (2075, 1011, 745);
INSERT INTO CancionXLista VALUES (2076, 1012, 746);
INSERT INTO CancionXLista VALUES (2077, 1012, 732);
INSERT INTO CancionXLista VALUES (2078, 1012, 787);
INSERT INTO CancionXLista VALUES (2079, 1012, 767);
INSERT INTO CancionXLista VALUES (2080, 1012, 735);
INSERT INTO CancionXLista VALUES (2081, 1012, 799);
INSERT INTO CancionXLista VALUES (2082, 1012, 803);
INSERT INTO CancionXLista VALUES (2083, 1012, 722);
INSERT INTO CancionXLista VALUES (2084, 1013, 738);
INSERT INTO CancionXLista VALUES (2085, 1013, 767);
INSERT INTO CancionXLista VALUES (2086, 1013, 734);
INSERT INTO CancionXLista VALUES (2087, 1013, 700);
INSERT INTO CancionXLista VALUES (2088, 1014, 700);
INSERT INTO CancionXLista VALUES (2089, 1014, 756);
INSERT INTO CancionXLista VALUES (2090, 1014, 743);
INSERT INTO CancionXLista VALUES (2091, 1015, 744);
INSERT INTO CancionXLista VALUES (2092, 1015, 758);
INSERT INTO CancionXLista VALUES (2093, 1015, 776);
INSERT INTO CancionXLista VALUES (2094, 1015, 710);
INSERT INTO CancionXLista VALUES (2095, 1015, 709);
INSERT INTO CancionXLista VALUES (2096, 1015, 723);
INSERT INTO CancionXLista VALUES (2097, 1015, 788);
INSERT INTO CancionXLista VALUES (2098, 1015, 759);
INSERT INTO CancionXLista VALUES (2099, 1016, 702);
INSERT INTO CancionXLista VALUES (2100, 1016, 739);
INSERT INTO CancionXLista VALUES (2101, 1016, 800);
INSERT INTO CancionXLista VALUES (2102, 1017, 767);
INSERT INTO CancionXLista VALUES (2103, 1017, 754);
INSERT INTO CancionXLista VALUES (2104, 1017, 778);
INSERT INTO CancionXLista VALUES (2105, 1017, 710);
INSERT INTO CancionXLista VALUES (2106, 1018, 799);
INSERT INTO CancionXLista VALUES (2107, 1018, 800);
INSERT INTO CancionXLista VALUES (2108, 1018, 803);
INSERT INTO CancionXLista VALUES (2109, 1000, 790);

--Tabla UsuarioLikes
INSERT INTO UsuarioLikes VALUES (900, 2000, TO_DATE('12/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2020, TO_DATE('29/06/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2045, TO_DATE('11/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2067, TO_DATE('10/04/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2054, TO_DATE('17/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2067, TO_DATE('01/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (900, 2032, TO_DATE('10/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2032, TO_DATE('29/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2078, TO_DATE('21/02/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2090, TO_DATE('10/04/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2105, TO_DATE('19/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2108, TO_DATE('16/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2020, TO_DATE('10/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (901, 2000, TO_DATE('03/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2045, TO_DATE('05/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2056, TO_DATE('07/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2067, TO_DATE('23/12/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2089, TO_DATE('22/12/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2100, TO_DATE('29/09/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (902, 2046, TO_DATE('30/10/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2000, TO_DATE('11/11/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2030, TO_DATE('19/08/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2056, TO_DATE('20/10/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2045, TO_DATE('29/11/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2090, TO_DATE('26/01/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2010, TO_DATE('10/02/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (903, 2001, TO_DATE('14/04/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2032, TO_DATE('25/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2022, TO_DATE('01/06/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2078, TO_DATE('15/03/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2076, TO_DATE('27/03/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2067, TO_DATE('10/03/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2056, TO_DATE('21/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (904, 2045, TO_DATE('10/11/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2014, TO_DATE('14/11/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2015, TO_DATE('18/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2056, TO_DATE('17/09/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2019, TO_DATE('01/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2109, TO_DATE('01/12/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2020, TO_DATE('01/02/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2100, TO_DATE('15/02/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (905, 2101, TO_DATE('18/09/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2044, TO_DATE('19/12/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2045, TO_DATE('12/09/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2078, TO_DATE('08/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2099, TO_DATE('04/06/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2087, TO_DATE('03/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2067, TO_DATE('07/03/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (906, 2097, TO_DATE('10/02/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2032, TO_DATE('11/01/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2048, TO_DATE('24/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2099, TO_DATE('28/08/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2044, TO_DATE('27/08/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2061, TO_DATE('10/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2010, TO_DATE('21/04/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (907, 2000, TO_DATE('22/07/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2044, TO_DATE('17/11/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2067, TO_DATE('06/12/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2089, TO_DATE('07/10/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2041, TO_DATE('10/11/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2001, TO_DATE('14/02/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2004, TO_DATE('11/10/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (908, 2058, TO_DATE('19/12/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2010, TO_DATE('20/05/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2034, TO_DATE('24/06/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2074, TO_DATE('28/04/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2089, TO_DATE('29/02/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2088, TO_DATE('10/10/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2002, TO_DATE('20/10/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (909, 2003, TO_DATE('11/12/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2004, TO_DATE('19/09/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2056, TO_DATE('08/09/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2045, TO_DATE('03/08/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2025, TO_DATE('02/02/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2067, TO_DATE('11/02/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2088, TO_DATE('19/09/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (910, 2080, TO_DATE('17/12/2015','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2004, TO_DATE('16/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2007, TO_DATE('19/09/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2012, TO_DATE('26/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2013, TO_DATE('25/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2067, TO_DATE('14/04/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2058, TO_DATE('10/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (911, 2000, TO_DATE('01/12/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2009, TO_DATE('16/05/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2015, TO_DATE('19/07/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2069, TO_DATE('23/10/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2045, TO_DATE('15/11/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2102, TO_DATE('24/11/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2060, TO_DATE('12/03/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (912, 2023, TO_DATE('11/02/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2044, TO_DATE('22/09/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2056, TO_DATE('20/01/2016','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2071, TO_DATE('13/01/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2090, TO_DATE('16/01/2018','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2021, TO_DATE('10/04/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2089, TO_DATE('20/03/2017','dd/mm/yyyy'));
INSERT INTO UsuarioLikes VALUES (913, 2077, TO_DATE('11/10/2015','dd/mm/yyyy'));

--Tabla UsuarioXSalto
INSERT INTO UsuarioXSalto VALUES (900, 718, TO_TIMESTAMP('10:45', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 734, TO_TIMESTAMP('11:23', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 777, TO_TIMESTAMP('11:56', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 712, TO_TIMESTAMP('11:54', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 800, TO_TIMESTAMP('11:32', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 768, TO_TIMESTAMP('14:36', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (900, 735, TO_TIMESTAMP('21:21', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 701, TO_TIMESTAMP('23:04', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 709, TO_TIMESTAMP('10:59', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 798, TO_TIMESTAMP('15:12', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 805, TO_TIMESTAMP('10:56', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 802, TO_TIMESTAMP('11:18', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 754, TO_TIMESTAMP('11:24', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (901, 721, TO_TIMESTAMP('11:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (902, 774, TO_TIMESTAMP('21:57', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (902, 729, TO_TIMESTAMP('11:41', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (902, 790, TO_TIMESTAMP('23:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (902, 712, TO_TIMESTAMP('18:31', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 715, TO_TIMESTAMP('18:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 716, TO_TIMESTAMP('11:57', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 718, TO_TIMESTAMP('12:31', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 749, TO_TIMESTAMP('17:10', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 769, TO_TIMESTAMP('11:43', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 736, TO_TIMESTAMP('11:20', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (903, 713, TO_TIMESTAMP('11:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (904, 777, TO_TIMESTAMP('18:44', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (904, 804, TO_TIMESTAMP('12:52', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 785, TO_TIMESTAMP('14:10', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 726, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 701, TO_TIMESTAMP('12:55', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 705, TO_TIMESTAMP('21:11', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 798, TO_TIMESTAMP('22:14', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (905, 711, TO_TIMESTAMP('22:19', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (906, 747, TO_TIMESTAMP('22:23', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (906, 723, TO_TIMESTAMP('22:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (906, 767, TO_TIMESTAMP('22:30', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (907, 784, TO_TIMESTAMP('15:04', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (907, 802, TO_TIMESTAMP('15:01', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (907, 804, TO_TIMESTAMP('15:03', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (907, 702, TO_TIMESTAMP('15:12', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (908, 706, TO_TIMESTAMP('15:08', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (908, 754, TO_TIMESTAMP('15:07', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (908, 734, TO_TIMESTAMP('15:12', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (908, 712, TO_TIMESTAMP('14:45', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (908, 803, TO_TIMESTAMP('13:56', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (909, 717, TO_TIMESTAMP('14:18', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (909, 720, TO_TIMESTAMP('13:05', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (910, 730, TO_TIMESTAMP('14:23', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (910, 719, TO_TIMESTAMP('17:27', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (910, 800, TO_TIMESTAMP('17:20', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (911, 744, TO_TIMESTAMP('17:10', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (911, 733, TO_TIMESTAMP('17:20', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (911, 782, TO_TIMESTAMP('17:10', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (911, 799, TO_TIMESTAMP('11:14', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (911, 787, TO_TIMESTAMP('11:18', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 717, TO_TIMESTAMP('11:34', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 714, TO_TIMESTAMP('11:37', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 718, TO_TIMESTAMP('11:32', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 739, TO_TIMESTAMP('11:57', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 801, TO_TIMESTAMP('10:48', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 802, TO_TIMESTAMP('19:32', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (912, 756, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 734, TO_TIMESTAMP('19:19', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 727, TO_TIMESTAMP('10:20', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 719, TO_TIMESTAMP('20:39', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 777, TO_TIMESTAMP('17:34', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 778, TO_TIMESTAMP('17:35', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 710, TO_TIMESTAMP('15:45', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 721, TO_TIMESTAMP('13:49', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 756, TO_TIMESTAMP('17:50', 'HH24:MI'));
INSERT INTO UsuarioXSalto VALUES (913, 720, TO_TIMESTAMP('17:21', 'HH24:MI'));

--Tabla UsuarioXReproduccion
INSERT INTO UsuarioXReproduccion VALUES (903, 715, TO_TIMESTAMP('10:50', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 716, TO_TIMESTAMP('10:57', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 718, TO_TIMESTAMP('10:31', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 749, TO_TIMESTAMP('10:10', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 714, TO_TIMESTAMP('10:43', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 736, TO_TIMESTAMP('10:20', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 715, TO_TIMESTAMP('10:46', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 768, TO_TIMESTAMP('10:42', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 750, TO_TIMESTAMP('10:55', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 763, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (903, 700, TO_TIMESTAMP('10:01', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (904, 777, TO_TIMESTAMP('10:44', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (904, 804, TO_TIMESTAMP('10:52', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 785, TO_TIMESTAMP('10:10', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 726, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 701, TO_TIMESTAMP('10:55', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 705, TO_TIMESTAMP('10:11', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 798, TO_TIMESTAMP('10:14', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (905, 711, TO_TIMESTAMP('10:19', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (906, 747, TO_TIMESTAMP('10:23', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (906, 723, TO_TIMESTAMP('10:50', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (906, 767, TO_TIMESTAMP('10:30', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (907, 784, TO_TIMESTAMP('10:04', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (907, 802, TO_TIMESTAMP('10:01', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (907, 804, TO_TIMESTAMP('10:03', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (907, 702, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (908, 706, TO_TIMESTAMP('10:08', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (908, 754, TO_TIMESTAMP('10:07', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (908, 734, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (908, 712, TO_TIMESTAMP('10:45', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (908, 719, TO_TIMESTAMP('10:56', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (909, 717, TO_TIMESTAMP('10:18', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (909, 720, TO_TIMESTAMP('10:05', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 730, TO_TIMESTAMP('10:23', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 719, TO_TIMESTAMP('10:27', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 800, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 801, TO_TIMESTAMP('10:54', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 777, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 768, TO_TIMESTAMP('10:21', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 789, TO_TIMESTAMP('10:02', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 722, TO_TIMESTAMP('10:09', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 711, TO_TIMESTAMP('10:13', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 805, TO_TIMESTAMP('10:04', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (910, 701, TO_TIMESTAMP('10:21', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (911, 744, TO_TIMESTAMP('10:10', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (911, 719, TO_TIMESTAMP('10:20', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (911, 716, TO_TIMESTAMP('10:19', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (911, 712, TO_TIMESTAMP('10:14', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (911, 787, TO_TIMESTAMP('10:18', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 717, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 714, TO_TIMESTAMP('10:37', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 718, TO_TIMESTAMP('10:32', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 739, TO_TIMESTAMP('10:57', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 801, TO_TIMESTAMP('10:48', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 802, TO_TIMESTAMP('10:32', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 756, TO_TIMESTAMP('10:14', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 744, TO_TIMESTAMP('10:32', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 733, TO_TIMESTAMP('10:56', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 740, TO_TIMESTAMP('10:43', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 701, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 777, TO_TIMESTAMP('10:12', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 754, TO_TIMESTAMP('10:11', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 755, TO_TIMESTAMP('10:02', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 743, TO_TIMESTAMP('10:05', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (912, 711, TO_TIMESTAMP('10:06', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 734, TO_TIMESTAMP('10:19', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 727, TO_TIMESTAMP('10:20', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 719, TO_TIMESTAMP('10:39', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 725, TO_TIMESTAMP('10:47', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 735, TO_TIMESTAMP('10:19', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 726, TO_TIMESTAMP('10:46', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 712, TO_TIMESTAMP('10:47', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 711, TO_TIMESTAMP('10:34', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 800, TO_TIMESTAMP('10:59', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 746, TO_TIMESTAMP('10:19', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 778, TO_TIMESTAMP('10:18', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 780, TO_TIMESTAMP('10:24', 'HH24:MI'));
INSERT INTO UsuarioXReproduccion VALUES (913, 713, TO_TIMESTAMP('10:24', 'HH24:MI'));
COMMIT;


---------------------------------------------------------------------SQL----------------------------------------------------------------------------------

/*  1. Novedades. Mostrar los títulos de las 5 álbumes más recientes del catálogo. */
WITH AlbumsRecientes (codAlbum, nombreAlbum) AS
        (SELECT A. codAlbum, A.nombreAlbum
         FROM Album A
         ORDER BY fechaLanzamiento DESC)
    SELECT A.nombreAlbum
    FROM AlbumsRecientes A
    WHERE ROWNUM <= 5;
         
/*  2.  Listar éxitos del país. Dado un país, cuales son las 30 canciones que más escucharon sus
        habitantes. Mostrar nombre artístico del intérprete principal, título de la canción y
        duración. */
WITH CancionesMasReproducidas (Interprete, Titulo, Duracion, codPais, Cancion, cantidadReproducidas) AS
            (SELECT P.nombreArtistico, N.titulo, EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion), C.codPais, U.codCancion, COUNT(U.codCancion)
             FROM UsuarioXReproduccion U, Cancion N, CancionXInterprete I, Interprete P, UsuarioC C
             WHERE U.codCancion = N.codCancion AND U.codCancion = I.codCancion AND N.codCancion = I.codCancion AND I.codInterprete = P.codInterprete 
                   AND U.codUsuario = C.codUsuario AND C.codPais = 103
             GROUP BY P.nombreArtistico, N.titulo, EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion), C.codPais, U.codCancion)
        SELECT C.Interprete, C.Titulo, C.Duracion
        FROM CancionesMasReproducidas C
        WHERE ROWNUM <= 30
        ORDER BY C.cantidadReproducidas DESC;
        
/*  3.  Listar canciones de un artista. Debe mostrar dado un artista, las 10 canciones que más
        se escucharon. Mostrar título, álbum o EP, año de lanzamiento y duración. */ --MEJOR ARTISTA = EMINEM
WITH ArtistasMasEscuchados(Titulo, Tipo, AnioLanzamiento, Duracion, codigoArtista, NombreArtista, Cancion, cantidadReproducidas) AS
            (SELECT N.titulo, A.tipo, EXTRACT(YEAR FROM A.fechaLanzamiento), EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion), P.codInterprete, 
                    P.nombreArtistico, U.codCancion, COUNT(U.codCancion)
             FROM UsuarioXReproduccion U, Cancion N, CancionXInterprete I, Interprete P, Album A
             WHERE U.codCancion = N.codCancion AND U.codCancion = I.codCancion AND N.codCancion = I.codCancion AND I.codInterprete = P.codInterprete 
                   AND N.codAlbum = A.codAlbum
                   AND P.codInterprete = 601
             GROUP BY N.titulo, A.tipo, EXTRACT(YEAR FROM A.fechaLanzamiento), EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion), P.codInterprete, 
                      P.nombreArtistico, U.codCancion)
        SELECT A.Titulo, A.Tipo, A.AnioLanzamiento, A.Duracion
        FROM ArtistasMasEscuchados A
        WHERE ROWNUM <= 10
        ORDER BY A.cantidadReproducidas DESC;
        

/*  4. Listar usuarios con suscripción gratuita que han saltado 5 o más canciones en la última
       hora. Mostrar solo la llave primaria del usuario. */
WITH SaltosUsuario (codigoUsuario, suscripcion, cancionesSaltadas) AS
            (SELECT U.codUsuario, U.tipoSuscripcion, COUNT(S.codCancion)
             FROM UsuarioXSalto S, UsuarioC U
             WHERE S.codUsuario = U.codUsuario AND U.tipoSuscripcion = 'Gratuita' 
                   AND EXTRACT(HOUR FROM SYSTIMESTAMP) - EXTRACT(HOUR FROM S.momentoSalto) - 5 <= 1
                   AND EXTRACT(MINUTE FROM SYSTIMESTAMP) - EXTRACT(MINUTE FROM S.momentoSalto) <= 0
             GROUP BY U.codUsuario, U.tipoSuscripcion)
        SELECT S.codigoUsuario
        FROM SaltosUsuario S
        ORDER BY S.cancionesSaltadas DESC;
       
/*  5. Últimos 10 likes que han hecho los amigos de un usuario específico. Mostrar nombre
       artístico del intérprete principal, título de la canción y duración. */
WITH LikesUsuario (InterpretePrincipal, Titulo, Duracion) AS
        (SELECT P.nombreArtistico, N.titulo, EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion)
        FROM UsuarioLikes U ,ListaReproduccion L, CancionXLista C, UsuarioXSeguidores S, CancionXInterprete I, Cancion N, Interprete P
        WHERE U.codRegistro = C.codRegistro AND L.codLista = C.codLista AND U.codUsuario = S.codUsuarioSeguidor AND I.codCancion = C.codCancion 
              AND I.codCancion = N.codCancion AND N.codCancion = C.codCancion AND I.codPrincipal = P.codInterprete
              AND S.codUsuarioSeguido = 900 AND L.codUsuario = 900
        ORDER BY U.fechaLike DESC)
    SELECT *
    FROM LikesUsuario
    WHERE ROWNUM <= 10;

/*  6. Suscripciones a punto de vencer. Listar la llave primaria del usuario al que se le vence la
       suscripción en lo próximo 5 días junto con la fecha en que se va a vencer la suscripción. */
WITH SuscripcionesVencer (codigoUsuario, fechaVencimiento) AS
        (SELECT U.codUsuario, U.fechaRenovacion
        FROM UsuarioC U
        WHERE EXTRACT(DAY FROM SYSDATE) - EXTRACT(DAY FROM U.fechaRenovacion) <= 5 
              AND EXTRACT(MONTH FROM SYSDATE) - EXTRACT(MONTH FROM U.fechaRenovacion) = 0
              AND EXTRACT(YEAR FROM SYSDATE) - EXTRACT(YEAR FROM U.fechaRenovacion) = 0)
    SELECT *
    FROM SuscripcionesVencer;

/*  7.  Mostrar listas de reproducción. Para un usuario mostrar todas las canciones en sus
        listas de reproducción ordenadas. Se debe mostrar el nombre de la lista de reproducción,
        nombre artístico del intérprete principal, título de la canción y duración. El orden debe
        estar dado por el nombre de la lista de reproducción y dentro de cada lista se deben
        ordenar las canciones por el orden determinado por el usuario. */
WITH ListasReproduccionUsuario (CodigoLista, NombreLista, Cancion, Titulo, Genero, Album, Interprete, Duracion, Orden) AS
            (SELECT L.codLista, L.nombreLista, C.codCancion, N.titulo, G.nombreGenero, A.nombreAlbum, P.nombreArtistico, 
                    EXTRACT(MINUTE FROM N.duracion) || ':' || EXTRACT(SECOND FROM N.duracion), L.Orden
             FROM ListaReproduccion L, CancionXLista C, Cancion N, Album A, Genero G, CancionXInterprete I, Interprete P
             WHERE L.codLista = C.codLista AND N.codCancion = C.codCancion AND N.codAlbum = A.codAlbum AND N.codGenero = G.codGenero
                   AND I.codCancion = C.codCancion AND I.codCancion = N.codCancion AND N.codCancion = C.codCancion AND I.codPrincipal = P.codInterprete
                   AND L.codUsuario = 904)
        SELECT L.nombreLista, L.Interprete, L.Titulo, L.Duracion
        FROM ListasReproduccionUsuario L
        ORDER BY L.NombreLista, (CASE L.Orden WHEN 'Titulo' THEN L.Titulo WHEN 'Album' THEN L.Album WHEN 'Genero' THEN L.Genero END);
        