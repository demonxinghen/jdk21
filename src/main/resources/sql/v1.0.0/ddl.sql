create database if not exists security;
use security;
create table user
(
    id varchar(32) primary key comment 'ID',
    username varchar(32) not null comment '用户名',
    name varchar(32) not null comment '姓名',
    password varchar(64) not null comment '密码',
    creator varchar(32) comment '创建人',
    create_time timestamp comment '创建时间',
    modifier varchar(32) comment '修改人',
    modify_time timestamp comment '修改时间',
    deleted boolean not null default false comment 'false正常,true已删除'
) comment '用户表';

create table permission
(
    id varchar(32) primary key comment 'ID',
    name varchar(32) not null comment '权限名',
    parent varchar(32) not null comment '父级权限',
    creator varchar(32) comment '创建人',
    create_time timestamp comment '创建时间',
    modifier varchar(32) comment '修改人',
    modify_time timestamp comment '修改时间',
    deleted boolean not null default false comment 'false正常,true已删除'
) comment '权限表';

create table user_permission_relation
(
    id varchar(32) primary key comment 'ID',
    user_id varchar(32) not null comment '用户ID',
    permission_id varchar(32) not null comment '权限ID',
    creator varchar(32) comment '创建人',
    create_time timestamp comment '创建时间',
    modifier varchar(32) comment '修改人',
    modify_time timestamp comment '修改时间',
    deleted boolean not null default false comment 'false正常,true已删除'
) comment '用户权限关联表';