package com.soysal.test.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class UserDto {
	
	private Long id;

	@NotNull
	private String name;
	
	private String lastName;
	
	private List<String> addresses;
}
