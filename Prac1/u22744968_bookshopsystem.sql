-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.11.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table u22744968_bookshopsystem.books
CREATE TABLE IF NOT EXISTS `books` (
  `ISBN` varchar(13) NOT NULL,
  `Title` varchar(100) NOT NULL,
  `NameOfAuthor` varchar(50) NOT NULL,
  `SurnameOfAuthor` varchar(50) NOT NULL,
  `StudentBorrowing` varchar(8) DEFAULT NULL,
  `BorrowDate` date DEFAULT NULL,
  `Available` binary(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ISBN`),
  KEY `StudentBorrowing` (`StudentBorrowing`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`StudentBorrowing`) REFERENCES `students` (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='list of books';

-- Dumping data for table u22744968_bookshopsystem.books: ~20 rows (approximately)
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` (`ISBN`, `Title`, `NameOfAuthor`, `SurnameOfAuthor`, `StudentBorrowing`, `BorrowDate`, `Available`) VALUES
	('9780060256654', 'Where the Wild Things Are', 'Maurice', 'Sendak', '21001234', '2023-03-07', _binary 0x30),
	('9780061120084', 'To Kill a Mockingbird', 'Harper', 'Lee', NULL, NULL, _binary 0x31),
	('9780061122415', 'Go Set a Watchman', 'Harper', 'Lee', NULL, NULL, _binary 0x31),
	('9780064401883', 'Island of the Blue Dolphins', 'Scott', 'O\'Dell', NULL, NULL, _binary 0x31),
	('9780131103627', 'Database Systems: The Complete Book', 'Hector', 'Garcia-Molina', '20004321', '2023-03-01', _binary 0x30),
	('9780132145375', 'Introduction to Algorithms', 'Thomas', 'Cormen', '20001234', '2023-01-01', _binary 0x30),
	('9780134023212', 'Computer Networks', 'Andrew', 'Tanenbaum', NULL, NULL, _binary 0x31),
	('9780134092669', 'Computer Systems: A Programmer\'s Perspective', 'Randal', 'Bryant', NULL, NULL, _binary 0x31),
	('9780134481268', 'Computer Networking: A Top-Down Approach', 'James', 'Kurose', NULL, NULL, _binary 0x31),
	('9780136019282', 'Operating System Concepts', 'Abraham', 'Silberschatz', '22004321', '2023-01-20', _binary 0x30),
	('9780136085317', 'Artificial Intelligence: A Modern Approach', 'Stuart', 'Russell', NULL, NULL, _binary 0x31),
	('9780140449334', 'Pride and Prejudice', 'Jane', 'Austen', NULL, NULL, _binary 0x31),
	('9780141187761', '1984', 'George', 'Orwell', NULL, NULL, _binary 0x31),
	('9780201633610', 'Design Patterns', 'Erich', 'Gamma', NULL, NULL, _binary 0x31),
	('9780262033848', 'Introduction to Algorithms', 'Charles', 'Leiserson', '21001234', '2023-01-10', _binary 0x30),
	('9780316769174', 'The Catcher in the Rye', 'J.D.', 'Salinger', '22005432', '2023-01-20', _binary 0x30),
	('9780439785969', 'Harry Potter and the Half-Blood Prince', 'J.K.', 'Rowling', '22004321', '2023-02-15', _binary 0x30),
	('9780545010221', 'Harry Potter and the Philosopher\'s Stone', 'J.K.', 'Rowling', NULL, NULL, _binary 0x31),
	('9780547928227', 'The Hobbit', 'J.R.R.', 'Tolkien', NULL, NULL, _binary 0x31),
	('9781118842080', 'Computer Organization and Design', 'David', 'Patterson', NULL, NULL, _binary 0x31);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;

-- Dumping structure for table u22744968_bookshopsystem.students
CREATE TABLE IF NOT EXISTS `students` (
  `StudentID` varchar(8) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Surname` varchar(50) NOT NULL,
  `Degree` varchar(10) NOT NULL,
  `PhoneNumber` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='List of students';

-- Dumping data for table u22744968_bookshopsystem.students: ~20 rows (approximately)
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` (`StudentID`, `Name`, `Surname`, `Degree`, `PhoneNumber`) VALUES
	('20001234', 'Siya', 'Mhlongo', 'BSc', '0821234567'),
	('20004321', 'Sipho', 'Mthembu', 'BEng', '0832345678'),
	('21001234', 'Isabella', 'Thompson', 'BSc', '0820123456'),
	('21002143', 'Emily', 'Smith', 'BEng', '0842345678'),
	('21005432', 'Olivia', 'Jones', 'BSc', '0814567890'),
	('21005678', 'Thando', 'Ngubane', 'BA', '0843456789'),
	('21006543', 'Zinhle', 'Dube', 'BSc', '0817890123'),
	('21008765', 'Nkosi', 'Dlamini', 'BSc', '0814567890'),
	('21009012', 'Sophia', 'Wilson', 'BA', '0826789012'),
	('21009876', 'Ntokozo', 'Nkosi', 'BEng', '0848901234'),
	('21039876', 'Ava', 'Martin', 'BEng', '0848901234'),
	('22002143', 'Sibusiso', 'Ndlovu', 'BA', '0839012345'),
	('22003210', 'Aphiwe', 'Nxumalo', 'BA', '0826789012'),
	('22004321', 'Jacob', 'Taylor', 'BSc', '0817890123'),
	('22005432', 'Nomthandazo', 'Mdlalose', 'BSc', '0820123456'),
	('22005678', 'Michael', 'Anderson', 'BA', '0839012345'),
	('22006543', 'James', 'Davis', 'BEng', '0835678901'),
	('22008765', 'Liam', 'Johnson', 'BSc', '0831234567'),
	('22009012', 'Lwazi', 'Nkosi', 'BEng', '0835678901'),
	('22093210', 'William', 'Brown', 'BA', '0823456789');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
