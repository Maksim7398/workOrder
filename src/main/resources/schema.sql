

CREATE TABLE client(
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       fio VARCHAR(250) ,
                       number_phone VARCHAR(250),
                       model_car VARCHAR(250) NOT NULL ,
                       year_car INT DEFAULT NULL ,
                       number_car VARCHAR(250) NOT NULL,
                       vin_code VARCHAR(250) NOT NULL,
                       mileage INT DEFAULT NULL
);

