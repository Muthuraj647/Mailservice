package com.muthu.sampleProject.MailService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.muthu.sampleProject.MailService.Entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {

	@Query("from UserDetails where email=?1")
	public UserDetails findByEmailId(String email);
	
	
}
