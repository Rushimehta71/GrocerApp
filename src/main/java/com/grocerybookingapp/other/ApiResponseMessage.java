package com.grocerybookingapp.other;

import org.springframework.http.HttpStatus;

import com.grocerybookingapp.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {
private String message;
private boolean success;
private HttpStatus status;
}
