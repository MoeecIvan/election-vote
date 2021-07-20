# 基于哪个镜像
FROM java:8
# 将本地文件夹挂载到当前容器
VOLUME /tmp
# 拷贝文件到容器，election-vote-admin-1.0.0.jar这里是maven打包后的名字
ADD election-vote-admin-1.0.0.jar app.jar
RUN bash -c 'touch /app.jar'
# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 9080