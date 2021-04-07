USE `scbooks`;

ALTER TABLE `scbooks`.`books`
ADD COLUMN `description` TEXT NULL AFTER `created_by`,
ADD COLUMN `publisher` TEXT NULL AFTER `description`,
ADD COLUMN `page_count` BIGINT NULL AFTER `publisher`,
ADD COLUMN `original_language` NVARCHAR(100) NULL AFTER `page_count`;
