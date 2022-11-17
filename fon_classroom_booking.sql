--drop schema classroom_booking cascade;

create schema if not exists classroom_booking;

set search_path = classroom_booking, public;

--show search_path;

create table user_app(
	first_name text,
	last_name text,
	email text not null unique,
	telephone text,
	user_name text primary key,
	"password" text not null,
	active text,
	role_id numeric,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);

insert into user_app values 
('Aleksandar', 'Janković', 'aleksandar.jankovic95@yahoo.com', '060/59-10-951', 'aca_janko', '1', 'A', null, 'aca_janko', now(), null, null);

create table classroom_type (
	id numeric primary key,
	dsc text,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);

CREATE SEQUENCE classroom_booking.classroom_type_sequ
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table classroom_type alter column id set default nextval('classroom_type_sequ');

create table classroom (
	id numeric primary key,
	dsc text,
	people_capacity numeric default 0, 
--	has_computers boolean,
	computer_capacity numeric default 0,
	class_room_type numeric,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_classroom_type foreign key(class_room_type) references classroom_type(id),
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);

CREATE SEQUENCE classroom_booking.classroom_sequ
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table classroom alter column id set default nextval('classroom_sequ');

create table reservation_type (
	id numeric primary key,
	dsc text,
	usc text not null,
	dtc timestamp not null default CURRENT_TIMESTAMP,
	usm text,
	dtm timestamp,
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);

CREATE SEQUENCE classroom_booking.reservation_type_sequ
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table reservation_type alter column id set default nextval('reservation_type_sequ');

create table reservation_status (
	id numeric primary key,
	dsc text,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);
select * from classroom_booking.classroom;
CREATE SEQUENCE classroom_booking.reservation_status_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table reservation_status alter column id set default nextval('reservation_status_seq');

create table reservation (
	id numeric primary key,
	classroom numeric not null,
	dsc text,
	reservation_type numeric not null,
	beginning_asked timestamp,
	end_asked timestamp,
	reservation_for_user text,
	reservation_status numeric,
	beginning_approved timestamp,
	end_approved timestamp,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_reserv_type foreign key(reservation_type) references reservation_type(id), 
	constraint fk_reserv_stat foreign key(reservation_status) references reservation_status(id), 
	constraint fk_classroom foreign key(classroom) references classroom(id), 
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);


alter table reservation 
add constraint fk_classroom foreign key(classroom) references classroom(id);

CREATE SEQUENCE classroom_booking.reservation_sequ
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table reservation alter column id set default nextval('reservation_sequ');

create table reservation_status_history (
	reservation_id numeric,
	reservation_status numeric,
	status_changed_by text,
	dt_change timestamp,
	constraint fk_reservation foreign key(reservation_id) references reservation(id),
	constraint fk_reserv_stat foreign key(reservation_status) references reservation_status(id),
	constraint fk_reserv_stat_changed_by foreign key(status_changed_by) references user_app(user_name)
);

--create table privileges_app (
--	id numeric primary key,
--	dsc text,
--	usc text not null,
--	dtc timestamp not null default current_timestamp,
--	usm text,
--	dtm timestamp,
--	constraint fk_usc foreign key(usc) references user_app(user_name),
--	constraint fk_usm foreign key(usm) references user_app(user_name)
--);
--
--CREATE SEQUENCE classroom_booking.privileges_app_sequ
--	INCREMENT BY 1
--	MINVALUE 1
--	MAXVALUE 9223372036854775807
--	START 0
--	CACHE 1
--	NO CYCLE;

--alter table privileges_app alter column id set default nextval('privileges_app_sequ');

create table roles (
	id numeric primary key,
	dsc text,
	usc text not null,
	dtc timestamp not null default current_timestamp,
	usm text,
	dtm timestamp,
	constraint fk_usc foreign key(usc) references user_app(user_name),
	constraint fk_usm foreign key(usm) references user_app(user_name)
);

CREATE SEQUENCE classroom_booking.roles_sequ
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 9223372036854775807
	START 0
	CACHE 1
	NO CYCLE;

alter table roles alter column id set default nextval('roles_sequ');

alter table user_app add constraint fk_roles foreign key(role_id) references roles(id);

--create table roles_privilages (
--	id_role numeric,
--	id_privilege numeric,
--	usc text not null,
--	dtc timestamp not null default current_timestamp,
--	constraint pk_roles_privileges primary key (id_role, id_privilege),
--	constraint fk_usc foreign key(usc) references user_app(user_name)
--);

--create table types ( entity attribute value table
--	
--	id numeric,
--	dsc text,
--	usc text not null,
--	dtc timestamp not null default current_timestamp,
--	usm text,
--	dtm timestamp,
--	constraint fk_usc foreign key(usc) references user_app(user_name),
--	constraint fk_usm foreign key(usm) references user_app(user_name)
--);


        select * from classroom order by id;
       
       
       select * from reservation r ;
       
        
        select now();
        
        select * from reservation_type rt ;
       	select * from reservation_status rs ;
        
        select * from classroom_booking.reservation r ;
        select * from user_app ua ;
       
        select * from classroom_type;
       
       
           select
        classroomt0_.id as id1_1_,
        classroomt0_.dtc as dtc2_1_,
        classroomt0_.dtm as dtm3_1_,
        classroomt0_.usc as usc4_1_,
        classroomt0_.usm as usm5_1_,
        classroomt0_.dsc as dsc6_1_ 
    from
        classroom_booking.classroom_type classroomt0_
        
        
    select
        * 
    from
        classroom_booking.reservation 
    where
        classroom = 1 
        and beginning_approved is not null
    
    select
        * 
    from
        classroom_booking.reservation 
    where
    	reservation_for_user = 'aca_janko'
        and beginning_approved is not null
        
        select
        user0_.user_name as user_nam1_1_,
        user0_.active as active2_1_,
        user0_.email as email3_1_,
        user0_.first_name as first_na4_1_,
        user0_.last_name as last_nam5_1_,
        user0_.password as password6_1_,
        user0_.role_id as role_id8_1_,
        user0_.telephone as telephon7_1_ 
    from
        classroom_booking.user_app user0_ 
    where
        user0_.user_name= 'nikola'
        
        
        
         select
        reservatio0_.id as id1_2_,
        reservatio0_.dtc as dtc2_2_,
        reservatio0_.dtm as dtm3_2_,
        reservatio0_.usc as usc4_2_,
        reservatio0_.usm as usm5_2_,
        reservatio0_.beginning_approved as beginnin6_2_,
        reservatio0_.beginning_asked as beginnin7_2_,
        reservatio0_.classroom as classro11_2_,
        reservatio0_.dsc as dsc8_2_,
        reservatio0_.end_approved as end_appr9_2_,
        reservatio0_.end_asked as end_ask10_2_,
        reservatio0_.reservation_for_user as reserva12_2_,
        reservatio0_.reservation_status as reserva13_2_,
        reservatio0_.reservation_type as reserva14_2_ 
    from
        classroom_booking.reservation reservatio0_
        
        
        
        
        
select now()::date - '2016-10-02'::date  
        

SELECT extract(month from interval '4 years');
        

        
        
        
        
        