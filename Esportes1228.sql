create database TimesCadastro;
use TimesCadastro;
create table times(
  Id integer not null auto_increment,
  NomeTime varchar(50) not null,
  Senha char(64) not null, 
  primary key(id)
);

create table InfoTimes(
  id integer not null auto_increment,
  NomeEsporte varchar(50) not null, 
  QuantiaJogador integer not null,
  IDTime integer not null,
  primary key(id),
  foreign key(IDTime) references times(id)
);