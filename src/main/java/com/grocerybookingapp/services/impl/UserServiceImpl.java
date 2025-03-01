package com.grocerybookingapp.services.impl;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.grocerybookingapp.entities.User;
import com.grocerybookingapp.exceptions.ResourceNotFoundException;
import com.grocerybookingapp.other.PageableResponse;
import com.grocerybookingapp.repositories.UserRepository;
import com.grocerybookingapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;

	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User createUser(User user) {
		//Genrate Unique id
		String id =UUID.randomUUID().toString();
		user.setUserId(id);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser=userRepository.save(user);
		return savedUser;
	}

	@Override
	public User updateUser(User user, String userId) {
		User user1 = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found With the given ID"));
		user1.setName(user.getName());
		user1.setPassword(user.getPassword());
		user1.setEmail(user.getEmail());
		//Email we are not allowing to update
		User updatedUser = userRepository.save(user1);
		return updatedUser;
	}

	@Override
	public void deleteUser(String userId) throws IOException {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found With the given ID"));
		userRepository.delete(user);
	}

	//Page
	//Page number start with 0
	@Override
	public Page<User> getAllUser(int pageNumber,int pageSize,String sortBy, String sortDir) {
		//If dsc then do it by dsc name or do it by asc and name
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		PageRequest pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<User> userListPage=userRepository.findAll(pageable);
		//Below method is created by us to get all page related details
		return userListPage;
	}

	@Override
	public User getSingleUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with given id"));
		return user;
	}
}
