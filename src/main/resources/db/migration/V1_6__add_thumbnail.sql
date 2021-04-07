USE `scbooks`;

ALTER TABLE `scbooks`.`users`
ADD COLUMN `thumbnail_url` NVARCHAR(255) NULL AFTER `role`;
