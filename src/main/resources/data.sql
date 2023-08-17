/*
  Test SQL File for Initial input
 */

insert into users(user_name,first_name,last_name)
    values('masoudUser','masoud','dehghan');

insert into users(user_name,first_name,last_name)
values('user2','test1','test1-d');

insert into users(user_name,first_name,last_name)
values('user3','test3','test3-d');

insert into director(name)
values('Spilbutrg');
insert into director(name)
values('Kiarostami');
insert into director(name)
values('Miami');
insert into director(name)
values('Mamad');

insert into film(name,director_id,genere)
values('Film1',4,1);
insert into film(name,director_id,genere)
values('Film2',1,2);
insert into film(name,director_id,genere)
values('Film3',1,3);
insert into film(name,director_id,genere)
values('Film4',2,3);
insert into film(name,director_id,genere)
values('Film5',3,3);
insert into film(name,director_id,genere)
values('Film6',3,2);
insert into film(name,director_id,genere)
values('Film7',2,2);
insert into film(name,director_id,genere)
values('Film8',2,2);
insert into film(name,director_id,genere)
values('Film9',2,2);