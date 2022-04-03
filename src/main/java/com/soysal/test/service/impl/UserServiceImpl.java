package com.soysal.test.service.impl;

import com.soysal.test.dto.UserDto;
import com.soysal.test.entity.Address;
import com.soysal.test.entity.User;
import com.soysal.test.repo.AddressRepository;
import com.soysal.test.repo.UserRepository;
import com.soysal.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;

	@Override
	@Transactional
	public UserDto save(UserDto userDto) {
		Assert.notNull(userDto.getName(), "Username cannot be empty");

		User user = new User();
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		final User userInDb = userRepository.save(user);
		List<Address> list = new ArrayList();

		userDto.getAddresses().forEach(item -> {
			Address address = new Address();
			address.setAddress(item);
			address.setAddressType(Address.AddressType.OTHER);
			address.setActive(true);
			address.setUser(userInDb);
			list.add(address);
		});
		addressRepository.saveAll(list);
		userDto.setId(userInDb.getId());
		return userDto;
	}

	@Override
	public void delete(Long id) {
		
	}

	@Override
	public List<UserDto> getAll() {
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = new ArrayList();
		
		users.forEach(item -> {
			UserDto userDto = new UserDto();
			userDto.setId(item.getId());
			userDto.setName(item.getName());
			userDto.setLastName(item.getLastName());
			userDto.setAddresses(
					item.getAddresses() != null ?
					item.getAddresses().stream().map(Address::getAddress).collect(Collectors.toList()) :
					null);
			userDtos.add(userDto);
		});
		return userDtos;
	}

	@Override
	public Page<UserDto> getAll(Pageable pageable) {
		return null;
	}
	

	
}
