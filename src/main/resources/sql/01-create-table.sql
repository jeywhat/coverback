CREATE TABLE cover(
   title VARCHAR (64) PRIMARY KEY NOT NULL,
   description TEXT,
   release_date VARCHAR (64) ,
   genre VARCHAR (64) ,
   developer VARCHAR (64) ,
   score DECIMAL ,
   rating VARCHAR (64) ,
   image BYTEA ,
   created_on TIMESTAMP NOT NULL
);