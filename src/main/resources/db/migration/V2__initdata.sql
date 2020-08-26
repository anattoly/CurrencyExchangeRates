INSERT INTO user(login, password, first_name, last_name, middle_name, user_id)
VALUES('anattoly@dotmail.com','$2a$08$RoJ4PxDm2iySnDO7s.4WYeXHCll0acE5D1Djm4eKgeBeiCfPwP.0i','Анатолий', 'Коваленко','Павлович', nextval('user_seq')),
('i.kolesnikov@dotmail.com','password','Иван', 'Колесников','Викторович', nextval('user_seq')),
('liz@dotmail.com','password','Елизавета', 'Мироненко','Павловна', nextval('user_seq'));

INSERT INTO user_role (user_id, user_role) VALUES (1, 'USER'), (2, 'USER'), (3, 'USER');
