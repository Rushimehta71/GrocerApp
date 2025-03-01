package com.grocerybookingapp.entities;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Entity
@Table(name = "user")

public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(name = "UserId",description = "Id of user !!")
	private String userId;

	@Column(name = "user_name")
	@Schema(name = "Name",description = "Name of user !!")
	private String name;

	@Column(name = "user_email", unique = true)
	@Schema(name = "Email",description = "Email of user !!")
	private String email;

	@Column(name = "user_password", length = 500)
	@Schema(name = "Password",description = "Password of user !!")
	private String password;

	@Pattern(regexp = "(?i)^(ADMIN|CUSTOMER)$", message = "Please enter a valid user role: Admin or Customer!!")
	@Schema(name = "role", accessMode = Schema.AccessMode.READ_ONLY, description = "Role of user !!")
	private String role;

	// implement this for managinging roles
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> "ROLE_" + this.role.toUpperCase());
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
