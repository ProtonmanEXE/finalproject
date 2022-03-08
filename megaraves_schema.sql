DROP DATABASE if exists megaraves;

CREATE DATABASE megaraves;

USE megaraves;

CREATE TABLE user_accounts (
    user_name char(64) NOT NULL,
    password varchar(518) NOT NULL,
    email varchar(64) NOT NULL,
	authority enum ("ROLE_USER", "ROLE_ADMIN") NOT NULL,
	enabled boolean,
    PRIMARY KEY (user_name)
);

CREATE TABLE wishlist (
	game_id int NOT NULL,
    user_name char(64) NOT NULL,
    title varchar(256) NOT NULL,
    image_url varchar(512) NOT NULL,
    release_date date NOT NULL,
    PRIMARY KEY (game_id),
    CONSTRAINT fk_user_name
		FOREIGN KEY(user_name)
        REFERENCES user_accounts(user_name)
);