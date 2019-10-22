DROP TABLE IF EXISTS cover;

CREATE TABLE cover(
   namefile VARCHAR (256) PRIMARY KEY NOT NULL,
   super_xci BOOLEAN ,
   nb_dlc DECIMAL ,
   version VARCHAR(256) ,
   fullpath VARCHAR (512) ,
   extension VARCHAR (8) ,
   size DECIMAL ,
   title VARCHAR (256),
   release_date VARCHAR (256) ,
   genre TEXT ,
   developer VARCHAR (256) ,
   score DECIMAL ,
   rating VARCHAR (64) ,
   image BYTEA ,
   can_be_downloaded BOOLEAN ,
   created_on TIMESTAMP NOT NULL
);
