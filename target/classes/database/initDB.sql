CREATE TABLE IF NOT EXISTS patients
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    first_name  VARCHAR(256) NOT NULL ,
    last_name  VARCHAR(256) NOT NULL,
    middle_name  VARCHAR(256) NOT NULL,
    doctor_id INTEGER
);

CREATE TABLE IF NOT EXISTS doctors
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    first_name  VARCHAR(256) NOT NULL ,
    last_name  VARCHAR(256) NOT NULL,
    middle_name  VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS prescriptions
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    title  VARCHAR(256) NOT NULL ,
    content  VARCHAR(256) NOT NULL ,
    patient_id INTEGER
);

CREATE TABLE IF NOT EXISTS appointments
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    title  VARCHAR(256) NOT NULL ,
    appointment_date DATE NOT NULL ,
    appointment_time TIME NOT NULL ,
    doctor_id INTEGER ,
    patient_id INTEGER
);

CREATE TABLE IF NOT EXISTS reviews
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    title  VARCHAR(256) NOT NULL ,
    content  VARCHAR(256) NOT NULL ,
    doctor_id INTEGER
);