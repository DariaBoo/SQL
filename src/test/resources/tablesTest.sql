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

insert into schoolManager.courses (course_name, description) values ('Alchemy','the study of transmutation of substances into other forms.');
insert into schoolManager.courses (course_name, description) values ('Herbology','the study of magical plants and how to take care of, utilise and combat them.');
insert into schoolManager.courses (course_name, description) values ('History of Magic','the study of magical history.');
insert into schoolManager.courses (course_name, description) values ('Potions','the art of creating mixtures with magical effects.');
insert into schoolManager.courses (course_name, description) values ('Muggle Studies','the study of the Muggle culture from a wizarding point of view.');
insert into schoolManager.courses (course_name, description) values ('Dark Arts','the study of defensive techniques to defend against the Dark Arts.');
insert into schoolManager.courses (course_name, description) values ('Charms','the study of spells concerned with giving an object new properties.');
insert into schoolManager.courses (course_name, description) values ('Flying','the study of flying of broomsticks.');

insert into schoolManager.groups (group_name, count_of_students) values ('WS-75', 20);
insert into schoolManager.groups (group_name, count_of_students) values ('CE-46', 12);
insert into schoolManager.groups (group_name, count_of_students) values ('IX-48', 15);
insert into schoolManager.groups (group_name, count_of_students) values ('UC-22', 25);
insert into schoolManager.groups (group_name, count_of_students) values ('XF-19', 29);
insert into schoolManager.groups (group_name, count_of_students) values ('LU-69', 17);
insert into schoolManager.groups (group_name, count_of_students) values ('GP-56', 26);
insert into schoolManager.groups (group_name, count_of_students) values ('NB-67', 24);

insert into schoolManager.students (group_id, first_name, last_name) values (1,'Lisa','Nott');
insert into schoolManager.students (group_id, first_name, last_name) values (6,'Seamus','Longbottom');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Lisa','Macmillan');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Hannah','Thomas');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Dean','Bones');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Justin','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (4,'Lavender','Longbottom');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Mandy','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Ronald','Turpin');
insert into schoolManager.students (group_id, first_name, last_name) values (4,'Lavender','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (2, 'Dean','Weasley');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Dean','Brocklehurst');
insert into schoolManager.students (group_id, first_name, last_name) values (6,'Hannah','Turpin');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Ernie','Granger');
insert into schoolManager.students (group_id, first_name, last_name) values (1,'Parvati','Weasley');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Harry','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Michael','Corner');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Lavender','Finch-Fletchley');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Terry','Parkinson');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Lisa','Macmillan');
insert into schoolManager.students (group_id, first_name, last_name) values (7,'Lavender','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Ronald','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Terry','Brocklehurst');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Hannah','Macmillan');
insert into schoolManager.students (group_id, first_name, last_name) values (7,'Seamus','Finnigan');
insert into schoolManager.students (group_id, first_name, last_name) values (1,'Neville','Goldstein');
insert into schoolManager.students (group_id, first_name, last_name) values (1,'Michael','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Pansy','Thomas');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Pansy','Corner');
insert into schoolManager.students (group_id, first_name, last_name) values (6,'Terry','Granger');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Padma','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Lavender','Brocklehurst');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Susan','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Ernie','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (1,'Seamus','Bones');
insert into schoolManager.students (group_id, first_name, last_name) values (4,'Lisa','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (6,'Hermione','Nott');
insert into schoolManager.students (group_id, first_name, last_name) values (3,'Harry','Weasley');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Theodore','Bones');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Dean','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (4,'Hermione','Brocklehurst');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Neville','Abbott');
insert into schoolManager.students (group_id, first_name, last_name) values (8,'Ronald','Abbott');
insert into schoolManager.students (group_id, first_name, last_name) values (7,'Terry','Nott');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Seamus','Abbott');
insert into schoolManager.students (group_id, first_name, last_name) values (1,'Hannah','Brocklehurst');
insert into schoolManager.students (group_id, first_name, last_name) values (2,'Anthony','Patil');
insert into schoolManager.students (group_id, first_name, last_name) values (5,'Parvati','Boot');
insert into schoolManager.students (group_id, first_name, last_name) values (4,'Ronald','Parkinson');
insert into schoolManager.students (group_id, first_name, last_name) values (7,'Michael','Weasley');

insert into schoolmanager.student_course (student_id,course_id) values (1,1);
insert into schoolmanager.student_course (student_id,course_id) values (1,2);
insert into schoolmanager.student_course (student_id,course_id) values (2,4);
insert into schoolmanager.student_course (student_id,course_id) values (2,1);
insert into schoolmanager.student_course (student_id,course_id) values (3,4);
insert into schoolmanager.student_course (student_id,course_id) values (3,6);
insert into schoolmanager.student_course (student_id,course_id) values (4,4);
insert into schoolmanager.student_course (student_id,course_id) values (5,8);
