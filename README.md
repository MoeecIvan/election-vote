# 投票项目说明
这是测试项目


# 一、项目简介
## 1.1 项目背景
此测试项目基于千万级用户的基础上，构建一套简易的投票系统。

## 1.2 项目目标
建立一套可扩展的支持高并发的系统。

## 1.3 项目分析
此项目的重点在于处理海量用户的查看投票服务信息，短时大量投票引起的高并发处理以及投票结束后的大量邮件服务处理。
此项目可以采用LVS+Nginx做负载均衡，分流到应用服务，在应用缓存部分，可以采用redis集群和MQ进一步处理高并发请求。
在投票结束后，邮件通知部分由于时效性要求不那么高，加上发邮件是个耗时操作，可以放到MQ进行异步处理。
由于用户数非常大，投票的数据可能非常多，在数据库层面，可能要进行分表分库的操作，本项目由于时间关系只用了一个数据库做演示用。

# 二、项目架构
## 2.1 系统架构
项目采用微服务的架构，按照业务划分为后台服务，会员服务以及投票服务，再加一个服务注册中心。

 1. 后台服务 -- 提供后台管理能力
 2. 会员服务 -- 提供会员相关能力
 3. 投票服务 -- 负责处理投票相关的业务

## 2.2 系统Api说明
 Api文档地址为http://[服务IP]/doc.html，里面提供了接口的测试的方法  
 用户和密码都是为admin

# 三、测试
在测试之前，需要做一些环境的配置的设置，系统需要以下运行环境：
 1. Mysql5.7+
 2. Redis3.2.8+  
 
数据库的链接和redis的配置根据实际的运行环境用相应的调整，更改的配置文件为application.yml文件  
环境配置完成后，可以按照下面的步骤和方式进行运行测试  
 1. 用doc/election_vote.sql文件创建数据库
 2. 启动eureka-server注册中心
 3. 启动election-vote-member会员服务
 4. 启动election-vote投票服务
 5. 启动election-vote-admin后台服务
 
# 四、部署说明
## 4.1 docker方式部署（简单）
1. 进入docker-deploy目录，此目录为打包好的各个微服务运行包，运行Dockerfile生成镜像。  
2. docker安装redis，用以下命令运行容器  
`docker run --name redis-server -p 16379:6379 -d redis --requirepass 'sa!2fdiEs'`  
3. docker安装mysql5.7，用以下命令运行容器  
`docker run -p 13306:3306 --name mysql-server -e MYSQL_ROOT_PASSWORD=ABCabc123 -d mysql:5.7`  
4. 打开终端，进入docker-deploy目录,运行`docker-compose up`命令即可启动所有服务
5. 启动成功后，输入http://localhost:9080,可在注册中心查看所有的服务状态  
后台服务Api文档及调试接口：http://localhost:9110/doc.html  
投票服务Api文档及调试接口：http://localhost:9120/doc.html  
会员服务Api文档及调试接口：http://localhost:9100/doc.html  
## 4.2 docker方式部署(idea+maven)
1. 运行以下命令生成对应的镜像
```
eureka-server [package,docker:build]
election-vote [package,docker:build]
election-vote-member [package,docker:build]
election-vote-admin [package,docker:build]
```  
2. docker安装redis，用以下命令运行容器  
`docker run --name redis-server -p 16379:6379 -d redis --requirepass 'sa!2fdiEs'`  
3. docker安装mysql5.7，用以下命令运行容器  
`docker run -p 13306:3306 --name mysql-server -e MYSQL_ROOT_PASSWORD=ABCabc123 -d mysql:5.7`  
4. 打开终端，进入项目根目录,运行`docker-compose up`命令即可启动所有服务
5. 启动成功后，输入http://localhost:9080,可在注册中心查看所有的服务状态  
后台服务Api文档及调试接口：http://localhost:9110/doc.html  
投票服务Api文档及调试接口：http://localhost:9120/doc.html  
会员服务Api文档及调试接口：http://localhost:9100/doc.html 
## 4.3 idea直接运行方式
先启动eureka-server，再启动其它的服务
 