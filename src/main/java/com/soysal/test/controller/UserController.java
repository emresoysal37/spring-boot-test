package com.soysal.test.controller;

import com.soysal.test.dto.UserDto;
import com.soysal.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto){
		return ResponseEntity.ok(userService.save(userDto));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAll(){
		return ResponseEntity.ok(userService.getAll());
	}
}
