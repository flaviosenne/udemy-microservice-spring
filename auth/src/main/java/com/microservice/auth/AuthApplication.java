package com.microservice.auth;

import com.microservice.auth.entity.Permission;
import com.microservice.auth.entity.User;
import com.microservice.auth.repository.PermissionRepository;
import com.microservice.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
						   BCryptPasswordEncoder bCryptPasswordEncoder){
		return args -> {
			initUsers(userRepository, permissionRepository, bCryptPasswordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
						   BCryptPasswordEncoder bCryptPasswordEncoder){
		Permission permission = null;
		Optional<Permission> findPermission = permissionRepository.findByDescription("Admin");
		if(findPermission.isEmpty()){
			permission = new Permission();
			permission.setDescription("Admin");
			permission = permissionRepository.save(permission);
		}else{
			permission = findPermission.get();
		}

		User admin = new User();
		admin.setUserName("Joao");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPassword(bCryptPasswordEncoder.encode("1234"));
		admin.setPermissions(Arrays.asList(permission));

		Optional<User> find = userRepository.findByUserName("Joao");
		if(find.isEmpty()){
			userRepository.save(admin);
		}
	}
}
