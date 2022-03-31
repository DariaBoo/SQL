CREATE SCHEMA IF NOT EXISTS schoolManager AUTHORIZATION h2user;

DROP TABLE IF EXISTS schoolManager.groups CASCADE;

CREATE TABLE schoolManager.groups
(
    group_id SERIAL,
    group_name VARCHAR(7) UNIQUE NOT NULL,
    count_of_students int,
    PRIMARY KEY(group_id)
);

DROP TABLE IF EXISTS schoolManager.courses CASCADE;

CREATE TABLE schoolManager.courses
(
    course_id SERIAL,
    course_name VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(150),
    PRIMARY KEY (course_id)
);
DROP TABLE IF EXISTS schoolManager.students CASCADE;

CREATE TABLE IF NOT EXISTS schoolManager.students
(
    student_id SERIAL,
    group_id int references schoolManager.groups(group_id),
    first_name VARCHAR(30)  NOT NULL,
    last_name VARCHAR(30)  NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS schoolManager.student_course CASCADE;

CREATE TABLE schoolManager.student_course
(    
    student_id INT REFERENCES schoolManager.students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id INT REFERENCES schoolManager.courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (course_id, student_id)  
);
