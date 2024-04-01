-- -----------------------------------------------------
-- Table `intuit_comment`.`posts`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `intuit_comment`.`posts`;

CREATE TABLE IF NOT EXISTS `intuit_comment`.`posts` (
    `id`            VARCHAR(40),
    `user_id`       VARCHAR(40)  NOT NULL,
    `text`          VARCHAR(255) NOT NULL,
    `comment_count` INT          NULL,
    `like_count`    INT          NULL,
    `dislike_count` INT          NULL,
    `created_at`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`   TIMESTAMP    NULL,
    `status`        TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT `posts.user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `intuit_comment`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE INDEX `posts_user_id_idx` ON `intuit_comment`.`posts` (`user_id` ASC);
CREATE INDEX `posts_status_idx` ON `intuit_comment`.`posts` (`status` DESC);