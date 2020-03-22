CREATE TABLE album (
    codalbum             NUMBER(4) NOT NULL,
    nombrealbum          VARCHAR2(50) NOT NULL,
    tipo                 VARCHAR2(50) NOT NULL,
    fechalanzamiento     DATE NOT NULL,
    empresa_codempresa   NUMBER(4) NOT NULL
);

ALTER TABLE album ADD CONSTRAINT album_pk PRIMARY KEY ( codalbum );

CREATE TABLE cancion (
    codcancion         NUMBER(4) NOT NULL,
    titulo             VARCHAR2(50) NOT NULL,
    duracion           DATE NOT NULL,
    genero_codgenero   NUMBER(4) NOT NULL,
    album_codalbum     NUMBER(4) NOT NULL
);

ALTER TABLE cancion ADD CONSTRAINT cancion_pk PRIMARY KEY ( codcancion );

CREATE TABLE cancionlikes (
    usuario_codusuario               NUMBER(4) NOT NULL,
    cancionxlista_cancionxlista_id   NUMBER NOT NULL
);

ALTER TABLE cancionlikes ADD CONSTRAINT cancionlikes_pk PRIMARY KEY ( usuario_codusuario,
                                                                      cancionxlista_cancionxlista_id );

CREATE TABLE cancionxinterprete (
    cancion_codcancion         NUMBER(4) NOT NULL,
    interprete_codinterprete   NUMBER(4) NOT NULL
);

ALTER TABLE cancionxinterprete ADD CONSTRAINT cancionxinterprete_pk PRIMARY KEY ( cancion_codcancion,
                                                                                  interprete_codinterprete );

CREATE TABLE cancionxlista (
    listareproduccion_codlista   NUMBER(4) NOT NULL,
    cancion_codcancion           NUMBER(4) NOT NULL,
    cancionxlista_id             NUMBER NOT NULL
);

ALTER TABLE cancionxlista ADD CONSTRAINT cancionxlista_pk PRIMARY KEY ( cancionxlista_id );

CREATE TABLE empresa (
    codempresa      NUMBER(4) NOT NULL,
    nombreempresa   VARCHAR2(50) NOT NULL
);

ALTER TABLE empresa ADD CONSTRAINT empresa_pk PRIMARY KEY ( codempresa );

CREATE TABLE genero (
    codgenero      NUMBER(4) NOT NULL,
    nombregenero   VARCHAR2(50) NOT NULL
);

ALTER TABLE genero ADD CONSTRAINT genero_pk PRIMARY KEY ( codgenero );

CREATE TABLE idioma (
    codidioma      NUMBER(4) NOT NULL,
    nombreidioma   VARCHAR2(50) NOT NULL
);

ALTER TABLE idioma ADD CONSTRAINT idioma_pk PRIMARY KEY ( codidioma );

CREATE TABLE idiomaxgenero (
    genero_codgenero   NUMBER(4) NOT NULL,
    idioma_codidioma   NUMBER(4) NOT NULL
);

ALTER TABLE idiomaxgenero ADD CONSTRAINT idiomaxgenero_pk PRIMARY KEY ( genero_codgenero,
                                                                        idioma_codidioma );

CREATE TABLE interprete (
    codinterprete     NUMBER(4) NOT NULL,
    nombrereal        VARCHAR2(50) NOT NULL,
    nombreartistico   VARCHAR2(50),
    paisc_codpais     NUMBER(4) NOT NULL
);

ALTER TABLE interprete ADD CONSTRAINT interprete_pk PRIMARY KEY ( codinterprete );

CREATE TABLE listareproduccion (
    codlista             NUMBER(4) NOT NULL,
    nombrelista          VARCHAR2(50) NOT NULL,
    visibilidad          VARCHAR2(50) NOT NULL,
    orden                VARCHAR2(50) NOT NULL,
    usuario_codusuario   NUMBER(4) NOT NULL
);

ALTER TABLE listareproduccion ADD CONSTRAINT listareproduccion_pk PRIMARY KEY ( codlista );

