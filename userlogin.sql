drop table if exists t_user;

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint auto_increment,
   loginName            varchar(255),
   loginPwd             varchar(255),
   realName             varchar(255),
   primary key (id)
);

insert into t_user(loginName,loginPwd,realName) values('zhangsan','123','张三');
insert into t_user(loginName,loginPwd,realName) values('jack','123','李四');
insert into t_user(loginName,loginPwd,realName) values('wangwu','123','王五');
insert into t_user(loginName,loginPwd,realName) values('rose','123','赵六');
insert into t_user(loginName,loginPwd,realName) values('aaaaa','123','张猛');
commit;
select * from t_user;
