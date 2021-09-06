/* +++++ FINAL DATABASE +++++ */
CREATE DATABASE IF NOT EXISTS torre_test;

USE torre_test;

-- Dimension country table
CREATE TABLE IF NOT EXISTS country
(
    id   SMALLINT UNSIGNED PRIMARY KEY,
    name VARCHAR(60)
) ENGINE = INNODB;

-- Dimension city table
CREATE TABLE IF NOT EXISTS city
(
    id   INT UNSIGNED PRIMARY KEY,
    name TEXT
) ENGINE = INNODB;

-- Dimension location table
CREATE TABLE IF NOT EXISTS location
(
    id         INT UNSIGNED PRIMARY KEY,
    country_id SMALLINT UNSIGNED,
    city_id    INT UNSIGNED,
    FOREIGN KEY (country_id) REFERENCES country (id) ON DELETE CASCADE,
    FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension openTo table
CREATE TABLE IF NOT EXISTS openTo
(
    id          SMALLINT UNSIGNED PRIMARY KEY,
    fulltime    BOOLEAN,
    freelance   BOOLEAN,
    internships BOOLEAN,
    hiring      BOOLEAN,
    mentoring   BOOLEAN,
    advising    BOOLEAN
) ENGINE = INNODB;

-- Facts people table
CREATE TABLE IF NOT EXISTS people
(
    subjectId   INT UNSIGNED PRIMARY KEY,
    verified    BOOLEAN,
    weight      DECIMAL(10, 4),
    location_id INT UNSIGNED,
    openTo      SMALLINT UNSIGNED,
    FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE CASCADE,
    FOREIGN KEY (openTo) REFERENCES openTo (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension type table
CREATE TABLE IF NOT EXISTS type
(
    id   SMALLINT UNSIGNED PRIMARY KEY,
    name VARCHAR(50)
) ENGINE = INNODB;

-- Dimension status table
CREATE TABLE IF NOT EXISTS status
(
    id     SMALLINT UNSIGNED PRIMARY KEY,
    status VARCHAR(20)
) ENGINE = INNODB;

-- Facts opps table
CREATE TABLE IF NOT EXISTS opps
(
    id        INT UNSIGNED PRIMARY KEY,
    objective VARCHAR(255),
    remote    BOOLEAN,
    deadline  DATE,
    created   DATE,
    type_id   SMALLINT UNSIGNED,
    status_id SMALLINT UNSIGNED,
    FOREIGN KEY (type_id) REFERENCES type (id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension skill table
CREATE TABLE IF NOT EXISTS skill
(
    id   INT UNSIGNED PRIMARY KEY,
    name VARCHAR(50)
) ENGINE = INNODB;

-- Dimension bridge opp_skill table
CREATE TABLE IF NOT EXISTS opp_skill
(
    opp_id   INT UNSIGNED,
    skill_id INT UNSIGNED,
    PRIMARY KEY (opp_id, skill_id),
    FOREIGN KEY (opp_id) REFERENCES opps (id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES opps (id) ON DELETE CASCADE
) ENGINE = INNODB;

/* ++++++ STAGING DATABASE +++++ */
CREATE DATABASE IF NOT EXISTS torre_stage;

USE torre_stage;

-- Dimension country table
CREATE TABLE IF NOT EXISTS country
(
    id   SMALLINT UNSIGNED PRIMARY KEY,
    name VARCHAR(60)
) ENGINE = INNODB;

-- Dimension city table
CREATE TABLE IF NOT EXISTS city
(
    id   INT UNSIGNED PRIMARY KEY,
    name TEXT
) ENGINE = INNODB;

-- Dimension location table
CREATE TABLE IF NOT EXISTS location
(
    id         INT UNSIGNED PRIMARY KEY,
    country_id SMALLINT UNSIGNED,
    city_id    INT UNSIGNED,
    FOREIGN KEY (country_id) REFERENCES country (id) ON DELETE CASCADE,
    FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension openTo table
CREATE TABLE IF NOT EXISTS openTo
(
    id          SMALLINT UNSIGNED PRIMARY KEY,
    fulltime    BOOLEAN,
    freelance   BOOLEAN,
    internships BOOLEAN,
    hiring      BOOLEAN,
    mentoring   BOOLEAN,
    advising    BOOLEAN
) ENGINE = INNODB;

-- Facts people table
CREATE TABLE IF NOT EXISTS people
(
    subjectId   INT UNSIGNED PRIMARY KEY,
    verified    BOOLEAN,
    weight      DECIMAL(10, 4),
    location_id INT UNSIGNED,
    openTo      SMALLINT UNSIGNED,
    FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE CASCADE,
    FOREIGN KEY (openTo) REFERENCES openTo (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension type table
CREATE TABLE IF NOT EXISTS type
(
    id   SMALLINT UNSIGNED PRIMARY KEY,
    name VARCHAR(50)
) ENGINE = INNODB;

-- Dimension status table
CREATE TABLE IF NOT EXISTS status
(
    id     SMALLINT UNSIGNED PRIMARY KEY,
    status VARCHAR(20)
) ENGINE = INNODB;

-- Facts opps table
CREATE TABLE IF NOT EXISTS opps
(
    id        INT UNSIGNED PRIMARY KEY,
    objective VARCHAR(255),
    remote    BOOLEAN,
    deadline  DATE,
    created   DATE,
    type_id   SMALLINT UNSIGNED,
    status_id SMALLINT UNSIGNED,
    FOREIGN KEY (type_id) REFERENCES type (id) ON DELETE CASCADE,
    FOREIGN KEY (status_id) REFERENCES status (id) ON DELETE CASCADE
) ENGINE = INNODB;

-- Dimension skill table
CREATE TABLE IF NOT EXISTS skill
(
    id   INT UNSIGNED PRIMARY KEY,
    name VARCHAR(50)
) ENGINE = INNODB;

-- Dimension bridge opp_skill table
CREATE TABLE IF NOT EXISTS opp_skill
(
    opp_id   INT UNSIGNED,
    skill_id INT UNSIGNED,
    PRIMARY KEY (opp_id, skill_id),
    FOREIGN KEY (opp_id) REFERENCES opps (id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES opps (id) ON DELETE CASCADE
) ENGINE = INNODB;
