CREATE TABLE `B` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `a_id` bigint(20),
  `name` varchar(64) NOT NULL,
  `time_created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
