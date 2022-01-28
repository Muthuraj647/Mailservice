package com.muthu.sampleProject.MailService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.muthu.sampleProject.MailService.Entity.ConfirmationToken;
import com.muthu.sampleProject.MailService.Entity.UserDetails;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	
	@Query("from ConfirmationToken where confirmationToken=?1")
	public ConfirmationToken findByConfirmationToken(String token);
	
	/*
	 * @Query("from UserDetails where email=?1") public UserDetails
	 * findByEmailId(String email);
	 */

}
