package com.soysal.test.service.impl;

import com.soysal.test.dto.UserDto;
import com.soysal.test.entity.Address;
import com.soysal.test.entity.User;
import com.soysal.test.repo.AddressRepository;
import com.soysal.test.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    public void testSave() {
        UserDto userDto = new UserDto();
        userDto.setName("Test-Name");
        userDto.setLastName("Test-Lastname");
        userDto.setAddresses(Arrays.asList("Test-Adres-1"));
        User userMock = mock(User.class);

        when(userMock.getId()).thenReturn(1L);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userMock);
        UserDto result = userService.save(userDto);

        assertEquals(result.getName(), userDto.getName());
        assertEquals(result.getId(), 1L);
    }

    @Test
    public void testSaveException() {
        UserDto userDto = new UserDto();
        userDto.setLastName("Test-Lastname");
        userDto.setAddresses(Arrays.asList("Test-Adres-1"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(userDto));
    }

    @Test
    public void testGetAll() {
        User user = new User();
        user.setId(1L);
        user.setName("Test-Name");
        user.setLastName("Test-Lastname");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<UserDto> userDtos = userService.getAll();

        assertEquals(userDtos.size(), 1);
        assertEquals(userDtos.get(0), UserDto.builder().id(1L).build());
    }

    @Test
    public void testGetAllWithAdress() {
        User user = new User();
        user.setId(1L);
        user.setName("Test-Name");
        user.setLastName("Test-Lastname");
        Address address = new Address();
        address.setAddress("Test-Adres");
        address.setAddressType(Address.AddressType.OTHER);
        user.setAddresses(Arrays.asList(address));

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<UserDto> userDtos = userService.getAll();

        assertEquals(userDtos.get(0).getAddresses().size(), 1);
    }
}