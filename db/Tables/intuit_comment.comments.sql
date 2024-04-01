-- -----------------------------------------------------
-- Table `intuit_comment`.`comments`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `intuit_comment`.`comments`;

CREATE TABLE IF NOT EXISTS `intuit_comment`.`comments`
(
    `id`            VARCHAR(40),
    `parent_id`     VARCHAR(40)  NOT NULL,
    `user_id`       VARCHAR(40)  NOT NULL,
    `text`          VARCHAR(255) NOT NULL,
    `comment_level` INT          NULL,
    `reply_count`   INT          NULL,
    `like_count`    INT          NULL,
    `dislike_count` INT          NULL,
    `created_at`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`   TIMESTAMP    NULL ON UPDATE CURRENT_TIMESTAMP,
    `status`        TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT `comments.user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `intuit_comment`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE INDEX `comments_parent_id_idx` ON `intuit_comment`.`comments` (`parent_id` ASC);
CREATE INDEX `comments_user_id_idx` ON `intuit_comment`.`comments` (`user_id` ASC);
CREATE INDEX `comments_created_at_idx` ON `intuit_comment`.`comments` (`created_at` DESC);
CREATE INDEX `comments_status_idx` ON `intuit_comment`.`comments` (`status` DESC);