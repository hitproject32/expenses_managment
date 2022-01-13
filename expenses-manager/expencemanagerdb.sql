-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Jan 13, 2022 at 02:47 PM
-- Server version: 5.7.36
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `expencemanagerdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) COLLATE utf16_bin NOT NULL,
  `password_hash` varchar(100) COLLATE utf16_bin NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password_hash`, `date_created`) VALUES
(1, 'dudu', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-02 20:06:43'),
(2, 'Test', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 19:11:30'),
(3, '������', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 19:43:22'),
(4, '��\rf-��', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 19:43:52'),
(5, '(��C��k', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 19:44:25'),
(6, '��1��]^', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 20:00:30'),
(7, 'Test2', '�2S�j�k�-Jo�=�ƭ�A��J��v!��3�H���m�F����^���y~���Td⠺�', '2022-01-10 20:00:52');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `account_id`, `name`) VALUES
(1, 0, 'Food'),
(2, 0, 'Household'),
(3, 0, 'Loans'),
(4, 0, 'Automobile'),
(5, 0, 'Travel');

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `cost` float NOT NULL,
  `currency` varchar(5) COLLATE utf16_bin NOT NULL,
  `info` varchar(100) COLLATE utf16_bin NOT NULL,
  `date_created` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`id`, `account_id`, `category_id`, `cost`, `currency`, `info`, `date_created`) VALUES
(22, 1, 5, 54321, 'ILS', 'azaza', 1641841341961),
(23, 2, 4, 54321, 'ILS', 'Test55', 1641841903063),
(24, 2, 5, 5678, 'ILS', 'Test556', 1641841918676),
(25, 2, 1, 200, 'ILS', 'Testing', 1641843273496),
(26, 2, 1, 200, 'ILS', 'Testing', 1641843456781),
(27, 2, 1, 200, 'ILS', 'Testing', 1641843674898),
(28, 2, 1, 200, 'ILS', 'Testing', 1641843689425),
(29, 7, 3, 54321, 'ILS', 'Test13', 1641844865549),
(30, 7, 5, 12345, 'ILS', 'Test2', 1641844874919);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
