package com.muthu.sampleProject.MailService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.muthu.sampleProject.MailService.Entity.ConfirmationToken;
import com.muthu.sampleProject.MailService.Entity.UserDetails;
import com.muthu.sampleProject.MailService.Repository.ConfirmationTokenRepository;
import com.muthu.sampleProject.MailService.Repository.UserRepository;

@Service
public class ConfirmationTokenService {

	@Autowired
	private JavaMailSender javaMailSender;
	

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void sendMail(SimpleMailMessage mailMessage)
	{
		javaMailSender.send(mailMessage);
	}

	public UserDetails findUser(String email) {
		try {
			return userRepository.findByEmailId(email);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

	public void saveUser(UserDetails userDetails) {
		
		userRepository.save(userDetails);
		
	}

	public void saveToken(ConfirmationToken confirmationToken) {
		// TODO Auto-generated method stub
		confirmationTokenRepository.save(confirmationToken);
	}

	public ConfirmationToken findByToken(String token) {
		// TODO Auto-generated method stub
		
		
		return confirmationTokenRepository.findByConfirmationToken(token);
	}

}
