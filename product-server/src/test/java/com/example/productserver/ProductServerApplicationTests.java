package com.example.productserver;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.productserver.common.CommonPage;
import com.example.productserver.common.CommonReturnType;
import com.example.productserver.po.ProductPo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServerApplication.class)
@AutoConfigureMockMvc
class ProductServerApplicationTests {
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
    public void get() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap();
        RequestBuilder request = MockMvcRequestBuilders
                .get("/get/1")
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



}
