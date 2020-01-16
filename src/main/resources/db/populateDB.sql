DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, role) VALUES
  ('User_1', 'user-1@yandex.ru', '{noop}password1', 'ROLE_USER'),
  ('User_2', 'user-2@yandex.ru', '{noop}password2', 'ROLE_USER'),
  ('User_3', 'user-3@yandex.ru', '{noop}password3', 'ROLE_USER'),
  ('User_4', 'user-4@yandex.ru', '{noop}password4', 'ROLE_USER'),
  ('User_5', 'user-5@yandex.ru', '{noop}password5', 'ROLE_USER'),
  ('Admin', 'admin@gmail.com', '{noop}admin', 'ROLE_ADMIN');

INSERT INTO restaurants (name, date) VALUES
  ('Dinner In The Sky', '2015-05-30'),
  ('Funky Gourmet', '2015-05-30'),
  ('Scala Vinoteca', '2015-05-30'),
  ('Funky Gourmet', '2015-05-31'),
  ('Scala Vinoteca', '2015-05-31');

INSERT INTO dishes (name, price, restaurant_id) VALUES
  ('Soup-1', 100, 100006),
  ('Salad-1', 100, 100006),
  ('Main course-1', 100, 100006),
  ('Desert-1', 100, 100006),
  ('Soup-2', 100, 100007),
  ('Salad-2', 100, 100007),
  ('Main course-2', 100, 100007),
  ('Desert-2', 100, 100007),
  ('Soup-3', 100, 100008),
  ('Salad-3', 100, 100008),
  ('Main course-3', 100, 100008),
  ('Desert-3', 100, 100008),
  ('Soup-4', 100, 100009),
  ('Salad-4', 100, 100009),
  ('Main course-4', 100, 100009),
  ('Desert-4', 100, 100009),
  ('Soup-5', 100, 100010),
  ('Salad-5', 100, 100010),
  ('Main course-5', 100, 100010),
  ('Desert-5', 100, 100010);

INSERT INTO votes (restaurant_id, user_id, date) VALUES
  (100006, 100000, '2015-05-30'),
  (100006, 100001, '2015-05-30'),
  (100006, 100002, '2015-05-30'),
  (100007, 100003, '2015-05-30'),
  (100008, 100004, '2015-05-30'),
  (100009, 100001, '2015-05-31'),
  (100009, 100002, '2015-05-31'),
  (100009, 100003, '2015-05-31'),
  (100010, 100004, '2015-05-31');


INSERT INTO rating (restaurant_id, date, summary_votes) VALUES
  (100006, '2015-05-30', 3),
  (100007, '2015-05-30', 1),
  (100008, '2015-05-30', 1),
  (100009, '2015-05-31', 3),
  (100010, '2015-05-31', 1);