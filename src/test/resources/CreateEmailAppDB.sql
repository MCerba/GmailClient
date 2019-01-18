
-- This script needs to run only once

DROP DATABASE IF EXISTS EMAIL_APP_DB;
CREATE DATABASE EMAIL_APP_DB;

USE EMAIL_APP_DB;

DROP USER IF EXISTS CerbaM@localhost;
CREATE USER CerbaM@'localhost' IDENTIFIED BY 'javaapp';

GRANT ALL ON EMAIL_APP_DB.* TO CerbaM@'localhost';

-- This creates a user with access from any IP number except localhost
-- Use only if your MyQL database is on a different host from localhost
-- DROP USER IF EXISTS TheUser;
-- CREATE USER TheUser IDENTIFIED WITH mysql_native_password BY 'pancake' REQUIRE NONE;
-- GRANT ALL ON GAMER_DB TO TheUser;

-- This creates a user with access from a specific IP number
-- Preferable to '%'
-- DROP USER IF EXISTS TheUser@'192.168.0.194';
-- CREATE USER TheUser@'192.168.0.194' IDENTIFIED WITH mysql_native_password BY 'pancake' REQUIRE NONE;
-- GRANT ALL ON GAMER_DB TO TheUser@'192.168.0.194';

FLUSH PRIVILEGES;

