-- -----------------------------------------------------
-- Table `intuit_comment`.`users`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `intuit_comment`.`users`;

CREATE TABLE IF NOT EXISTS `intuit_comment`.`users`
(
    `id`          VARCHAR(40),
    `email`       VARCHAR(100) NOT NULL UNIQUE,
    `password`    VARCHAR(100) NOT NULL,
    `name`        VARCHAR(100) NOT NULL,
    `profile_pic` VARCHAR(255) NOT NULL,
    `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` TIMESTAMP    NULL,
    `status`      TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

CREATE INDEX `users_status_idx` ON `intuit_comment`.`users` (`status` DESC);