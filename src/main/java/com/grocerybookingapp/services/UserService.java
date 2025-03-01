package com.grocerybookingapp.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.grocerybookingapp.entities.User;
import com.grocerybookingapp.other.PageableResponse;

public interface UserService {
	  
	  //Create
	  User createUser(User user);
	  
	  //Update
	  User updateUser(User user,String ID);
	  
	  //delete
	  void deleteUser(String userId) throws IOException;
	  
	  //Get all Users
	  Page<User> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
	  
	  //get Single user by id
	  User getSingleUserById(String userId);
}
