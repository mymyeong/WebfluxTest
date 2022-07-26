DROP TABLE IF EXISTS my_user;
CREATE TABLE my_user
(
    id bigint auto_increment primary key,
    name varchar(100),
    passwd varchar(200)
);

INSERT INTO my_user (name, passwd) VALUES ('test1', 'test');
INSERT INTO my_user (name, passwd) VALUES ('test2', 'test');