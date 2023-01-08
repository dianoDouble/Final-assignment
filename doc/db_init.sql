CREATE DATABASE help

CREATE TABLE user (
    `id` VARCHAR(40) NOT NULL,
	`name` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`userName` VARCHAR(20),
	`password` VARCHAR(100),
	PRIMARY KEY (`id`)
)

CREATE TABLE role (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
	`cname` VARCHAR(50) COLLATE 'utf8mb4_general_ci',
	`ename` VARCHAR(50) COLLATE 'utf8mb4_general_ci',
	`picture` VARCHAR(100) COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`)
)

CREATE TABLE admin (
    `id` VARCHAR(40) NOT NULL,
	`adminName` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`userName` VARCHAR(20),
	`password` VARCHAR(100),
	PRIMARY KEY (`id`)
)

--录入管理员信息
INSERT INTO admin(id, adminName, userName, password) VALUES (1, "admin", "adminlsj", MD5('123456'))


