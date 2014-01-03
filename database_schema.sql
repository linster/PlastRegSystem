-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.33a-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for plastregsystem
CREATE DATABASE IF NOT EXISTS `plastregsystem` /*!40100 DEFAULT CHARACTER SET ascii COLLATE ascii_bin */;
USE `plastregsystem`;


-- Dumping structure for table plastregsystem.authorities
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` varchar(50) COLLATE ascii_bin NOT NULL,
  `authority` varchar(50) COLLATE ascii_bin NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii COLLATE=ascii_bin COMMENT='Stores which user has which role.';

-- Dumping data for table plastregsystem.authorities: ~1 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` (`username`, `authority`) VALUES
	('jim', 'ROLE_ADMIN');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;


-- Dumping structure for table plastregsystem.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) COLLATE ascii_bin NOT NULL,
  `password` varchar(50) COLLATE ascii_bin NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=ascii COLLATE=ascii_bin;

-- Dumping data for table plastregsystem.users: ~1 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
	('jim', '123', 1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
