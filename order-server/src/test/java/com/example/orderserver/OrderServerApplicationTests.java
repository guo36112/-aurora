package com.example.orderserver;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServerApplication.class)
@AutoConfigureMockMvc
class OrderServerApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Before
    public void setupMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    public void list() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap();
        paramMap.add("pageNum","1");
        paramMap.add("pageSize","20");
        RequestBuilder request = MockMvcRequestBuilders
                .get("/list")
                .params(paramMap);
        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(content);
        String code = jsonObject.getStr("code");
        Assert.assertEquals("200",code);

    }


    @Test
    public void add() throws Exception {
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("productId","1");
        paramMap.put("orderPrice","5.88");
        paramMap.put("shippingAddress","hunan");
        RequestBuilder request = MockMvcRequestBuilders
                .post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JSONUtil.toJsonStr(paramMap));
        MvcResult mvcResult = mockMvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = JSONUtil.parseObj(content);
        String code = jsonObject.getStr("code");
        Assert.assertEquals("200",code);

    }


}
