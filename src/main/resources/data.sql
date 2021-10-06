/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username, version) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01',0);

insert into driver (id, date_created, deleted, online_status, password, username, version) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02',0);

insert into driver (id, date_created, deleted, online_status, password, username, version) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03',0);


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username, version) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04',0);

insert into driver (id, date_created, deleted, online_status, password, username, version) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05',0);

insert into driver (id, date_created, deleted, online_status, password, username, version) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06',0);

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username, version)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07',0);

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username, version)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08',0);


-- Data for Cars
insert into car (id, date_created, license_plate, seat_count, convertible, rating, engine_type, deleted, car_status, manufacturer,version)
values
(1, now(), '12311-ax', 4, TRUE, 2, 'GAS', FALSE, 'FREE','Skoda',0), 
(2, now(), '125-cf', 5, FALSE, 3, 'ELECTRIC', FALSE, 'FREE','Fiat',0),
(4, now(), '125-54-c', 2, TRUE, 3, 'GAS', FALSE, 'FREE','Ford',0),
(5, now(), '985-54-c', 3, TRUE, 5, 'HYBRID', FALSE, 'FREE','BMW',0),
(6, now(), '111-44-s', 4, TRUE, 5, 'GAS', FALSE, 'IN_USE','AUDI',0),
(7, now(), '331-66-sx', 4, TRUE, 5, 'ELECTRIC', FALSE, 'IN_USE','VolksWagen',0);