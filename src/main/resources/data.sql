INSERT INTO users (email, first_name, last_name, password)
VALUES ('CristianPerez@gmail.com', 'Cristian', 'Perez', '$2a$10$GceBTMx5xPcFs8skaMTH2.taB0mzHt7U3fY2J0Mjevj0ARq81XK0i'),
       ('RominaPaez@gmail.com', 'Romina', 'Paez', '$2a$10$GceBTMx5xPcFs8skaMTH2.taB0mzHt7U3fY2J0Mjevj0ARq81XK0i');

INSERT INTO cinema (id,name)
VALUES (1,'Cine Vicente Lopez'),
       (2,'Cine San Martin'),
       (3,'Cine San Isidro'),
       (4,'Cine Tigre'),
       (5,'Cine San Fernando');

INSERT INTO theater (cinema_id, theater_number, theater_type)
VALUES (1,1,'REGULAR'),
       (1,2,'REGULAR'),
       (1,3,'REGULAR'),
       (1,4,'HIGH_DEFINITION'),
       (1,5,'HIGH_DEFINITION'),
       (2,1,'REGULAR'),
       (2,2,'REGULAR'),
       (2,3,'REGULAR'),
       (2,4,'REGULAR'),
       (2,5,'HIGH_DEFINITION'),
       (3,1,'REGULAR'),
       (3,2,'REGULAR'),
       (3,3,'HIGH_DEFINITION'),
       (3,4,'HIGH_DEFINITION'),
       (3,5,'HIGH_DEFINITION'),
       (4,1,'REGULAR'),
       (4,2,'REGULAR'),
       (4,3,'REGULAR'),
       (4,4,'HIGH_DEFINITION'),
       (4,5,'HIGH_DEFINITION'),
       (5,1,'REGULAR'),
       (5,2,'REGULAR'),
       (5,3,'REGULAR'),
       (5,4,'REGULAR'),
       (5,5,'HIGH_DEFINITION');