CREATE TABLE paisc (
    codpais      NUMBER(4) NOT NULL,
    nombrepais   VARCHAR2(50) NOT NULL
);

ALTER TABLE paisc ADD CONSTRAINT paisc_pk PRIMARY KEY ( codpais );

CREATE TABLE suscripcion (
    tiposuscripcion   VARCHAR2(50) NOT NULL,
    fechainicio       DATE,
    fecharenovacion   DATE
);

ALTER TABLE suscripcion ADD CONSTRAINT suscripcion_pk PRIMARY KEY ( tiposuscripcion );

CREATE TABLE usuario (
    codusuario                    NUMBER(4) NOT NULL,
    nombre                        VARCHAR2(50) NOT NULL,
    apellido                      VARCHAR2(50) NOT NULL,
    nickname                      VARCHAR2(50) NOT NULL,
    paisc_codpais                 NUMBER(4) NOT NULL,
    suscripcion_tiposuscripcion   VARCHAR2(50) NOT NULL
);

ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( codusuario );

CREATE TABLE usuarioxreproduccion (
    usuario_codusuario   NUMBER(4) NOT NULL,
    cancion_codcancion   NUMBER(4) NOT NULL
);

ALTER TABLE usuarioxreproduccion ADD CONSTRAINT usuarioxreproduccion_pk PRIMARY KEY ( usuario_codusuario,
                                                                                      cancion_codcancion );

CREATE TABLE usuarioxsalto (
    usuario_codusuario   NUMBER(4) NOT NULL,
    cancion_codcancion   NUMBER(4) NOT NULL
);

ALTER TABLE usuarioxsalto ADD CONSTRAINT usuarioxsalto_pk PRIMARY KEY ( usuario_codusuario,
                                                                        cancion_codcancion );

CREATE TABLE usuarioxseguidores (
    usuario_codusuario    NUMBER(4) NOT NULL,
    usuario_codusuario1   NUMBER(4) NOT NULL
);

ALTER TABLE usuarioxseguidores ADD CONSTRAINT usuarioxseguidores_pk PRIMARY KEY ( usuario_codusuario,
                                                                                  usuario_codusuario1 );

ALTER TABLE album
    ADD CONSTRAINT album_empresa_fk FOREIGN KEY ( empresa_codempresa )
        REFERENCES empresa ( codempresa );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_album_fk FOREIGN KEY ( album_codalbum )
        REFERENCES album ( codalbum );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_genero_fk FOREIGN KEY ( genero_codgenero )
        REFERENCES genero ( codgenero );

ALTER TABLE cancionlikes
    ADD CONSTRAINT cancionlikes_cancionxlista_fk FOREIGN KEY ( cancionxlista_cancionxlista_id )
        REFERENCES cancionxlista ( cancionxlista_id );

ALTER TABLE cancionlikes
    ADD CONSTRAINT cancionlikes_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinterprete_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinterprete_interprete_fk FOREIGN KEY ( interprete_codinterprete )
        REFERENCES interprete ( codinterprete );

ALTER TABLE cancionxlista
    ADD CONSTRAINT cancionxlista_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE cancionxlista
    ADD CONSTRAINT cancionxlista_listareproduccion_fk FOREIGN KEY ( listareproduccion_codlista )
        REFERENCES listareproduccion ( codlista );

ALTER TABLE idiomaxgenero
    ADD CONSTRAINT idiomaxgenero_genero_fk FOREIGN KEY ( genero_codgenero )
        REFERENCES genero ( codgenero );

ALTER TABLE idiomaxgenero
    ADD CONSTRAINT idiomaxgenero_idioma_fk FOREIGN KEY ( idioma_codidioma )
        REFERENCES idioma ( codidioma );

ALTER TABLE interprete
    ADD CONSTRAINT interprete_paisc_fk FOREIGN KEY ( paisc_codpais )
        REFERENCES paisc ( codpais );

