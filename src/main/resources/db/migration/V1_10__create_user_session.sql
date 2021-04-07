USE `scbooks`;

CREATE TABLE IF NOT EXISTS `scbooks`.`user_session` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(10) UNSIGNED NOT NULL,
  `session_string` NVARCHAR(100) NOT NULL,
  `created_on` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_user_session_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_session`
    FOREIGN KEY (`user_id`)
    REFERENCES `scbooks`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
