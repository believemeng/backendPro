CREATE TABLE `dspread`.`<table_name>` (
	`uid` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
	`salt` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
	`password` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
	`openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
	`unionid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
	`create_time` datetime DEFAULT NULL,
	`update_time` timestamp NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`uid`)
) ENGINE=`InnoDB` AUTO_INCREMENT=3 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci ROW_FORMAT=DYNAMIC CHECKSUM=0 DELAY_KEY_WRITE=0;