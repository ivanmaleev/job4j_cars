CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  email TEXT NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE if not exists cars (
  id SERIAL PRIMARY KEY,
  brand_id int references carbrands(id),
  bodytype TEXT NOT NULL,
  photoPath TEXT
);

CREATE TABLE if not exists advertisements (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL,
  car_id int references cars(id),
  user_id int references users(id),
  sold boolean NOT NULL
);

CREATE TABLE if not exists carbrands (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE
);