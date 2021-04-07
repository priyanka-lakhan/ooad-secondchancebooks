USE `scbooks`;

CREATE TABLE IF NOT EXISTS `scbooks`.`book_listing_transactions` (
  `id` BIGINT(10) UNSIGNED NOT NULL,
  `book_listing_id` BIGINT(10) UNSIGNED NOT NULL,
  `book_id` BIGINT(10) UNSIGNED NOT NULL,
  `type` ENUM('SELL', 'BUY', 'RENT') NULL DEFAULT 'BUY',
  `created_by` BIGINT(10) UNSIGNED NOT NULL,
  `created_on` DATETIME NULL,
  `updated_on` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_listing_id_idx` (`book_listing_id` ASC) VISIBLE,
  INDEX `fk_book_id_t_idx` (`book_id` ASC) VISIBLE,
  INDEX `fk_created_user_idx` (`created_by` ASC) VISIBLE,
  CONSTRAINT `fk_listing_id`
    FOREIGN KEY (`book_listing_id`)
    REFERENCES `scbooks`.`book_listings` (`book_listing_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_id_t`
    FOREIGN KEY (`book_id`)
    REFERENCES `scbooks`.`books` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_created_user`
    FOREIGN KEY (`created_by`)
    REFERENCES `scbooks`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
