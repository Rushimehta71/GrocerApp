package com.grocerybookingapp.other;

import com.grocerybookingapp.entities.User;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private User user;
}