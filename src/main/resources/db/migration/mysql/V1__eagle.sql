/**
 * Author:  JGilson
 * Created: 30/01/2018
 */

CREATE TABLE IF NOT EXISTS _user (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS equipment_type (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS equipment_model (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    equipment_type_id BIGINT NOT NULL,
    PRIMARY KEY(id),

    FOREIGN KEY (equipment_type_id)
        REFERENCES equipment_type (id)
            ON UPDATE CASCADE ON DELETE RESTRICT       		
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS client (
    id BIGINT AUTO_INCREMENT NOT NULL,
    date_create TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL,    
    chat_id VARCHAR(255),
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS _order (
    id BIGINT AUTO_INCREMENT NOT NULL,
    initial_date TIMESTAMP NOT NULL,
    final_date TIMESTAMP NOT NULL,
    price DOUBLE NOT NULL,
    equipment_model_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    PRIMARY KEY(id),

    FOREIGN KEY (equipment_model_id)
        REFERENCES equipment_model (id)
            ON UPDATE CASCADE ON DELETE RESTRICT,

    FOREIGN KEY (client_id)
        REFERENCES client (id)
            ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;