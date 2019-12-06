CREATE TABLE user_information (
    user_id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    address VARCHAR(256) NOT NULL,
    phone_number VARCHAR(128) NOT NULL,
    
    PRIMARY KEY (user_id)
);

CREATE TABLE bank_details (
    id INTEGER NOT NULL AUTO_INCREMENT,
    bank_name VARCHAR(128),
    account_number VARCHAR(128) NOT NULL,
    balance double NOT NULL,
    user_id INTEGER ,
    foreign key (user_id) references user_information(user_id),
    
    PRIMARY KEY (id)
);