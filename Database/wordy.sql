-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 10, 2023 at 09:42 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wordy`
--

-- --------------------------------------------------------

--
-- Table structure for table `gamehistory`
--

DROP TABLE IF EXISTS `gamehistory`;
CREATE TABLE IF NOT EXISTS `gamehistory` (
  `WordID` int NOT NULL AUTO_INCREMENT,
  `GameDate` date NOT NULL,
  `LongestWord` varchar(20) NOT NULL,
  `Username` varchar(15) NOT NULL,
  PRIMARY KEY (`WordID`),
  KEY `Username` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gamehistory`
--

INSERT INTO `gamehistory` (`WordID`, `GameDate`, `LongestWord`, `Username`) VALUES
(1, '2023-05-01', 'abandonment', 'Cj'),
(2, '2023-05-01', 'abbreviations', 'Flor'),
(3, '2023-05-02', 'abnormalities', 'Cj'),
(4, '2023-05-02', 'abstractedly', 'Flor'),
(5, '2023-05-03', 'abbreviations', 'Flor'),
(6, '2023-05-03', 'acceptability', 'Flor'),
(7, '2023-05-04', 'acclimatisation', 'Cj');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `PlayerID` varchar(50) NOT NULL,
  `Username` varchar(15) NOT NULL,
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `NumOfWins` int NOT NULL,
  PRIMARY KEY (`PlayerID`),
  UNIQUE KEY `Username` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`PlayerID`, `Username`, `FirstName`, `LastName`, `Password`, `NumOfWins`) VALUES
('P1', 'Jessy', 'Jessalyn', 'Masaba', '827ccb0eea8a706c4c34a16891f84e7b', 9),
('P2', 'Ban', 'Ivan', 'Pags', '202cb962ac59075b964b07152d234b70', 3),
('P3', 'Cj', 'Carl Joshua', 'Lalwet', '827ccb0eea8a706c4c34a16891f84e7b', 6),
('P4', 'Monster', 'Ginny', 'Dela Cruz', '827ccb0eea8a706c4c34a16891f84e7b', 5),
('P5', 'Beasty', 'Maricar', 'Bautista', '827ccb0eea8a706c4c34a16891f84e7b', 3),
('P6', 'Flor', 'Fredy', 'Ignacio', '827ccb0eea8a706c4c34a16891f84e7b', 4);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `gamehistory`
--
ALTER TABLE `gamehistory`
  ADD CONSTRAINT `gamehistory_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `player` (`Username`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
