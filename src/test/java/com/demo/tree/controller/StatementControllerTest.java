package com.demo.tree.controller;

import com.demo.tree.dto.AccountFilterRequest;
import com.demo.tree.dto.AppResponse;
import com.demo.tree.security.vo.LoginRequest;
import com.demo.tree.security.vo.LoginResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(0)
    void testAccountFilterRequestWithUser() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().userName("user").password("user").build();
        MockHttpServletRequestBuilder loginServletRequestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest));
        String content = mockMvc.perform(loginServletRequestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        AppResponse<LoginResponse> response = objectMapper.readValue(content, new TypeReference<>() {
        });
        String userAccessToken = response.payload().accessToken();

        AccountFilterRequest request = AccountFilterRequest.builder().accountId(1L).build();
        MockHttpServletRequestBuilder accountServletRequestBuilder = post("/api/v1/statements/filter")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAccessToken)
                .content(objectMapper.writeValueAsString(request));
        mockMvc.perform(accountServletRequestBuilder).andExpect(status().isOk());

        post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAccessToken);

    }

    @Test
    @Order(1)
    void testAccountFilterRequestWithAdmin() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().userName("admin").password("admin").build();
        MockHttpServletRequestBuilder loginServletRequestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest));
        String content = mockMvc.perform(loginServletRequestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        AppResponse<LoginResponse> response = objectMapper.readValue(content, new TypeReference<>() {
        });
        String userAccessToken = response.payload().accessToken();

        AccountFilterRequest request = AccountFilterRequest.builder().accountId(1L).amountFrom(500.0).amountTo(1500.0).build();
        MockHttpServletRequestBuilder accountServletRequestBuilder = post("/api/v1/statements/filter")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAccessToken)
                .content(objectMapper.writeValueAsString(request));
        mockMvc.perform(accountServletRequestBuilder).andExpect(status().isOk());

        post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAccessToken);

    }

    @Test
    @Order(2)
    void testUserLoginRequestFailed() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().userName("user").password("user123").build();
        MockHttpServletRequestBuilder loginServletRequestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest));
        mockMvc.perform(loginServletRequestBuilder).andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void testAdminLoginRequestFailed() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().userName("admin").password("admin123").build();
        MockHttpServletRequestBuilder loginServletRequestBuilder = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest));
        mockMvc.perform(loginServletRequestBuilder).andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    void testUserLoginTwice() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().userName("user").password("user").build();
        MockHttpServletRequestBuilder firstLoginServletRequest = post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest));
        mockMvc.perform(firstLoginServletRequest).andExpect(status().isConflict());
    }
}