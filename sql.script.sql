-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: market_otomasyon
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alis`
--

DROP TABLE IF EXISTS `alis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alis` (
  `AlisID` int NOT NULL AUTO_INCREMENT,
  `UrunID` int DEFAULT NULL,
  `TedarikciID` int DEFAULT NULL,
  `AlisFiyati` decimal(10,2) NOT NULL,
  `ToplamMaliyet` decimal(12,2) NOT NULL,
  `Miktar` int NOT NULL,
  PRIMARY KEY (`AlisID`),
  KEY `UrunID` (`UrunID`),
  KEY `TedarikciID` (`TedarikciID`),
  CONSTRAINT `alis_ibfk_1` FOREIGN KEY (`UrunID`) REFERENCES `urun` (`UrunID`),
  CONSTRAINT `alis_ibfk_2` FOREIGN KEY (`TedarikciID`) REFERENCES `tedarikci` (`TedarikciID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kategori`
--

DROP TABLE IF EXISTS `kategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategori` (
  `KategoriID` int NOT NULL AUTO_INCREMENT,
  `KategoriAdi` varchar(100) NOT NULL,
  `KategoriAciklama` varchar(255) DEFAULT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`KategoriID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mudur`
--

DROP TABLE IF EXISTS `mudur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mudur` (
  `MudurID` int NOT NULL AUTO_INCREMENT,
  `M_Ad` varchar(100) NOT NULL,
  `M_Soyad` varchar(100) NOT NULL,
  `M_IletisimBilgisi` varchar(255) DEFAULT NULL,
  `Unvan` varchar(100) DEFAULT NULL,
  `KullaniciAdi` varchar(100) NOT NULL,
  `Sifre` varchar(255) NOT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`MudurID`),
  UNIQUE KEY `KullaniciAdi` (`KullaniciAdi`),
  UNIQUE KEY `UQ_KullaniciAdi` (`KullaniciAdi`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `musteri`
--

DROP TABLE IF EXISTS `musteri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musteri` (
  `MusteriID` int NOT NULL AUTO_INCREMENT,
  `Ad` varchar(100) NOT NULL,
  `Soyad` varchar(100) NOT NULL,
  `IletisimBilgisi` varchar(255) DEFAULT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`MusteriID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `personel`
--

DROP TABLE IF EXISTS `personel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personel` (
  `PersonelID` int NOT NULL AUTO_INCREMENT,
  `P_Ad` varchar(100) NOT NULL,
  `P_Soyad` varchar(100) NOT NULL,
  `P_IletisimBilgisi` varchar(255) DEFAULT NULL,
  `Unvan` varchar(100) DEFAULT NULL,
  `Gorev` varchar(255) DEFAULT NULL,
  `KullaniciAdi` varchar(100) NOT NULL,
  `Sifre` varchar(255) NOT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`PersonelID`),
  UNIQUE KEY `KullaniciAdi` (`KullaniciAdi`),
  UNIQUE KEY `UQ_KullaniciAdi` (`KullaniciAdi`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `siparis`
--

DROP TABLE IF EXISTS `siparis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `siparis` (
  `SiparisID` int NOT NULL AUTO_INCREMENT,
  `MusteriID` int DEFAULT NULL,
  `OdemeTuru` varchar(50) DEFAULT NULL,
  `SiparisTarihi` date NOT NULL,
  PRIMARY KEY (`SiparisID`),
  KEY `MusteriID` (`MusteriID`),
  CONSTRAINT `siparis_ibfk_1` FOREIGN KEY (`MusteriID`) REFERENCES `musteri` (`MusteriID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `siparisdetay`
--

DROP TABLE IF EXISTS `siparisdetay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `siparisdetay` (
  `SiparisDetayID` int NOT NULL AUTO_INCREMENT,
  `SiparisID` int DEFAULT NULL,
  `UrunID` int DEFAULT NULL,
  `Miktar` int NOT NULL,
  `BirimFiyat` decimal(10,2) NOT NULL,
  `ToplamTutar` decimal(12,2) NOT NULL,
  PRIMARY KEY (`SiparisDetayID`),
  KEY `SiparisID` (`SiparisID`),
  KEY `UrunID` (`UrunID`),
  CONSTRAINT `siparisdetay_ibfk_1` FOREIGN KEY (`SiparisID`) REFERENCES `siparis` (`SiparisID`),
  CONSTRAINT `siparisdetay_ibfk_2` FOREIGN KEY (`UrunID`) REFERENCES `urun` (`UrunID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tedarikci`
--

DROP TABLE IF EXISTS `tedarikci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tedarikci` (
  `TedarikciID` int NOT NULL AUTO_INCREMENT,
  `Ad` varchar(100) NOT NULL,
  `Soyad` varchar(100) NOT NULL,
  `IletisimBilgisi` varchar(255) DEFAULT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  `Firmaadi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TedarikciID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `urun`
--

DROP TABLE IF EXISTS `urun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `urun` (
  `UrunID` int NOT NULL AUTO_INCREMENT,
  `UrunAdi` varchar(100) NOT NULL,
  `AlisFiyat` decimal(10,2) NOT NULL,
  `SatisFiyat` decimal(10,2) NOT NULL,
  `StokMiktari` int NOT NULL,
  `KategoriID` int DEFAULT NULL,
  `TedarikciID` int DEFAULT NULL,
  `Aktif` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`UrunID`),
  KEY `KategoriID` (`KategoriID`),
  KEY `TedarikciID` (`TedarikciID`),
  CONSTRAINT `urun_ibfk_1` FOREIGN KEY (`KategoriID`) REFERENCES `kategori` (`KategoriID`),
  CONSTRAINT `urun_ibfk_2` FOREIGN KEY (`TedarikciID`) REFERENCES `tedarikci` (`TedarikciID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-03 21:32:45
