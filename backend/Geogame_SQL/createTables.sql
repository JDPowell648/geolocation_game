DROP DATABASE IF EXISTS geogame;
CREATE DATABASE geogame;
use geogame;
CREATE TABLE LOGININFO(
    `name` VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL,
    `sessionId` VARCHAR(500) DEFAULT '',
    `expires` VARCHAR(20) DEFAULT '',
    PRIMARY KEY (`name`),
    UNIQUE (`name`)
);

CREATE TABLE USERS(
	`name` VARCHAR(50) NOT NULL,
    `rank` INT,
    gamesPlayed INT,
	averageScore INT,
    accuracy INT,
    PRIMARY KEY (`name`),
    UNIQUE (`name`)
);

CREATE TABLE LOCATION(
	location_id int NOT NULL,
    latitude double NOT NULL,
    longitude double NOT NULL,
    PRIMARY KEY (location_id),
    UNIQUE (location_id)
);

INSERT INTO LOGININFO(name, password) VALUES('dev', '$2a$10$Ob1.G57yhKMA.bUqICpRE.mujb39Ts.YSkn0k9pU/I6Sm7DaJI1xy');
INSERT INTO USERS(`name`, `rank`, gamesPlayed, averageScore, accuracy) VALUES('dev', "1","0","0","0");
INSERT INTO LOCATION(location_id, latitude,longitude) VALUES(1,42.02337824997835,-93.64945650100708);

DELIMITER //
CREATE PROCEDURE updateRank()
BEGIN
	SET @rc = 0;
	UPDATE USERS JOIN (SELECT @rc := @rc + 1 AS `rank`, `name` FROM USERS ORDER BY averageScore DESC)
	AS `order` USING(`name`) SET USERS.`rank` = `order`.`rank`;
END//
