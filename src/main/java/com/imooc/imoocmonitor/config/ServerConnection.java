package com.imooc.imoocmonitor.config;

import ch.ethz.ssh2.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huangxin
 * @Date: Created in 上午11:26 2018/3/25
 * @Description:
 */
@Configuration
public class ServerConnection {

    @Bean
    public Connection connection()throws Exception {
        //建立连接
        Connection connection = new Connection("192.168.1.104");
        //打开我们新建的连接
        connection.connect();
        //做一下我们的远程身份认证
        connection.authenticateWithPassword("root", "123456");
        return connection;
    }

}
