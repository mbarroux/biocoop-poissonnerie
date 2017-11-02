create table poissons(
  code smallint primary key,
  espece varchar(255) not null,
  nom_scientifique varchar(255),
  date_debut_vente date,
  date_fin_vente date,
  type smallint not null -- 1 = poisson / 2 = crustace
);

create table zones_de_peche(
  code varchar2(20) primary key,
  libelle varchar(255) not null,
  code_zone_parent varchar2(20) default null,
  foreign key (code_zone_parent) references zones_de_peche(code)
);

create table poissons_zones_de_peche(
  code_poisson smallint,
  code_zone_peche varchar2(20),
  commentaire varchar2(255),
  primary key(code_poisson, code_zone_peche),
  foreign key (code_poisson) references poissons(code),
  foreign key (code_zone_peche) references zones_de_peche(code)
);