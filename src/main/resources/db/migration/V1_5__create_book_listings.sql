USE `scbooks`;

CREATE TABLE IF NOT EXISTS `scbooks`.`book_listings` (
  `book_listing_id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT(10) UNSIGNED NOT NULL,
  `heading` TEXT NOT NULL,
  `description` TEXT NULL,
  `status` ENUM('SOLD', 'AVAILABLE') NULL DEFAULT 'AVAILABLE',
  `type` ENUM('SELL', 'RENT') NULL DEFAULT 'SELL',
  `price` BIGINT(10) NOT NULL,
  `thumbnail_url` NVARCHAR(255) NULL,
  `created_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `rental_details_id` BIGINT(10) UNSIGNED NULL,
  `created_by` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`book_listing_id`),
  INDEX `fk_book_listing_book_id_idx` (`book_id` ASC) VISIBLE,
  INDEX `fk_created_by_listing_idx` (`created_by` ASC) VISIBLE,
  INDEX `fk_rental_details_id_idx` (`rental_details_id` ASC) VISIBLE,
  CONSTRAINT `fk_book_listing_book_id`
    FOREIGN KEY (`book_id`)
    REFERENCES `scbooks`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_created_by_listing`
    FOREIGN KEY (`created_by`)
    REFERENCES `scbooks`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rental_details_id`
    FOREIGN KEY (`rental_details_id`)
    REFERENCES `scbooks`.`rental_details` (`rental_details_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
