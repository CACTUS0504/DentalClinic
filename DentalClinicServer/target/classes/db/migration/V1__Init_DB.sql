CREATE TABLE IF NOT EXISTS patients
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    first_name  VARCHAR(256) NOT NULL ,
    last_name  VARCHAR(256) NOT NULL,
    middle_name  VARCHAR(256) NOT NULL,
    doctor_id INTEGER,
    user_id INTEGER
);

CREATE TABLE IF NOT EXISTS doctors
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    first_name  VARCHAR(256) NOT NULL ,
    last_name  VARCHAR(256) NOT NULL,
    middle_name  VARCHAR(256) NOT NULL,
    user_id INTEGER
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
    appointment_date TIMESTAMP NOT NULL ,
    doctor_id INTEGER NOT NULL,
    patient_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    title  VARCHAR(256) NOT NULL ,
    content  VARCHAR(256) NOT NULL ,
    rating INTEGER NOT NULL,
    doctor_id INTEGER
);

CREATE TABLE IF NOT EXISTS users
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    is_banned BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id    SERIAL NOT NULL PRIMARY KEY ,
    name VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id    INT NOT NULL,
    role_id    INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);