ALTER TABLE listareproduccion
    ADD CONSTRAINT listareproduccion_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_paisc_fk FOREIGN KEY ( paisc_codpais )
        REFERENCES paisc ( codpais );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_suscripcion_fk FOREIGN KEY ( suscripcion_tiposuscripcion )
        REFERENCES suscripcion ( tiposuscripcion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxreproduccion
    ADD CONSTRAINT usuarioxreproduccion_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxreproduccion
    ADD CONSTRAINT usuarioxreproduccion_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuarioxsalto
    ADD CONSTRAINT usuarioxsalto_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

ALTER TABLE usuarioxsalto
    ADD CONSTRAINT usuarioxsalto_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuarioxseguidores
    ADD CONSTRAINT usuarioxseguidores_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxseguidores
    ADD CONSTRAINT usuarioxseguidores_usuario_fkv1 FOREIGN KEY ( usuario_codusuario1 )
        REFERENCES usuario ( codusuario );

ALTER TABLE album
    ADD CONSTRAINT album_empresa_fk FOREIGN KEY ( empresa_codempresa )
        REFERENCES empresa ( codempresa );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_album_fk FOREIGN KEY ( album_codalbum )
        REFERENCES album ( codalbum );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_genero_fk FOREIGN KEY ( genero_codgenero )
        REFERENCES genero ( codgenero );

ALTER TABLE cancionlikes
    ADD CONSTRAINT cancionlikes_cancionxlista_fk FOREIGN KEY ( cancionxlista_cancionxlista_id )
        REFERENCES cancionxlista ( cancionxlista_id );

ALTER TABLE cancionlikes
    ADD CONSTRAINT cancionlikes_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinterprete_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinterprete_interprete_fk FOREIGN KEY ( interprete_codinterprete )
        REFERENCES interprete ( codinterprete );

ALTER TABLE cancionxlista
    ADD CONSTRAINT cancionxlista_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE cancionxlista
    ADD CONSTRAINT cancionxlista_listareproduccion_fk FOREIGN KEY ( listareproduccion_codlista )
        REFERENCES listareproduccion ( codlista );

ALTER TABLE idiomaxgenero
    ADD CONSTRAINT idiomaxgenero_genero_fk FOREIGN KEY ( genero_codgenero )
        REFERENCES genero ( codgenero );

ALTER TABLE idiomaxgenero
    ADD CONSTRAINT idiomaxgenero_idioma_fk FOREIGN KEY ( idioma_codidioma )
        REFERENCES idioma ( codidioma );

ALTER TABLE interprete
    ADD CONSTRAINT interprete_paisc_fk FOREIGN KEY ( paisc_codpais )
        REFERENCES paisc ( codpais );

ALTER TABLE listareproduccion
    ADD CONSTRAINT listareproduccion_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_paisc_fk FOREIGN KEY ( paisc_codpais )
        REFERENCES paisc ( codpais );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_suscripcion_fk FOREIGN KEY ( suscripcion_tiposuscripcion )
        REFERENCES suscripcion ( tiposuscripcion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxreproduccion
    ADD CONSTRAINT usuarioxreproduccion_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxreproduccion
    ADD CONSTRAINT usuarioxreproduccion_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuarioxsalto
    ADD CONSTRAINT usuarioxsalto_cancion_fk FOREIGN KEY ( cancion_codcancion )
        REFERENCES cancion ( codcancion );

ALTER TABLE usuarioxsalto
    ADD CONSTRAINT usuarioxsalto_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

ALTER TABLE usuarioxseguidores
    ADD CONSTRAINT usuarioxseguidores_usuario_fk FOREIGN KEY ( usuario_codusuario )
        REFERENCES usuario ( codusuario );

--  ERROR: FK name length exceeds maximum allowed length(30) 

ALTER TABLE usuarioxseguidores
    ADD CONSTRAINT usuarioxseguidores_usuario_fkv1 FOREIGN KEY ( usuario_codusuario1 )
        REFERENCES usuario ( codusuario );

CREATE SEQUENCE cancionxlista_cancionxlista_id START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER cancionxlista_cancionxlista_id BEFORE
    INSERT ON cancionxlista
    FOR EACH ROW
    WHEN ( new.cancionxlista_id IS NULL )
BEGIN
    :new.cancionxlista_id := cancionxlista_cancionxlista_id.nextval;
END;
/