/* 
PROCEDURE:
	createComment
DESCRIPTION:
	Creates Comment posted by a User
SAMPLE USAGE:
	CALL createComment();
SAMPLE OUTPUT:

AUTHOR:
	2024-3-18	Himanshu Mahajan	(Created)
*/

USE `intuit_comment`;
DROP PROCEDURE IF EXISTS `createComment`;

DELIMITER $$
USE `intuit_comment`$$
CREATE PROCEDURE `createComment`(
    IN _comment_id VARCHAR(24),
    IN _post_id VARCHAR(24),
    IN _parent_id VARCHAR(24),
    IN _user_id VARCHAR(24),
    IN _text VARCHAR(200),
    IN _comment_level INT
)
BEGIN
    INSERT INTO `intuit_comment`.`comments`(id, post_id, parent_id, user_id, text, comment_level)
    VALUES (_comment_id, _post_id, _parent_id, _user_id, _text, _comment_level);

    UPDATE `intuit_comment`.`posts` SET comment_count = comment_count + 1 WHERE id = _post_id;

    IF _comment_level != 0
    THEN
        UPDATE `intuit_comment`.`comments` SET reply_count = reply_count + 1 WHERE id = _parent_id;
    END IF;

    SELECT * FROM `intuit_comment`.`comments` WHERE id = _comment_id;
END$$

DELIMITER ;

