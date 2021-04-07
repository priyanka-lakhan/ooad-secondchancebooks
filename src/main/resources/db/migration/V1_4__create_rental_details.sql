USE `scbooks`;

CREATE TABLE IF NOT EXISTS `scbooks`.`rental_details` (
  `rental_details_id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date` DATETIME NULL,
  `end_date` DATETIME NULL,
  PRIMARY KEY (`rental_details_id`));
