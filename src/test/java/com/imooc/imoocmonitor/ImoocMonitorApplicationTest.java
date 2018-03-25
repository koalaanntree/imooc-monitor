package com.imooc.imoocmonitor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * @Author: huangxin
 * @Date: Created in 上午11:09 2018/3/25
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ImoocMonitorApplicationTest {

    //获取上下文的对象
    @Autowired
    private WebApplicationContext webApplicationContext;
    //拿到我们的http请求模拟发射器
    private MockMvc mockMvc;

    @Before
    public void init() {
        //构建我们的模拟发射器
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void ssh() throws Exception{
        String result = mockMvc.perform(
                MockMvcRequestBuilders.post("/monitor")
                        .param("cmd", "top -bn1 | grep java 1")
        ).andReturn().getResponse().getContentAsString();
        log.info(result);
    }
}