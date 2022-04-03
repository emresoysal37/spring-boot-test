package com.soysal.test.service.impl;

import com.soysal.test.dto.UserDto;
import com.soysal.test.entity.Address;
import com.soysal.test.repo.AddressRepository;
import com.soysal.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSave() {
        UserDto userDto = new UserDto();
        userDto.setName("Test-Name");
        userDto.setLastName("Test-Lastname");
        userDto.setAddresses(Arrays.asList("Test-Address-1"));

        UserDto result = userService.save(userDto);
        List<Address> addressList = addressRepository.findAll();

        assertTrue(result.getId() > 0L);
        assertEquals(addressList.size(), 1);
    }
}
