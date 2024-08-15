CREATE TABLE `myspringbatchdatarepository`.`car` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `make` VARCHAR(45) NULL,
                                                     `model` VARCHAR(45) NULL,
                                                     `color` VARCHAR(45) NULL,
                                                     `year` INT NULL,
                                                     `price` INT NULL,
                                                     PRIMARY KEY (`id`));
CREATE TABLE `myspringbatchdatarepository`.`person` (
                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                        `firstName` VARCHAR(45) NULL,
                                                        `lastName` VARCHAR(45) NULL,
                                                        `email` VARCHAR(45) NULL,
                                                        `gender` VARCHAR(45) NULL,
                                                        `age` INT NULL,
                                                        PRIMARY KEY (`id`));