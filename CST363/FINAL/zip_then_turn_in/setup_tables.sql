DROP DATABASE IF EXISTS silicon_bay;
CREATE DATABASE silicon_bay;

USE silicon_bay;

CREATE TABLE `client` (
    `clientID`		INT(11) 	NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
    `clientName` 	VARCHAR(45) NOT NULL
);

CREATE TABLE `server` (
    `serverID`		INT(11)		NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
    `clientID`		INT(11) 	NOT NULL,
    `serverName`	VARCHAR(45)	NOT NULL,
    KEY `clientID_idx` (`clientID`),
    CONSTRAINT `clientID` FOREIGN KEY (`clientID`)
        REFERENCES `client` (`clientID`)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `backup` (
    `backupID`			INT(11)		NOT NULL PRIMARY KEY,
    `serverID`			INT(11)		NOT NULL,
    `backupSource`		LONGTEXT,
    `backupDestination` LONGTEXT,
    `frequency`			VARCHAR(32) DEFAULT NULL,
    `notes` 			LONGTEXT,
    KEY `serverID_idx` (`serverID`),
    CONSTRAINT `serverID` FOREIGN KEY (`serverID`)
        REFERENCES `server` (`serverID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE `role` (
    `roleID`	INT(11)		NOT NULL	AUTO_INCREMENT	PRIMARY KEY,
    `role`		VARCHAR(45) DEFAULT NULL
);

CREATE TABLE `serverrole` (
    `roleID` 	INT(11)	NOT NULL,
    `serverID`	INT(11) NOT NULL,
    KEY `roleID_idx` (`roleID`),
    KEY `serverID_idx` (`serverID`),
    CONSTRAINT `roleID` FOREIGN KEY (`roleID`)
        REFERENCES `role` (`roleID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `srsrerverID` FOREIGN KEY (`serverID`)
        REFERENCES `server` (`serverID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);