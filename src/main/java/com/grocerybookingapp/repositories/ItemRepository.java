package com.grocerybookingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.grocerybookingapp.entities.Item;

@Service
public interface ItemRepository extends JpaRepository<Item,String>{
}
