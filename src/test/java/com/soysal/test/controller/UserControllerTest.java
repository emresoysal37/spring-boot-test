package com.TestLastName.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soysal.test.TestApplication;
import com.soysal.test.controller.UserController;
import com.soysal.test.dto.UserDto;
import com.soysal.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = TestApplication.class)
public class UserControllerTest {

    private final static String CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        // given
        UserDto userDto = UserDto.builder().name("TestName").lastName("TestLastName").build();

        // when
        ResultActions actions = mockMvc.perform(post("/user")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(userDto)));

        // then
        ArgumentCaptor<UserDto> captor = ArgumentCaptor.forClass(UserDto.class);
        verify(userService, times(1)).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("TestName");
        assertThat(captor.getValue().getLastName()).isEqualTo("TestLastName");
        actions.andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturns400() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(post("/user")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString("test-value")));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInput_thenReturns400() throws Exception {
        // given
        UserDto kisiDto = UserDto.builder().lastName("TestLastName").build();

        // when
        ResultActions actions = mockMvc.perform(post("/user")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(kisiDto)));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void whenCallTumunuListele_thenReturns200() throws Exception {
        // given
        UserDto userDto = UserDto.builder().name("TestName").lastName("TestLastName").build();
        when(userService.getAll()).thenReturn(Arrays.asList(userDto));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/user")
                .contentType(CONTENT_TYPE)).andReturn();

        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(userService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Arrays.asList(userDto)))
                .isEqualToIgnoringWhitespace(responseBody);
    }

    @Test
    void whenCallGetAll_thenReturnsNoData() throws Exception {
        // given
        when(userService.getAll()).thenReturn(Collections.emptyList());

        // when
        MvcResult mvcResult = mockMvc.perform(get("/user")
                .contentType(CONTENT_TYPE)).andReturn();

        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(userService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Collections.emptyList()))
                .isEqualToIgnoringWhitespace(responseBody);
    }
}
