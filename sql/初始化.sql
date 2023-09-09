CREATE TABLE `interface_info` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `name` varchar(256) NOT NULL COMMENT '名称',
                                  `description` varchar(256) DEFAULT NULL COMMENT '描述',
                                  `url` varchar(512) NOT NULL COMMENT '接口地址',
                                  `request_params` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '璇锋眰鍙傛暟',
                                  `request_header` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '请求头',
                                  `response_header` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '响应头',
                                  `status` int(11) NOT NULL DEFAULT '1' COMMENT '接口状态（0-关闭，1-开启）',
                                  `method` tinyint(4) NOT NULL COMMENT '请求类型',
                                  `user_id` bigint(20) NOT NULL COMMENT '创建人',
                                  `delete_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删, 1-已删)',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='接口信息';


CREATE TABLE `user` (
                        `uid` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(255) NOT NULL,
                        `nickname` varchar(255) DEFAULT NULL,
                        `password` varchar(255) NOT NULL,
                        `user_role` varchar(20) NOT NULL,
                        `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                        `delete_flag` tinyint(1) DEFAULT '0',
                        `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci,
                        `enable` tinyint(1) DEFAULT '0',
                        `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '#',
                        `access_key` varchar(512) DEFAULT NULL,
                        `secret_key` varchar(512) DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                        PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

CREATE TABLE `user_interface_info` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `user_id` bigint(20) NOT NULL COMMENT '调用用户 id',
                                       `interface_info_id` bigint(20) NOT NULL COMMENT '接口 id',
                                       `total_num` int(11) NOT NULL DEFAULT '0' COMMENT '总调用次数',
                                       `left_num` int(11) NOT NULL DEFAULT '0' COMMENT '剩余调用次数',
                                       `delete_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删, 1-已删)',
                                       `status` int(11) NOT NULL DEFAULT '0' COMMENT '0-正常，1-禁用',
                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户调用接口关系';

-- 初始密码都为12345678
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (1, 'root', NULL, 'fc9e0c9df7766f260c92bd0371f1b14d', 'admin', '123@qq.com', 0, '超级管理员', 0, 'http://localhost:9000/api/static/bucket_user_1/avatar/d8d4b745-1ddb-4ca4-a168-e45a01372aa4.jpg', NULL, NULL, '2023-08-19 19:17:58', '2023-08-21 09:52:38');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (32, 'jzdn', '许荣轩', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', '956287248@qq.com', 0, NULL, 0, '#', '713890562c9ca56337ace9f63d0ba722', 'b80e82df9cb09ae3bc52104a0ff2c3f8', '2023-08-31 14:38:27', '2023-09-08 18:08:37');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (33, 'Rnrw', '刘浩然', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'jerold.gottlieb@gmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-16 14:38:33', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (34, 'ris', '蔡鹤轩', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'glen.feil@gmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:39', '2023-08-31 14:38:42');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (35, '6I4', '王熠彤', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'alva.tremblay@hotmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:47', '2023-08-31 14:38:49');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (36, '8M3', '赖浩然', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'max.ryan@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:55');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (37, 'Hx9zb', '彭梓晨', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'nicky.wunsch@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (38, 'ir', '谢思聪', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'connie.langworth@hotmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (39, 'dnpEV', '马凯瑞', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'elizbeth.bergnaum@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (40, 'JXd', '莫弘文', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'adrian.stark@gmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (41, 'VS2TS', '丁立辉', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'bryan.jacobson@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (42, 'Ro', '宋煜祺', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'janeen.boehm@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (43, '9aFl', '阎文昊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'virgen.schneider@hotmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (44, 'JxqM4', '杜鹏涛', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'wilbert.grady@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (45, 'iyUu', '邱智渊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'ed.jacobi@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (46, 'AchI', '于建辉', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'lenny.fritsch@hotmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (47, '6yP', '蔡浩然', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'alvaro.beer@gmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (48, '0YpTt', '龚嘉熙', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'emmitt.wyman@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (49, 'Zv2SG', '吴修洁', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'luis.schneider@yahoo.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (50, 'Ro', '张文博', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'kaye.grimes@gmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
INSERT INTO `user`(`uid`, `username`, `nickname`, `password`, `user_role`, `email`, `delete_flag`, `remark`, `enable`, `avatar`, `access_key`, `secret_key`, `create_time`, `update_time`) VALUES (51, 'Nwpt', '叶智渊', 'fc9e0c9df7766f260c92bd0371f1b14d', 'user', 'cyndy.witting@hotmail.com', 0, NULL, 0, '#', NULL, NULL, '2023-08-31 14:38:52', '2023-08-31 14:38:36');
