CREATE TABLE bbn_nurses(
nurse_id INT NOT NULL AUTO_INCREMENT ,
lastName VARCHAR( 255 ) NOT NULL ,
firstName VARCHAR( 255 ) ,
address VARCHAR( 255 ) ,
city VARCHAR( 255 ) ,
isRN INT,
phone VARCHAR( 225 ),
email VARCHAR( 225 ),
PRIMARY KEY ( nurse_id )
)

CREATE TABLE bbn_clients(
client_id INT NOT NULL AUTO_INCREMENT ,
lastName VARCHAR( 255 ) NOT NULL ,
firstName VARCHAR( 255 ) ,
address VARCHAR( 255 ) ,
city VARCHAR( 255 ) ,
numChildren VARCHAR( 225 ),
phone VARCHAR( 225 ),
email VARCHAR( 225 ),
PRIMARY KEY ( client_id )
)

CREATE TABLE bbn_nurse_sched(
nurse_id INT,
month VARCHAR( 225 ),
day VARCHAR ( 225 ),
year VARCHAR ( 225 ),
startTime VARCHAR ( 225 ),
endTime VARCHAR ( 225 ),
repeating VARCHAR ( 225 ),
location VARCHAR ( 225 )
)

CREATE TABLE bbn_client_request(
client_id INT,
month VARCHAR( 225 ),
day VARCHAR ( 225 ),
year VARCHAR ( 225 ),
startTime VARCHAR ( 225 ),
endTime VARCHAR ( 225 ),
repeating VARCHAR ( 225 ),
location VARCHAR ( 225 )
)