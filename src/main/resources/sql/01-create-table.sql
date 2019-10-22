DROP TABLE IF EXISTS cover;

CREATE TABLE cover(
   namefile VARCHAR (64) PRIMARY KEY NOT NULL,
   super_xci BOOLEAN ,
   nb_dlc DECIMAL ,
   version VARCHAR(64) ,
   fullpath VARCHAR (256) ,
   extension VARCHAR (8) ,
   size DECIMAL ,
   title VARCHAR (64),
   release_date VARCHAR (64) ,
   genre VARCHAR (64) ,
   developer VARCHAR (64) ,
   score DECIMAL ,
   rating VARCHAR (64) ,
   image BYTEA ,
   can_be_downloaded BOOLEAN ,
   created_on TIMESTAMP NOT NULL
);
