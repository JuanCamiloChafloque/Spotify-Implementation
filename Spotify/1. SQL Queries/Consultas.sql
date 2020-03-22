--Proyecto Final Entrega 1
--Presentado Por: Juan Camilo Chafloque, Juan Pablo Linares, Juan Manuel Duarte

--DROP DE TABLAS
DROP TABLE CancionXInterprete;
DROP TABLE Cancion;
DROP TABLE Interprete;
DROP TABLE Genero;
DROP TABLE Album;
DROP TABLE PaisA;

--CREACIÓN DE TABLAS

--Tabla PaisA
CREATE TABLE PaisA(
    codigoPais         NUMERIC(4,0),
    nombrePais         VARCHAR2(50) NOT NULL,
    gentilicio         VARCHAR2(50) NOT NULL,
    PRIMARY KEY (codigoPais)
);
 
--Tabla Album
CREATE TABLE Album(
    codigoAlbum        NUMERIC(4,0),
    titulo             VARCHAR2(50) NOT NULL,
    anioLanzamiento    DATE NOT NULL,
    PRIMARY KEY (codigoAlbum)
);

--Tabla Genero
CREATE TABLE Genero(
    codigoGenero       NUMERIC(4,0),
    nombreGenero       VARCHAR2(50) NOT NULL,
    PRIMARY KEY (codigoGenero)
);
    
--Tabla Interprete
CREATE TABLE Interprete(
    codigoInterprete    NUMERIC(4,0),
    nombreArtistico     VARCHAR2(50),
    nombreReal          VARCHAR2(50) NOT NULL,
    codigoPais          NUMERIC(4,0) NOT NULL,
    fechaNacimiento     DATE NOT NULL,
    PRIMARY KEY (codigoInterprete),
    FOREIGN KEY (codigoPais) REFERENCES PaisA (codigoPais)
);
    
--Tabla Canción
CREATE TABLE Cancion(
    codigoCancion       NUMERIC(4,0), 
    titulo              VARCHAR2(50) NOT NULL, 
    codigoAlbum         NUMERIC(4,0) NOT NULL, 
    codigoGenero        NUMERIC(4,0) NOT NULL, 
    codCancionOriginal  NUMERIC(4,0),
    fechaLanzamiento    DATE NOT NULL,
    letra               VARCHAR2(100) NOT NULL,
    enVivo              CHAR NOT NULL,
    PRIMARY KEY(codigoCancion),
    FOREIGN KEY (codigoAlbum) REFERENCES Album (codigoAlbum),
    FOREIGN KEY (codigoGenero) REFERENCES Genero(codigoGenero),
    CHECK (enVivo IN ('S','N'))
);

ALTER TABLE Cancion ADD CONSTRAINT codCancionOriginalFK FOREIGN KEY (codCancionOriginal) REFERENCES Cancion (codigoCancion);

--Tabla CancionXInterprete
CREATE TABLE CancionXInterprete(
    codigoInterprete   NUMERIC(4,0),
    codigoCancion      NUMERIC(4,0),
    rol                VARCHAR2(20) DEFAULT 'Principal',
    PRIMARY KEY(codigoInterprete, codigoCancion),
    FOREIGN KEY(codigoInterprete) REFERENCES Interprete(codigoInterprete),
    FOREIGN KEY(codigoCancion) REFERENCES Cancion(codigoCancion) ON DELETE CASCADE,
    CHECK (rol IN ('Principal','Invitado'))
);

--INSERTS

--Paises(codigo,nombre_pais,gentilicio)
CREATE SEQUENCE valorPais
 START WITH     100
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 COMMIT;
 
 --Album (código, titulo, añoLanzamiento)
CREATE SEQUENCE valorAlbum
 START WITH     200
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 COMMIT;
 
 --Interprete(código, nombre_artístico, nombre real, codPais, fecha_nacimiento)
CREATE SEQUENCE valorInterprete
 START WITH     300
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 COMMIT;

--Cancion(cod, titulo, codAlbum, codGenero, fecha, codCancionOriginal, letra, envivo) 
CREATE SEQUENCE valorCancion
 START WITH     500
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 COMMIT;
 
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

