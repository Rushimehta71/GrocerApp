package com.grocerybookingapp.controllers;

import java.io.IOException;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocerybookingapp.entities.User;
import com.grocerybookingapp.other.ApiResponseMessage;
import com.grocerybookingapp.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "These are  User APIs")
public class UserController {

	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping()
	@Operation(summary = "Post new user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User userDto1 = userService.createUser(user);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{userId}")
	@Operation(summary = "Update exsting user by userid")
	// update
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = "New user created !!") })
	public ResponseEntity<User> updateUser(@PathVariable("userId") String userId,
			@Valid @RequestBody User userForUpdate) {
		User updatedUserDto = userService.updateUser(userForUpdate, userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	@Operation(summary = "Delete exsting user by userId")
	// delete
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException {
		System.out.println("Deleting User");
		userService.deleteUser(userId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("User got deleted").success(true)
				.status(HttpStatus.OK).build();
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping()
	@Operation(summary = "Get all exsting users")
	// http://localhost:8080/users?pageNumber=0&pageSize=10&sortBy=name&sortDir=asc
	public ResponseEntity<Page<User>> getAllUser(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		System.out.println("RRR");
		return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}
}
