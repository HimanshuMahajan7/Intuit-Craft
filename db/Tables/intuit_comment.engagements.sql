-- -----------------------------------------------------
-- Table `intuit_comment`.`engagements`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `intuit_comment`.`engagements`;

CREATE TABLE IF NOT EXISTS `intuit_comment`.`engagements` (
    `resource_id` VARCHAR(40),
    `user_id`     VARCHAR(40),
    `engagement`  TINYINT       NOT NULL,
    `created_at`  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` TIMESTAMP     NULL,
    PRIMARY KEY (`resource_id`, `user_id`),
    CONSTRAINT `engagements.user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `intuit_comment`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE INDEX `engagements_engagement_idx` ON `intuit_comment`.`engagements` (`engagement` DESC);