INSERT INTO PaisA VALUES (valorPais.nextval, 'Estados Unidos', 'Americano');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Bruno Mars', 'Peter Gene Hernandez', valorPais.currval, to_date('08/10/1985','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Unorthodox JukeBox', to_date('06/12/2012','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Locked Out of Heaven', valorAlbum.currval, 406, NULL, to_date('1/10/2012','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'When I Was Your Man', valorAlbum.currval, 404, NULL, to_date('15/01/2013','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Treasure', valorAlbum.currval, 404, NULL, to_date('17/06/2013','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Gorilla', valorAlbum.currval, 408, NULL, to_date('26/08/2013','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Moonshine', valorAlbum.currval, 402, NULL, to_date('2/12/2013','dd/mm/yyyy'), 'Letra5', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Young Girls', valorAlbum.currval, 406, NULL, to_date('10/12/2013','dd/mm/yyyy'), 'Letra6', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
        INSERT INTO Album VALUES(ValorAlbum.nextval, '24K Magic', to_date('18/11/2016','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES (valorCancion.nextval, '24K Magic', valorAlbum.currval, 404, NULL, to_date('7/10/2016','dd/mm/yyyy'), 'Letra1', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Thats What I Like', valorAlbum.currval, 404, NULL, to_date('30/01/2017','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Versace On The Floor', valorAlbum.currval, 404, NULL, to_date('14/05/2017','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Chunky', valorAlbum.currval, 406, NULL, to_date('27/11/2017','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Finesse', valorAlbum.currval, 408, NULL, to_date('4/01/2018','dd/mm/yyyy'), 'Letra5', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Eminem', 'Marshall Bruce Mathers', valorPais.currval, to_date('17/10/1972','dd/mm/yyyy'));
        INSERT INTO Album VALUES(valorAlbum.nextval, 'Recovery', to_date('18/06/2010','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'No Love', valorAlbum.currval, 403, NULL, to_date('5/10/2010','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'NO LoVe', valorAlbum.currval, 403, NULL, to_date('5/10/2010','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Not Afraid', valorAlbum.currval, 402, NULL, to_date('29/04/2010','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Love The Way You Lie', valorAlbum.currval, 402, NULL, to_date('21/06/2010','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Space Bound', valorAlbum.currval, 402, NULL, to_date('14/06/2011','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES(valorAlbum.nextval, 'Revival', to_date('15/12/2017','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Walk on Water', valorAlbum.currval, 403, NULL, to_date('10/11/2017','dd/mm/yyyy'), 'Letra1', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'River', valorAlbum.currval, 402, NULL, to_date('5/01/2018','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'RiVeR', valorAlbum.currval, 402, NULL, to_date('5/01/2018','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Nowhere Fast', valorAlbum.currval, 403, NULL, to_date('27/03/2018','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES (valorCancion.nextval, 'Believe', valorAlbum.currval, 403, NULL, to_date('26/06/2018','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Issues', 'Tyler Carter', valorPais.currval, to_date('30/12/1991','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Black Diamonds', to_date('13/11/2012','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(7000, 'Black Diamonds', valorAlbum.currval, 409, NULL, to_date('12/03/2013','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 7000, 'Principal');
            INSERT INTO Cancion VALUES(7001, 'King Of Amarillo', valorAlbum.currval, 409, NULL, to_date('01/10/2012','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 7001, 'Principal');
            INSERT INTO Cancion VALUES(7002, 'The Worst Of Them', valorAlbum.currval, 407, NULL, to_date('17/12/2012','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 7002, 'Principal');
            INSERT INTO Cancion VALUES(7003, 'Princeton Ave', valorAlbum.currval, 407, NULL, to_date('31/10/2012','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 7003, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Diamond Dreams', to_date('18/11/2014','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'King Of Amarillo', valorAlbum.currval, 401, 7001, to_date('12/12/2014','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Princeton Ave', valorAlbum.currval, 401, 7003, to_date('12/12/2014','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Hooligans', valorAlbum.currval, 401, 7000, to_date('12/12/2014','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'The Worst Of Them', valorAlbum.currval, 401, 7002, to_date('12/12/2014','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Australia', 'Australiano');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Hands Like Houses', 'Trenton Woodley', valorPais.currval, to_date('26/09/1981','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Unimagine', to_date('23/07/2013','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(8000, 'Developments', valorAlbum.currval, 407, NULL, to_date('25/10/2013','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 8000, 'Principal');
            INSERT INTO Cancion VALUES(8001, 'Introduced Species', valorAlbum.currval, 407, NULL, to_date('12/07/2013','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 8001, 'Invitado');
            INSERT INTO Cancion VALUES(8002, 'ShapeShifters', valorAlbum.currval, 407, NULL, to_date('30/10/2013','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 8002, 'Principal');
            INSERT INTO Cancion VALUES(8003, 'No Parallels', valorAlbum.currval, 407, NULL, to_date('11/11/2013','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 8003, 'Principal');
            INSERT INTO Cancion VALUES(8004, 'A Tale of Outer Suburbia', valorAlbum.currval, 407, NULL, to_date('24/12/2013','dd/mm/yyyy'), 'Letra5', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, 8004, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'ReImagine', to_date('12/09/2014','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Recollect', valorAlbum.currval, 401, 8002, to_date('01/07/2013','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Revive', valorAlbum.currval, 401, 8004, to_date('01/11/2013','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Rediscover', valorAlbum.currval, 401, 8003, to_date('23/08/2013','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Release', valorAlbum.currval, 401, 8000, to_date('01/06/2013','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Reflect', valorAlbum.currval, 401, 8001, to_date('12/12/2013','dd/mm/yyyy'), 'Letra5', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Inglaterra', 'Ingles');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Paul McCartney', 'Paul McCartney', valorPais.currval, to_date('18/06/1942','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Band On The Run', to_date('07/12/1973','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Band On The Run', valorAlbum.currval, 401, NULL, to_date('28/06/1974','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Jet', valorAlbum.currval, 401, NULL, to_date('15/02/1974','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Helen Wheels', valorAlbum.currval, 401, NULL, to_date('26/10/1973','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Mrs Vandebilt', valorAlbum.currval, 401, NULL, to_date('13/01/1974','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Venus And Mars', to_date('30/05/1975','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Rock Show', valorAlbum.currval, 401, NULL, to_date('27/10/1975','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Venus And Mars', valorAlbum.currval, 401, NULL, to_date('27/10/1975','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Love In Song', valorAlbum.currval, 401, NULL, to_date('04/10/1975','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Letting Go', valorAlbum.currval, 401, NULL, to_date('16/05/1975','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'George Michael', 'Georgios Kyriacos Panayiotou', valorPais.currval, to_date('25/06/1963','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Faith', to_date('27/10/1987','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Hard Play', valorAlbum.currval, 404, NULL, to_date('12/10/1987','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Father Figure', valorAlbum.currval, 404, NULL, to_date('02/01/1988','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'One More Try', valorAlbum.currval, 404, NULL, to_date('11/04/1988','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Monkey', valorAlbum.currval, 408, NULL, to_date('03/07/1988','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Kissing A Fool', valorAlbum.currval, 408, NULL, to_date('21/11/1988','dd/mm/yyyy'), 'Letra5', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Older', to_date('14/05/1996','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Jesus To A Child', valorAlbum.currval, 404, NULL, to_date('13/10/1995','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'FastLove', valorAlbum.currval, 404, NULL, to_date('22/04/1996','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Spinning The Wheel', valorAlbum.currval, 404, NULL, to_date('19/10/1996','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Star People', valorAlbum.currval, 404, NULL, to_date('28/04/1996','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Francia', 'Frances'); --David Guetta y Phoenix
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'David Guetta', 'Pierre David Guetta', valorPais.currval, to_date('07/11/1967','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Nothing But The Beat', to_date('26/08/2011','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Titanium', valorAlbum.currval, 410, NULL, to_date('28/11/2011','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Without You', valorAlbum.currval, 410, NULL, to_date('27/09/2011','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Just One Last Time', valorAlbum.currval, 410, NULL, to_date('15/11/2012','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Play Hard', valorAlbum.currval, 411, NULL, to_date('22/03/2013','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Listen', to_date('24/11/2014','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Shot Me Down', valorAlbum.currval, 410, NULL, to_date('20/01/2014','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'BAD', valorAlbum.currval, 411, NULL, to_date('14/05/2014','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Sun Goes Down', valorAlbum.currval, 411, NULL, to_date('31/07/2015','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Dangerous', valorAlbum.currval, 411, NULL, to_date('07/10/2014','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Phoenix', 'Thomas Mars', valorPais.currval, to_date('21/11/1976','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Wolfgang Amadeus Phoenix', to_date('25/05/2009','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, '1901', valorAlbum.currval, 401, NULL, to_date('23/02/2009','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Liztomania', valorAlbum.currval, 401, NULL, to_date('07/07/2009','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Girlfriend', valorAlbum.currval, 412, NULL, to_date('10/04/2009','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Lasso', valorAlbum.currval, 412, NULL, to_date('01/06/2009','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Rome', valorAlbum.currval, 401, NULL, to_date('14/10/2009','dd/mm/yyyy'), 'Letra5', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Ti Amo', to_date('09/06/2017','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'J-Boy', valorAlbum.currval, 404, NULL, to_date('27/04/2017','dd/mm/yyyy'), 'Letra1', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Ti Amo', valorAlbum.currval, 404, NULL, to_date('18/05/2017','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'TI AMo', valorAlbum.currval, 404, NULL, to_date('18/05/2017','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Lovelife', valorAlbum.currval, 401, NULL, to_date('23/05/2017','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Telefono', valorAlbum.currval, 412, NULL, to_date('14/10/2017','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Suecia', 'Sueco');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Avicii', 'Tim Bergling', valorPais.currval, to_date('08/09/1989','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Stories', to_date('02/10/2015','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Waiting For Love', valorAlbum.currval, 410, NULL, to_date('22/05/2015','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Broken Arrows', valorAlbum.currval, 411, NULL, to_date('29/09/2015','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'For A Better Day', valorAlbum.currval, 411, NULL, to_date('28/08/2015','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Pure Grinding', valorAlbum.currval, 410, NULL, to_date('28/08/2015','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'PURE GrinDing', valorAlbum.currval, 410, NULL, to_date('28/08/2015','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Colombia', 'Colombiano');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Juanes', 'Juan Esteban Aristizábal', valorPais.currval, to_date('09/08/1972','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'P.A.R.C.E', to_date('07/12/2010','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Yerbatero', valorAlbum.currval, 401, NULL, to_date('10/06/2010','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Y No Regresas', valorAlbum.currval, 401, NULL, to_date('11/10/2010','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Regalito', valorAlbum.currval, 404, NULL, to_date('28/02/2011','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Soledad', valorAlbum.currval, 401, NULL, to_date('14/04/2011','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Loco De Amor', to_date('11/03/2014','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Luz', valorAlbum.currval, 401, NULL, to_date('16/12/2013','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Mil Pedazos', valorAlbum.currval, 404, NULL, to_date('03/03/2014','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Una Flor', valorAlbum.currval, 404, NULL, to_date('09/06/2014','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Verdad', valorAlbum.currval, 401, NULL, to_date('19/10/2014','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'JBalvin', 'José Álvaro Osorio', valorPais.currval, to_date('07/05/1985','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'El Negocio', to_date('25/03/2011','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Seguire Subiendo', valorAlbum.currval, 405, NULL, to_date('14/05/2011','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Un Sueño', valorAlbum.currval, 405, NULL, to_date('29/05/2011','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Pandereta', valorAlbum.currval, 405, NULL, to_date('23/10/2011','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Abrazame', valorAlbum.currval, 405, NULL, to_date('11/12/2011','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
        INSERT INTO Album VALUES (valorAlbum.nextval, 'Energía', to_date('24/06/2014','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Ginza', valorAlbum.currval, 405, NULL, to_date('17/07/2015','dd/mm/yyyy'), 'Letra1', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Bobo', valorAlbum.currval, 405, NULL, to_date('06/05/2016','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Safari', valorAlbum.currval, 405, NULL, to_date('17/06/2016','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Sigo Extrañandote', valorAlbum.currval, 404, NULL, to_date('16/02/2017','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
INSERT INTO PaisA VALUES (valorPais.nextval, 'Republica Dominicana', 'Dominicano');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Juan Luis Guerra', 'Juan Luis Guerra', valorPais.currval, to_date('07/06/1957','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'A Son De Guerra', to_date('08/06/2010','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Bachata en Fukuoka', valorAlbum.currval, 400, NULL, to_date('22/02/2010','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Mi Bendición', valorAlbum.currval, 400, NULL, to_date('31/05/2010','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Guagua', valorAlbum.currval, 400, NULL, to_date('12/07/2010','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Calle', valorAlbum.currval, 400, NULL, to_date('30/08/2010','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Vicente Garcia', 'Vicente Luis García', valorPais.currval, to_date('30/03/1983','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'A La Mar', to_date('08/06/2016','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'A La Mar', valorAlbum.currval, 400, NULL, to_date('22/05/2016','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'La Paloma', valorAlbum.currval, 400, NULL, to_date('19/09/2016','dd/mm/yyyy'), 'Letra2', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Mal de Amore', valorAlbum.currval, 400, NULL, to_date('13/11/2016','dd/mm/yyyy'), 'Letra3', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Carmesí', valorAlbum.currval, 400, NULL, to_date('22/01/2017','dd/mm/yyyy'), 'Letra4', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
INSERT INTO PaisA VALUES (valorPais.nextval, 'España', 'Español');
    INSERT INTO Interprete VALUES (valorInterprete.nextval, 'Alejandro Sanz', 'Alejandro Sánchez Pizarro', valorPais.currval, to_date('18/12/1968','dd/mm/yyyy'));
        INSERT INTO Album VALUES (valorAlbum.nextval, 'El Alma al Aire', to_date('26/09/2000','dd/mm/yyyy'));
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Cuando Nadie Me Ve', valorAlbum.currval, 406, NULL, to_date('28/08/2000','dd/mm/yyyy'), 'Letra1', 'N');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Invitado');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Qusiera Ser', valorAlbum.currval, 404, NULL, to_date('30/10/2000','dd/mm/yyyy'), 'Letra2', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'El Alma al Aire', valorAlbum.currval, 404, NULL, to_date('08/01/2000','dd/mm/yyyy'), 'Letra3', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');
            INSERT INTO Cancion VALUES(valorCancion.nextval, 'Llegó, llegó soledad', valorAlbum.currval, 406, NULL, to_date('12/03/2000','dd/mm/yyyy'), 'Letra4', 'S');
            INSERT INTO CancionXInterprete VALUES (valorInterprete.currval, valorCancion.currval, 'Principal');

---------------------------------------------------------------------SQL----------------------------------------------------------------------------------

/* 1. Nombre de intérprete, cantidad de canciones en las que ha participado con rol
principal, cantidad de canciones en las que ha sido invitado. SI el artista no ha participado
en ninguna canción o no ha tenido roles principales debe aparecer en el resultado con
valores en 0 donde corresponda */

WITH CancionesPrincipalArtista(nombreInterprete, nombreReal, cantidadCancionesPrincipal) AS
            (SELECT I.nombreArtistico, I.nombreReal, nvl(COUNT(X.codigoCancion), 0)
             FROM Interprete I , Cancion C, CancionXInterprete X
             WHERE I.codigoInterprete = X.codigoInterprete AND C.codigoCancion = X.codigoCancion
                  AND X.rol = 'Principal'
             GROUP BY I.nombreArtistico, I.nombreReal),
    CancionesInvitadoArtista(nombreInterprete, nombreReal, cantidadCancionesInvitado) AS
            (SELECT I.nombreArtistico, I.nombreReal, nvl(COUNT(X.codigoCancion), 0)
             FROM Interprete I , Cancion C, CancionXInterprete X
             WHERE I.codigoInterprete = X.codigoInterprete AND C.codigoCancion = X.codigoCancion
                  AND X.rol = 'Invitado'
             GROUP BY I.nombreArtistico, I.nombreReal)
    SELECT I.nombreArtistico, I.nombreReal, nvl(P.cantidadCancionesPrincipal, 0) AS CancionesPrincipal, nvl(C.cantidadCancionesInvitado, 0) AS CancionesInvitado
    FROM Interprete I LEFT OUTER JOIN CancionesPrincipalArtista P ON I.nombreArtistico = P.nombreInterprete LEFT OUTER JOIN
                    CancionesInvitadoArtista C ON I.nombreArtistico = C.nombreInterprete;


/*2. Nacionalidad (Gentilicio), cantidad de artistas (distintos) que han tenido roles
principales en las canciones */

SELECT P.gentilicio, COUNT(DISTINCT X.codigoInterprete) AS CantidadArtistasPrincipales
FROM PaisA P , Interprete I, Cancion C, CancionXInterprete X
WHERE I.codigoInterprete = X.codigoInterprete AND C.codigoCancion = X.codigoCancion AND P.codigoPais = I.codigoPais AND X.rol = 'Principal'
GROUP BY P.gentilicio;


/*3. Título del álbum, año de lanzamiento y rol predominante. El rol predominante es el
que más se repite en las canciones del álbum */

WITH AlbumXRolPrincipal (nombreAlbum, fechaLanzamiento, cantidadCancionesPrincipales) AS
            (SELECT A.titulo, A.anioLanzamiento, COUNT(X.codigoCancion)
             FROM Album A, Cancion C, CancionXInterprete X
             WHERE A.codigoAlbum = C.codigoAlbum AND C.codigoCancion = X.codigoCancion AND X.rol = 'Principal'
             GROUP BY A.titulo, A.anioLanzamiento),
    AlbumXRolInvitado (nombreAlbum, fechaLanzamiento, cantidadCancionesInvitado) AS
            (SELECT A.titulo, A.anioLanzamiento, COUNT(X.codigoCancion)
             FROM Album A, Cancion C, CancionXInterprete X
             WHERE A.codigoAlbum = C.codigoAlbum AND C.codigoCancion = X.codigoCancion AND X.rol = 'Invitado'
             GROUP BY A.titulo, A.anioLanzamiento)
             
    SELECT A.titulo, A.anioLanzamiento, 
           CASE WHEN nvl(P.cantidadCancionesPrincipales, 0) < nvl(I.cantidadCancionesInvitado, 0) THEN 'Invitado' ELSE 'Principal' END AS RolPredominante
    FROM Album A LEFT OUTER JOIN AlbumXRolPrincipal P ON A.titulo = P.nombreAlbum LEFT OUTER JOIN
                    AlbumXRolInvitado I ON A.titulo = I.nombreAlbum;


/*4. Top 5 de los artistas que interpretan más géneros. */

WITH ArtistasXGenero (nombreInterprete, nombreReal, cantidadGeneros) AS
            (SELECT I.nombreArtistico, I.nombreReal, COUNT(DISTINCT C.codigoGenero)
             FROM Interprete I, Genero G, Cancion C, CancionXInterprete X
             WHERE I.codigoInterprete = X.codigoInterprete AND G.codigoGenero = C.codigoGenero AND C.codigoCancion = X.codigoCancion
             GROUP BY I.nombreArtistico, I.nombreReal),
     ArtistasDescendentes (nombreInterprete, nombreReal, cantidadGeneros) AS
            (SELECT A.nombreInterprete, A.nombreReal, A.cantidadGeneros
             FROM ArtistasXGenero A
             GROUP BY A.nombreInterprete, A.nombreReal, A.cantidadGeneros
             ORDER BY A.cantidadGeneros DESC)
    SELECT *
    FROM ArtistasDescendentes
    WHERE ROWNUM <= 5;
             

/*6. Géneros cuyas canciones tiene más covers con géneros diferentes. Mostrar nombre
del género y cantidad de covers de otro género. (no incluir canciones en vivo) */

WITH Covers (codigoCancionCover, generoCancionCover, codigoCancionOriginal) AS
            (SELECT X.codigoCancion, G.nombreGenero, C.codCancionOriginal
             FROM Genero G, Cancion C, cancionXInterprete X
             WHERE G.codigoGenero = C.codigoGenero AND C.codigoCancion = X.codigoCancion AND C.codCancionOriginal IS NOT NULL AND C.envivo = 'N'),
    CoversXOriginal(codigoCancionCover, generoCancionCover, codigoCancionOriginal, generoCancionOriginal) AS
            (SELECT V.codigoCancionCover, V.generoCancionCover, V.codigoCancionOriginal, G.nombreGenero
             FROM Genero G, Covers V, Cancion C
             WHERE G.codigoGenero = C.codigoGenero AND V.codigoCancionOriginal = C.codigoCancion),
    CoverXOriginalDiferente (codigoCancionCover, generoCancionCover) AS
            (SELECT X.codigoCancionCover, X.generoCancionCover
             FROM CoversXOriginal X
             WHERE X.generoCancionCover <> X.generoCancionOriginal),
    NumeroCancionesGenero (genero, cantidadCanciones) AS
            (SELECT X.generoCancionCover, COUNT(X.codigoCancionCover)
             FROM CoverXOriginalDiferente X
             GROUP BY X.generoCancionCover)
    SELECT *
    FROM NumeroCancionesGenero N
    WHERE ROWNUM <= 1
    ORDER BY N.cantidadCanciones DESC;
            

/*7.  Artístas más representativos de cada género. El más representativo es el que tenga
más canciones originales. Mostrar género, intérprete y cantidad de canciones originales. */

WITH ArtistaOriginalXGenero (nombreGenero, nombreArtista, cantidadCancionesOriginales) AS
                (SELECT G.nombreGenero, I.nombreArtistico, COUNT(X.codigoCancion)
                 FROM Interprete I, Genero G, Cancion C, CancionXInterprete X
                 WHERE I.codigoInterprete = X.codigoInterprete AND G.codigoGenero = C.codigoGenero AND C.codigoCancion = X.codigoCancion
                       AND C.codCancionOriginal IS NULL
                 GROUP BY G.nombreGenero, I.nombreArtistico),
    CancionMayorXGenero (nombreGenero, maximaCancion) AS
                (SELECT X.nombreGenero, MAX(cantidadCancionesOriginales)
                 FROM ArtistaOriginalXGenero X
                 GROUP BY X.nombreGenero),
    ArtistaRepresentativoXGenero (nombreGenero, nombreArtista, CancionesOriginales) AS
                (SELECT A.nombreGenero, A.nombreArtista, A.cantidadCancionesOriginales
                 FROM ArtistaOriginalXGenero A JOIN CancionMayorXGenero C ON A.nombreGenero = C.nombreGenero
                 GROUP BY A.nombregenero, A.nombreArtista, A.cantidadCancionesOriginales, C.maximaCancion
                 HAVING A.cantidadCancionesOriginales = C.maximaCancion
                 ORDER BY A.nombreGenero DESC)
    SELECT *
    FROM ArtistaRepresentativoXGenero;


/*8.  Limpiar catálogo: En la relación canciones pueden existir registros duplicados. Se
consideran duplicados si tienen el mismo título, mismo año de publicación y mismo
género. El título se considera igual sin importar mayúsculas y minúsculas.
Si existen duplicados se deben eliminar los registros de la relación canción y solo dejar el
que tenga el menor código. Tenga en cuenta que posiblemente deba actualizar otras
relaciones también. */

--VERIFICAR CUALES SON LAS CANCIONES QUE TIENEN EL DUPLICADO (SE MUESTRAN UNICAMENTE LAS CANCIONES CON EL CODIGO MAYOR QUE SON LAS QUE SE VAN A ELIMINAR)
SELECT C.* 
FROM Cancion C 
WHERE ROWID >
(SELECT MIN(ROWID) 
 FROM Cancion C2 
 WHERE C.codigoGenero = C2.codigoGenero AND UPPER(C.titulo) = UPPER(C2.titulo) AND C.fechaLanzamiento = C2.fechaLanzamiento); 

--SE ELIMINAN LAS TUPLAS QUE APARECEN EN LA CONSULTA ANTERIOR
DELETE FROM Cancion C WHERE ROWID >
(SELECT MIN(ROWID) FROM Cancion C2 WHERE C.codigoGenero = C2.codigoGenero
AND UPPER(C.titulo) = UPPER(C2.titulo)
AND C.fechaLanzamiento = C2.fechaLanzamiento);


/*5. Año, número de canciones, trés géneros dominantes (los 3 géneros con más
canciones ese año) */

WITH CancionesXanio (anio, numeroCanciones) AS
            (SELECT YEAR(C.fechaLanzamiento), COUNT(C.codigoCancion)
             FROM Cancion C
             GROUP BY YEAR(C.fechaLanzamiento)),
    GeneroXAnio (anio, nombreGenero, numeroGeneros) AS
            (SELECT YEAR(C.fechaLanzamiento), G.nombreGenero, COUNT(DISTINCT C.codigoGenero)
             FROM Cancion C, Genero G
             WHERE C.codigoGenero = G.codigoGenero
             GROUP BY YEAR(C.fechaLanzamiento), G.nombreGenero),
    GeneroMayorXAnio (anio, nombreGenero, maximo) AS
            (SELECT G.anio, G.nombreGenero, MAX(G.numeroGeneros)
             FROM generoXAnio G
             GROUP BY G.anio, G.nombreGenero),
    GeneroMasFamosoXAnio (anio, numeroCanciones, nombreGenero) AS
            (SELECT C.anio, C.numeroCanciones, G.nombreGenero
             FROM CancionesXAnio C, GeneroMayorXAnio G
             GROUP BY C.anio, C.numeroCanciones, G.nombreGenero)
    SELECT *
    FROM GeneroMasFamosoXAnio;
    

