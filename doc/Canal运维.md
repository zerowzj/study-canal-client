# 一. 安装

## 1.1 Canal Server

## 1.2

## 1.3



# 二. 配置

## 2.1 canal配置

```properties
#可选项: tcp(默认), kafka, RocketMQ
canal.serverMode=kafka
```



## 2.1 instance配置

```properties
#
canal.instance.mysql.slaveId=

#数据库配置
canal.instance.master.address=
canal.instance.master.journal.name = 
canal.instance.master.position = 
canal.instance.master.timestamp =

#
canal.instance.dbUsername=root
canal.instance.dbPassword=root
canal.instance.defaultDatabaseName=
canal.instance.connectionCharset=UTF-8

#过滤配置
canal.instance.filter.regex=.*\\..*\

#MQ配置
canal.mq.topic=example
#针对库名或者表名发送动态topic
canal.mq.dynamicTopic=mytest,.*,mytest.user,mytest\\..*,.*\\..*
canal.mq.partition=0
#hash partition config
canal.mq.partitionsNum=3
#库名.表名: 唯一主键，多个表之间用逗号分隔
canal.mq.partitionHash=mytest.person:id,mytest.role:id
```

