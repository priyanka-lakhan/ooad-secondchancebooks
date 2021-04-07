USE `scbooks`;

CREATE TABLE IF NOT EXISTS `scbooks`.`books` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` TEXT(200) NOT NULL,
  `author` NVARCHAR(200) NULL,
  `isbn` NVARCHAR(50) NULL,
  `published_date` DATETIME NULL,
  `edition` NVARCHAR(10) NULL,
  `thumbnail_url` NVARCHAR(255) NULL,
  `category` NVARCHAR(100) NULL,
  `created_on` DATETIME NULL,
  `updated_on` DATETIME NULL,
  `created_by` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_book_user_id`
    FOREIGN KEY (`created_by`)
    REFERENCES `scbooks`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
