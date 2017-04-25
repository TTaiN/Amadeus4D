/*
  Author: TTaiN
  - Amadeus Discord System --
  MySQL Database Schema
*/

CREATE TABLE credentials(
  token VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL,
  PRIMARY KEY (token)
);

CREATE TABLE developers(
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE listening_channels(
  guild_id VARCHAR(255) NOT NULL,
  channel_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (guild_id, channel_id)
);

CREATE TABLE djs(
  guild_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (guild_id, user_id)
);

CREATE TABLE moderators(
  guild_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (guild_id, user_id)
);

CREATE TABLE api(
  purpose VARCHAR(255) NOT NULL,
  api_key VARCHAR(255) NOT NULL,
  active BOOLEAN NOT NULL,
  PRIMARY KEY (name, key)
);