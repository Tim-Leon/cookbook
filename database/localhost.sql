-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 30, 2022 at 06:47 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cookbook`
--
CREATE DATABASE IF NOT EXISTS `cookbook` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cookbook`;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `user_id` int(8) UNSIGNED NOT NULL,
  `recipe_id` int(8) UNSIGNED NOT NULL,
  `text` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`user_id`, `recipe_id`, `text`) VALUES
(6, 3, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `favorites`
--

CREATE TABLE `favorites` (
  `user_id` int(8) UNSIGNED NOT NULL,
  `recipe_id` int(8) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `favorites`
--

INSERT INTO `favorites` (`user_id`, `recipe_id`) VALUES
(6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `ingredients`
--

CREATE TABLE `ingredients` (
  `id` int(8) UNSIGNED NOT NULL,
  `name` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingredients`
--

INSERT INTO `ingredients` (`id`, `name`) VALUES
(1, 'Cabbage'),
(2, 'Citrus'),
(3, 'Tomatoes'),
(4, 'Pizza dough'),
(5, 'Cheese'),
(6, 'Tomatoe paste'),
(7, 'salt'),
(8, 'pepper'),
(9, 'oil'),
(10, 'flour'),
(11, 'garlic'),
(12, 'sugar'),
(13, 'water'),
(14, 'onion'),
(15, 'olive'),
(16, 'chicken'),
(17, 'juice'),
(18, 'milk'),
(19, 'lemon'),
(20, 'butter'),
(21, 'egg'),
(22, 'cheese'),
(23, 'wheat'),
(24, 'vegetable'),
(25, 'vanilla'),
(26, 'vinegar'),
(27, 'parsley'),
(28, 'honey'),
(29, 'soy'),
(30, 'wine'),
(31, 'seeds'),
(32, 'celery'),
(33, 'rice'),
(34, 'cinnamon'),
(35, 'tomato'),
(36, 'bread'),
(37, 'eggs'),
(38, 'onions'),
(39, 'yeast'),
(40, 'leaves'),
(41, 'broth'),
(42, 'tomatoes'),
(43, 'cream'),
(44, 'cloves'),
(45, 'thyme'),
(46, 'peeled'),
(47, 'ginger'),
(48, 'beans'),
(49, 'soda'),
(50, 'basil'),
(51, 'mushrooms'),
(52, 'apple'),
(53, 'parmesan'),
(54, 'yogurt'),
(55, 'stock'),
(56, 'bell'),
(57, 'oats'),
(58, 'sodium'),
(59, 'beef'),
(60, 'flakes'),
(61, 'carrot'),
(62, 'oregano'),
(63, 'chocolate'),
(64, 'cumin'),
(65, 'paprika'),
(66, 'sesame'),
(67, 'mustard'),
(68, 'spinach'),
(69, 'corn'),
(70, 'potatoes'),
(71, 'coconut'),
(72, 'carrots'),
(73, 'nutmeg'),
(74, 'cilantro'),
(75, 'raisins'),
(76, 'chili'),
(77, 'syrup'),
(78, 'peas'),
(79, 'peanut'),
(80, 'almond'),
(81, 'walnuts'),
(82, 'canned'),
(83, 'lime'),
(84, 'leaf'),
(85, 'pineapple'),
(86, 'margarine'),
(87, 'cabbage'),
(88, 'cucumber'),
(89, 'broccoli'),
(90, 'cornstarch'),
(91, 'zucchini'),
(92, 'coriander'),
(93, 'paste'),
(94, 'turkey'),
(95, 'banana'),
(96, 'almonds'),
(97, 'nuts'),
(98, 'maple'),
(99, 'cheddar'),
(100, 'cider'),
(101, 'scallions'),
(102, 'lettuce'),
(103, 'dill');

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `sender_id` int(8) NOT NULL,
  `reciever_id` int(8) NOT NULL,
  `text` text,
  `recipe_id` int(8) NOT NULL,
  `message_id` int(8) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`sender_id`, `reciever_id`, `text`, `recipe_id`, `message_id`) VALUES
(6, 5, 'kys', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `recipes`
--

CREATE TABLE `recipes` (
  `id` int(8) UNSIGNED NOT NULL,
  `user_id` int(8) UNSIGNED NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `summary` text,
  `description` text,
  `image` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipes`
--

INSERT INTO `recipes` (`id`, `user_id`, `name`, `summary`, `description`, `image`) VALUES
(7, 6, 'Pizza1', '1', '1', 0x89504e470d0a1a0a0000000d494844520000002000000020080300000044a48ac60000006f504c5445c0c0c0ffe45dffa754006a00008000009900e84600c93c00ff4d00ff984ffff7b2e2cba6ffbf40004000f5d63bce1e1edcac4ccb96318d5000ffca61ebe0da884b007e3f00e0a943ffd37aff6d2ec18d2cad7435e5b00effbe3dd7c4b9dcab48daab4dc18d2db0783cc49028c79329a37e8ce50000000174524e530040e6d866000000d249444154388ded91c792c23010443dc9c81632c1223960d2ff7f232d0cb50bc589d3d6167ded37f3a6a42cfbe63f869ef2a665511593dc4464c22f0cf144c56921eab434290c2cff109ea60ec369de82889a56b3529d901ffb39b6637305020b4c8398c30acb99e6376091f48562bc0483562b95008269311a96544310e086242808c119aea6e5e8887e95081c690e1ed430e4e8573e667762bdd932f14ecc6c67e916e2ed66fde893a569daaeebfbfa96beefbab669fcef8788fbe1706c4fe70b723eb5c7c3b08fd94be2533ef8abbf9c2b36200b2114ddaf510000000049454e44ae426082),
(8, 6, 'Pizza2', '1', '1', 0x89504e470d0a1a0a0000000d494844520000002000000020080300000044a48ac60000006f504c5445c0c0c0ffe45dffa754006a00008000009900e84600c93c00ff4d00ff984ffff7b2e2cba6ffbf40004000f5d63bce1e1edcac4ccb96318d5000ffca61ebe0da884b007e3f00e0a943ffd37aff6d2ec18d2cad7435e5b00effbe3dd7c4b9dcab48daab4dc18d2db0783cc49028c79329a37e8ce50000000174524e530040e6d866000000d249444154388ded91c792c23010443dc9c81632c1223960d2ff7f232d0cb50bc589d3d6167ded37f3a6a42cfbe63f869ef2a665511593dc4464c22f0cf144c56921eab434290c2cff109ea60ec369de82889a56b3529d901ffb39b6637305020b4c8398c30acb99e6376091f48562bc0483562b95008269311a96544310e086242808c119aea6e5e8887e95081c690e1ed430e4e8573e667762bdd932f14ecc6c67e916e2ed66fde893a569daaeebfbfa96beefbab669fcef8788fbe1706c4fe70b723eb5c7c3b08fd94be2533ef8abbf9c2b36200b2114ddaf510000000049454e44ae426082),
(9, 6, 'test', '1', '1', 0x89504e470d0a1a0a0000000d494844520000002000000020080300000044a48ac60000006f504c5445c0c0c0ffe45dffa754006a00008000009900e84600c93c00ff4d00ff984ffff7b2e2cba6ffbf40004000f5d63bce1e1edcac4ccb96318d5000ffca61ebe0da884b007e3f00e0a943ffd37aff6d2ec18d2cad7435e5b00effbe3dd7c4b9dcab48daab4dc18d2db0783cc49028c79329a37e8ce50000000174524e530040e6d866000000d249444154388ded91c792c23010443dc9c81632c1223960d2ff7f232d0cb50bc589d3d6167ded37f3a6a42cfbe63f869ef2a665511593dc4464c22f0cf144c56921eab434290c2cff109ea60ec369de82889a56b3529d901ffb39b6637305020b4c8398c30acb99e6376091f48562bc0483562b95008269311a96544310e086242808c119aea6e5e8887e95081c690e1ed430e4e8573e667762bdd932f14ecc6c67e916e2ed66fde893a569daaeebfbfa96beefbab669fcef8788fbe1706c4fe70b723eb5c7c3b08fd94be2533ef8abbf9c2b36200b2114ddaf510000000049454e44ae426082);

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredients`
--

CREATE TABLE `recipe_ingredients` (
  `recipe_id` int(8) UNSIGNED NOT NULL,
  `ingredient_id` int(8) UNSIGNED NOT NULL,
  `amount` int(8) DEFAULT NULL,
  `unit` enum('kg','g','l','dl','cl','ml') DEFAULT NULL,
  `portions` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `recipe_ingredients`
--

INSERT INTO `recipe_ingredients` (`recipe_id`, `ingredient_id`, `amount`, `unit`, `portions`) VALUES
(7, 3, 100, 'g', 1),
(7, 4, 100, 'g', 1),
(7, 6, 100, 'g', 1),
(8, 4, 100, 'g', 1),
(8, 85, 50, 'g', 1),
(9, 4, 1, 'g', 1),
(9, 85, 50, 'g', 1);

-- --------------------------------------------------------

--
-- Table structure for table `shoppinglist`
--

CREATE TABLE `shoppinglist` (
  `user_id` int(8) UNSIGNED NOT NULL,
  `ingredient_id` int(8) UNSIGNED NOT NULL,
  `amount` int(8) UNSIGNED NOT NULL,
  `week` int(8) UNSIGNED NOT NULL,
  `unit` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `shoppinglist`
--

INSERT INTO `shoppinglist` (`user_id`, `ingredient_id`, `amount`, `week`, `unit`) VALUES
(6, 3, 100, 21, 'g'),
(6, 4, 100, 21, 'g'),
(6, 6, 100, 21, 'g');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `recipe_id` int(8) NOT NULL,
  `name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`recipe_id`, `name`) VALUES
(-1, 'desert'),
(-1, 'gluten free'),
(-1, 'lactose free'),
(-1, 'main course'),
(-1, 'starter'),
(-1, 'sweets'),
(-1, 'vegan'),
(-1, 'vegetarian'),
(7, 'pizza'),
(8, 'pizza'),
(9, 'pizza');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(8) UNSIGNED NOT NULL,
  `username` varchar(32) NOT NULL,
  `display_name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `display_name`, `password`, `role`) VALUES
(5, 'admin', 'admi', '123', 'ADMIN'),
(6, 'Kazys', 'kazis', '123', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `weeklydinnerlist`
--

CREATE TABLE `weeklydinnerlist` (
  `dinnerid` int(8) UNSIGNED NOT NULL,
  `user_id` int(8) UNSIGNED NOT NULL,
  `recipe_id` int(8) UNSIGNED NOT NULL,
  `week` int(8) UNSIGNED NOT NULL,
  `date` varchar(20) NOT NULL,
  `servings` int(8) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `weeklydinnerlist`
--

INSERT INTO `weeklydinnerlist` (`dinnerid`, `user_id`, `recipe_id`, `week`, `date`, `servings`) VALUES
(1, 6, 7, 21, '2022-05-23', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `favorites`
--
ALTER TABLE `favorites`
  ADD PRIMARY KEY (`user_id`,`recipe_id`);

--
-- Indexes for table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recipe_ingredients`
--
ALTER TABLE `recipe_ingredients`
  ADD PRIMARY KEY (`recipe_id`,`ingredient_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`recipe_id`,`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`,`username`);

--
-- Indexes for table `weeklydinnerlist`
--
ALTER TABLE `weeklydinnerlist`
  ADD PRIMARY KEY (`dinnerid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `message_id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `weeklydinnerlist`
--
ALTER TABLE `weeklydinnerlist`
  MODIFY `dinnerid` int(8) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Database: `marvel`
--
CREATE DATABASE IF NOT EXISTS `marvel` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `marvel`;

-- --------------------------------------------------------

--
-- Stand-in structure for view `allpowers`
-- (See below for the actual view)
--
CREATE TABLE `allpowers` (
`name` varchar(64)
,`super_power` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `characters`
--

CREATE TABLE `characters` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `side` varchar(64) NOT NULL,
  `super_power` varchar(255) NOT NULL,
  `release_year` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `characters`
--

INSERT INTO `characters` (`id`, `name`, `side`, `super_power`, `release_year`) VALUES
(1, 'Spider-Man', 'Superhero', 'Swinging, web shooting, strength, speed, wallcrawling, superhuman reflexes', 1962),
(2, 'Wolverine', 'Superhero', 'Regeneration, strength, durability, stamina', 1974),
(3, 'Colossus', 'Superhero', 'Metal Skin, Strength', 1975),
(4, 'Doctor Doom', 'Supervillain', 'Technopathy, Armor Grants, Sorcery', 1962),
(5, 'Silver Surfer', 'Superhero', 'Cosmic Power, power absorbtion', 1966),
(6, 'Captain America', 'Superhero', 'superhuman strength, superhuman durability, shield', 1941),
(7, 'Dormammu', 'Supervillain', 'energy projection, time travel, teleportation, life-force absorption, elemental contol', 1964),
(8, 'Mole Man', 'Supervillain', 'genius level intelect, radar sense, japanese martial arts', 1961),
(9, 'Morbius', 'Anti-hero', 'Genius level instellect, biochemist, biologist, superhuman strength, speed, senses, flight hypnotism, accelerated healing', 1971),
(10, 'Red Skull', 'Supervillain', 'Genius-level intellect expert tactician, strategist, skilled hand-to-hand combatant and marksman', 1941),
(11, 'Anti-Venom', 'Anti-hero', 'superhuman strength, durability, stamina, accelerated healing factor,  wall-crawling, web-generating abilities, spider-senses and camouflage', 2008),
(12, 'Hulk', 'Superhero', 'Incredible superhuman strength, durability, and healing factor', 1962),
(13, 'Magneto', 'Supervillain', 'Magnetism manipulation, shoot electromagnetic pulses, create force fields', 1963),
(14, 'Sabretooth', 'Supervillain', 'accelerated healing, super strength, durability, agility', 1977),
(15, 'Doctor Strange', 'Superhero', 'conjure myriad spells', 1963),
(16, 'Black Panther', 'Superhero', 'knowledge, strength and every experience of every previous Black Panther, superhuman strength, endurance, speed, agility, reflexes, stamina and senses', 1966),
(17, 'Juggernaut', 'Supervillain', 'Superhuman strength, stamina, and durability Invulnerability via mystical force field Immunity to mental attacks via his helmet', 1965),
(18, 'Deadpool', 'Anti-hero', 'Superhuman strength, stamina, durability, healing factor, teleportation, immortality', 1991),
(19, 'Thor', 'Superhero', 'superhuman strength, speed, agility, durability, immunity to diseases, possesion of mjolnir ( power over storms and generates energy blasts known as anti-force)', 1962),
(20, 'Green Goblin', 'Supervillain', 'Superhuman strength, stamina, durability, agility, reflexes, regeneration, genius-level intellect, master hand-to-hand combatant, a Goblin Glider which has a variety of offensive weapons', 1964),
(21, 'Mysterio', 'Supervillain', 'expert designer of special effects devices and stage illusions, a master hypnotist and magician and an amateur chemist and roboticist', 1964),
(22, 'Black Cat', 'Supervillain', 'reflexes, agility, stamina of an Olympic level acrobat', 1979),
(23, 'Iron-Man', 'Superhero', 'rich billionaire playboy', 1962);

-- --------------------------------------------------------

--
-- Table structure for table `comic_books`
--

CREATE TABLE `comic_books` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `year` int(5) NOT NULL,
  `artist` varchar(64) NOT NULL,
  `writer` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comic_books`
--

INSERT INTO `comic_books` (`id`, `name`, `year`, `artist`, `writer`) VALUES
(1, 'Symbiote Spider-Man Vol 1 #5', 2019, 'Greg Land', 'Peter David '),
(2, 'Amazing Spider-Man Vol 1 #194', 1979, 'Keith Pollard', 'Marv Wolfman'),
(3, 'Doctor Strange (2015) #389', 2018, 'Niko Henrichon', 'Donny Cates '),
(4, 'The Infinity Gauntlet #1', 1991, 'George PÃ©rez', 'Jim Starlin'),
(5, 'Tales of Suspense Vol 1 #39', 1962, 'Don Heck', 'Stan Lee, Larry Lieber'),
(6, 'Incredible Hulk Vol 1 #181', 1974, 'Herb Trimpe', 'Len Wein'),
(7, 'Civil War: Front Line Vol 1 #1', 2006, 'Ramon Bachs', 'Paul Jenkins'),
(8, 'Amazing Fantasies Vol 1 #15', 1962, 'Steve Dikto', 'Stan Lee, Steve Dikto'),
(9, 'Iron Man Vol 4 #4', 2005, 'Adi Granov', 'Warren Ellis'),
(10, 'Avengers Vol 1 #1', 1963, 'Jack Kirby', 'Stan Lee');

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(64) NOT NULL,
  `director` varchar(64) NOT NULL,
  `release_year` int(5) NOT NULL,
  `comic_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`id`, `title`, `director`, `release_year`, `comic_id`) VALUES
(1, 'Eternals', 'ChloÃ© Zhao', 2021, NULL),
(2, 'Avengers: Endgame', 'Anthony Russo, Joe Russo', 2019, 5),
(3, 'Thor: Ragnarok', 'Taika Waititi', 2017, NULL),
(4, 'Iron Man 3', 'Shane Black', 2013, 10),
(5, 'Avengers: Age of Ultron', 'Joss Whedon', 2015, NULL),
(6, 'Captain America: Civil War', 'Anthony Russo, Joe Russo', 2016, 7),
(7, 'Shang-Shi and The Legends of the Ten Rings', 'Destin Daniel Cretton', 2021, NULL),
(8, 'Ant-Man', 'Peyton Reed', 2015, NULL),
(9, 'Captain Marvel', 'Anna Boden, Ryan Fleck', 2019, NULL),
(10, 'Doctor Strange', 'Scott Derrickson', 2016, 4),
(11, 'The Avengers', 'Joss Whedon', 2012, 11);

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE `participants` (
  `comic_id` int(11) NOT NULL,
  `character_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `participants`
--

INSERT INTO `participants` (`comic_id`, `character_id`) VALUES
(1, 1),
(1, 21),
(2, 1),
(2, 22),
(3, 7),
(3, 15),
(4, 1),
(4, 5),
(4, 12),
(4, 15),
(5, 23),
(6, 2),
(6, 12),
(7, 1),
(7, 23),
(8, 1),
(9, 3),
(10, 19),
(10, 12),
(10, 23);

-- --------------------------------------------------------

--
-- Structure for view `allpowers`
--
DROP TABLE IF EXISTS `allpowers`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `allpowers`  AS SELECT `ch`.`name` AS `name`, `ch`.`super_power` AS `super_power` FROM `characters` AS `ch` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `characters`
--
ALTER TABLE `characters`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comic_books`
--
ALTER TABLE `comic_books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);
--
-- Database: `venckus`
--
CREATE DATABASE IF NOT EXISTS `venckus` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `venckus`;

-- --------------------------------------------------------

--
-- Table structure for table `planets`
--

CREATE TABLE `planets` (
  `name` varchar(128) NOT NULL,
  `rotation_period` varchar(128) NOT NULL,
  `orbital_period` varchar(128) NOT NULL,
  `diameter` varchar(128) NOT NULL,
  `climate` varchar(128) NOT NULL,
  `gravity` varchar(128) NOT NULL,
  `terrain` varchar(128) NOT NULL,
  `surface_water` varchar(128) NOT NULL,
  `population` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `planets`
--

INSERT INTO `planets` (`name`, `rotation_period`, `orbital_period`, `diameter`, `climate`, `gravity`, `terrain`, `surface_water`, `population`) VALUES
('Alderaan', '24', '364', '12500', 'temperate', '1 standard', 'grasslands, mountains', '40', '2000000000'),
('Aleen Minor', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA'),
('Bespin', '12', '5110', '118000', 'temperate', '1.5 (surface), 1 standard (Cloud City)', 'gas giant', '0', '6000000'),
('Bestine IV', '26', '680', '6400', 'temperate', 'NA', 'rocky islands, oceans', '98', '62000000'),
('Cato Neimoidia', '25', '278', '0', 'temperate, moist', '1 standard', 'mountains, fields, forests, rock arches', 'NA', '10000000'),
('Cerea', '27', '386', 'NA', 'temperate', '1', 'verdant', '20', '450000000'),
('Champala', '27', '318', 'NA', 'temperate', '1', 'oceans, rainforests, plateaus', 'NA', '3500000000'),
('Chandrila', '20', '368', '13500', 'temperate', '1', 'plains, forests', '40', '1200000000'),
('Concord Dawn', 'NA', 'NA', 'NA', 'NA', 'NA', 'jungles, forests, deserts', 'NA', 'NA'),
('Corellia', '25', '329', '11000', 'temperate', '1 standard', 'plains, urban, hills, forests', '70', '3000000000'),
('Coruscant', '24', '368', '12240', 'temperate', '1 standard', 'cityscape, mountains', 'NA', '1000000000000'),
('Dagobah', '23', '341', '8900', 'murky', 'N/A', 'swamp, jungles', '8', 'NA'),
('Dantooine', '25', '378', '9830', 'temperate', '1 standard', 'oceans, savannas, mountains, grasslands', 'NA', '1000'),
('Dathomir', '24', '491', '10480', 'temperate', '0.9', 'forests, deserts, savannas', 'NA', '5200'),
('Dorin', '22', '409', '13400', 'temperate', '1', 'NA', 'NA', 'NA'),
('Endor', '18', '402', '4900', 'temperate', '0.85 standard', 'forests, mountains, lakes', '8', '30000000'),
('Eriadu', '24', '360', '13490', 'polluted', '1 standard', 'cityscape', 'NA', '22000000000'),
('Felucia', '34', '231', '9100', 'hot, humid', '0.75 standard', 'fungus forests', 'NA', '8500000'),
('Geonosis', '30', '256', '11370', 'temperate, arid', '0.9 standard', 'rock, desert, mountain, barren', '5', '100000000000'),
('Glee Anselm', '33', '206', '15600', 'tropical, temperate', '1', 'lakes, islands, swamps, seas', '80', '500000000'),
('Haruun Kal', '25', '383', '10120', 'temperate', '0.98', 'toxic cloudsea, plateaus, volcanoes', 'NA', '705300'),
('Hoth', '23', '549', '7200', 'frozen', '1.1 standard', 'tundra, ice caves, mountain ranges', '100', 'NA'),
('Iktotch', '22', '481', 'NA', 'arid, rocky, windy', '1', 'rocky', 'NA', 'NA'),
('Iridonia', '29', '413', 'NA', 'NA', 'NA', 'rocky canyons, acid pools', 'NA', 'NA'),
('Jakku', 'NA', 'NA', 'NA', 'NA', 'NA', 'deserts', 'NA', 'NA'),
('Kalee', '23', '378', '13850', 'arid, temperate, tropical', '1', 'rainforests, cliffs, canyons, seas', 'NA', '4000000000'),
('Kamino', '27', '463', '19720', 'temperate', '1 standard', 'ocean', '100', '1000000000'),
('Kashyyyk', '26', '381', '12765', 'tropical', '1 standard', 'jungle, forests, lakes, rivers', '60', '45000000'),
('Malastare', '26', '201', '18880', 'arid, temperate, tropical', '1.56', 'swamps, deserts, jungles, mountains', 'NA', '2000000000'),
('Mirial', 'NA', 'NA', 'NA', 'NA', 'NA', 'deserts', 'NA', 'NA'),
('Mon Cala', '21', '398', '11030', 'temperate', '1', 'oceans, reefs, islands', '100', '27000000000'),
('Mustafar', '36', '412', '4200', 'hot', '1 standard', 'volcanoes, lava rivers, mountains, caves', '0', '20000'),
('Muunilinst', '28', '412', '13800', 'temperate', '1', 'plains, forests, hills, mountains', '25', '5000000000'),
('Mygeeto', '12', '167', '10088', 'frigid', '1 standard', 'glaciers, mountains, ice canyons', 'NA', '19000000'),
('NA', '0', '0', '0', 'NA', 'NA', 'NA', 'NA', 'NA'),
('Naboo', '26', '312', '12120', 'temperate', '1 standard', 'grassy hills, swamps, forests, mountains', '12', '4500000000'),
('Nal Hutta', '87', '413', '12150', 'temperate', '1 standard', 'urban, oceans, swamps, bogs', 'NA', '7000000000'),
('Ojom', 'NA', 'NA', 'NA', 'frigid', 'NA', 'oceans, glaciers', '100', '500000000'),
('Ord Mantell', '26', '334', '14050', 'temperate', '1 standard', 'plains, seas, mesas', '10', '4000000000'),
('Polis Massa', '24', '590', '0', 'artificial temperate ', '0.56 standard', 'airless asteroid', '0', '1000000'),
('Quermia', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA'),
('Rodia', '29', '305', '7549', 'hot', '1 standard', 'jungles, oceans, urban, swamps', '60', '1300000000'),
('Ryloth', '30', '305', '10600', 'temperate, arid, subartic', '1', 'mountains, valleys, deserts, tundra', '5', '1500000000'),
('Saleucami', '26', '392', '14920', 'hot', 'NA', 'caves, desert, mountains, volcanoes', 'NA', '1400000000'),
('Serenno', 'NA', 'NA', 'NA', 'NA', 'NA', 'rainforests, rivers, mountains', 'NA', 'NA'),
('Shili', 'NA', 'NA', 'NA', 'temperate', '1', 'cities, savannahs, seas, plains', 'NA', 'NA'),
('Skako', '27', '384', 'NA', 'temperate', '1', 'urban, vines', 'NA', '500000000000'),
('Socorro', '20', '326', '0', 'arid', '1 standard', 'deserts, mountains', 'NA', '300000000'),
('Stewjon', 'NA', 'NA', '0', 'temperate', '1 standard', 'grass', 'NA', 'NA'),
('Sullust', '20', '263', '12780', 'superheated', '1', 'mountains, volcanoes, rocky deserts', '5', '18500000000'),
('Tatooine', '23', '304', '10465', 'arid', '1 standard', 'desert', '1', '200000'),
('Tholoth', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA'),
('Toydaria', '21', '184', '7900', 'temperate', '1', 'swamps, lakes', 'NA', '11000000'),
('Trandosha', '25', '371', '0', 'arid', '0.62 standard', 'mountains, seas, grasslands, deserts', 'NA', '42000000'),
('Troiken', 'NA', 'NA', 'NA', 'NA', 'NA', 'desert, tundra, rainforests, mountains', 'NA', 'NA'),
('Tund', '48', '1770', '12190', 'NA', 'NA', 'barren, ash', 'NA', '0'),
('Umbara', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA'),
('Utapau', '27', '351', '12900', 'temperate, arid, windy', '1 standard', 'scrublands, savanna, canyons, sinkholes', '0.9', '95000000'),
('Vulpter', '22', '391', '14900', 'temperate, artic', '1', 'urban, barren', 'NA', '421000000'),
('Yavin IV', '24', '4818', '10200', 'temperate, tropical', '1 standard', 'jungle, rainforests', '8', '1000'),
('Zolan', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA');

-- --------------------------------------------------------

--
-- Table structure for table `species`
--

CREATE TABLE `species` (
  `name` varchar(128) NOT NULL,
  `classification` varchar(128) NOT NULL,
  `designation` varchar(128) NOT NULL,
  `average_height` varchar(128) NOT NULL,
  `skin_colors` varchar(128) NOT NULL,
  `hair_colors` varchar(128) NOT NULL,
  `eye_colors` varchar(128) NOT NULL,
  `average_lifespan` varchar(128) NOT NULL,
  `language` varchar(128) NOT NULL,
  `homeworld` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `species`
--

INSERT INTO `species` (`name`, `classification`, `designation`, `average_height`, `skin_colors`, `hair_colors`, `eye_colors`, `average_lifespan`, `language`, `homeworld`) VALUES
('Aleena', 'reptile', 'sentient', '80', 'blue, gray', 'none', 'NA', '79', 'Aleena', 'Aleen Minor'),
('Besalisk', 'amphibian', 'sentient', '178', 'brown', 'none', 'yellow', '75', 'besalisk', 'Ojom'),
('Cerean', 'mammal', 'sentient', '200', 'pale pink', 'red, blond, black, white', 'hazel', 'NA', 'Cerean', 'Cerea'),
('Chagrian', 'amphibian', 'sentient', '190', 'blue', 'none', 'blue', 'NA', 'Chagria', 'Champala'),
('Clawdite', 'reptilian', 'sentient', '180', 'green, yellow', 'none', 'yellow', '70', 'Clawdite', 'Zolan'),
('Droid', 'artificial', 'sentient', 'NA', 'NA', 'NA', 'NA', 'indefinite', 'NA', 'NA'),
('Dug', 'mammal', 'sentient', '100', 'brown, purple, grey, red', 'none', 'yellow, blue', 'NA', 'Dugese', 'Malastare'),
('Ewok', 'mammal', 'sentient', '100', 'brown', 'white, brown, black', 'orange, brown', 'NA', 'Ewokese', 'Endor'),
('Geonosian', 'insectoid', 'sentient', '178', 'green, brown', 'none', 'green, hazel', 'NA', 'Geonosian', 'Geonosis'),
('Gungan', 'amphibian', 'sentient', '190', 'brown, green', 'none', 'orange', 'NA', 'Gungan basic', 'Naboo'),
('Human', 'mammal', 'sentient', '180', 'caucasian, black, asian, hispanic', 'blonde, brown, black, red', 'brown, blue, green, hazel, grey, amber', '120', 'Galactic Basic', 'Coruscant'),
('Hutt', 'gastropod', 'sentient', '300', 'green, brown, tan', 'NA', 'yellow, red', '1000', 'Huttese', 'Nal Hutta'),
('Iktotchi', 'NA', 'sentient', '180', 'pink', 'none', 'orange', 'NA', 'Iktotchese', 'Iktotch'),
('Kaleesh', 'reptile', 'sentient', '170', 'brown, orange, tan', 'none', 'yellow', '80', 'Kaleesh', 'Kalee'),
('Kaminoan', 'amphibian', 'sentient', '220', 'grey, blue', 'none', 'black', '80', 'Kaminoan', 'Kamino'),
('Kel Dor', 'NA', 'sentient', '180', 'peach, orange, red', 'none', 'black, silver', '70', 'Kel Dor', 'Dorin'),
('Mirialan', 'mammal', 'sentient', '180', 'yellow, green', 'black, brown', 'blue, green, red, yellow, brown, orange', 'NA', 'Mirialan', 'Mirial'),
('Mon Calamari', 'amphibian', 'sentient', '160', 'red, blue, brown, magenta', 'none', 'yellow', 'NA', 'Mon Calamarian', 'Mon Cala'),
('Muun', 'mammal', 'sentient', '190', 'grey, white', 'none', 'black', '100', 'Muun', 'Muunilinst'),
('Nautolan', 'amphibian', 'sentient', '180', 'green, blue, brown, red', 'none', 'black', '70', 'Nautila', 'Glee Anselm'),
('Neimodian', 'NA', 'sentient', '180', 'grey, green', 'none', 'red, pink', 'NA', 'Neimoidia', 'Cato Neimoidia'),
('Pau\'an', 'mammal', 'sentient', '190', 'grey', 'none', 'black', '700', 'Utapese', 'Utapau'),
('Quermian', 'mammal', 'sentient', '240', 'white', 'none', 'yellow', '86', 'Quermian', 'Quermia'),
('Rodian', 'sentient', 'reptilian', '170', 'green, blue', 'NA', 'black', 'NA', 'Galactic Basic', 'Rodia'),
('Skakoan', 'mammal', 'sentient', 'NA', 'grey, green', 'none', 'NA', 'NA', 'Skakoan', 'Skako'),
('Sullustan', 'mammal', 'sentient', '180', 'pale', 'none', 'black', 'NA', 'Sullutese', 'Sullust'),
('Tholothian', 'mammal', 'sentient', 'NA', 'dark', 'NA', 'blue, indigo', 'NA', 'NA', 'Tholoth'),
('Togruta', 'mammal', 'sentient', '180', 'red, white, orange, yellow, green, blue', 'none', 'red, orange, yellow, green, blue, black', '94', 'Togruti', 'Shili'),
('Toong', 'NA', 'sentient', '200', 'grey, green, yellow', 'none', 'orange', 'NA', 'Tundan', 'Tund'),
('Toydarian', 'mammal', 'sentient', '120', 'blue, green, grey', 'none', 'yellow', '91', 'Toydarian', 'Toydaria'),
('Trandoshan', 'reptile', 'sentient', '200', 'brown, green', 'none', 'yellow, orange', 'NA', 'Dosh', 'Trandosha'),
('Twi\'lek', 'mammals', 'sentient', '200', 'orange, yellow, blue, green, pink, purple, tan', 'none', 'blue, brown, orange, pink', 'NA', 'Twi\'leki', 'Ryloth'),
('Vulptereen', 'NA', 'sentient', '100', 'grey', 'none', 'yellow', 'NA', 'vulpterish', 'Vulpter'),
('Wookiee', 'mammal', 'sentient', '210', 'gray', 'black, brown', 'blue, green, yellow, brown, golden, red', '400', 'Shyriiwook', 'Kashyyyk'),
('Xexto', 'NA', 'sentient', '125', 'grey, yellow, purple', 'none', 'black', 'NA', 'Xextese', 'Troiken'),
('Yoda\'s species', 'mammal', 'sentient', '66', 'green, yellow', 'brown, white', 'brown, green, yellow', '900', 'Galactic basic', 'NA'),
('Zabrak', 'mammal', 'sentient', '180', 'pale, brown, red, orange, yellow', 'black', 'brown, orange', 'NA', 'Zabraki', 'Iridonia');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `planets`
--
ALTER TABLE `planets`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `species`
--
ALTER TABLE `species`
  ADD PRIMARY KEY (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
