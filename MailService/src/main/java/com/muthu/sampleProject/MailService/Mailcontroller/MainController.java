package com.muthu.sampleProject.MailService.Mailcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.muthu.sampleProject.MailService.Entity.ConfirmationToken;
import com.muthu.sampleProject.MailService.Entity.UserDetails;
import com.muthu.sampleProject.MailService.Repository.ConfirmationTokenRepository;
import com.muthu.sampleProject.MailService.Repository.UserRepository;
import com.muthu.sampleProject.MailService.service.ConfirmationTokenService;

@Controller
public class MainController {

	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
	@RequestMapping("/register")
	public String home(Model model)
	{
		UserDetails userDetails=new UserDetails();
		model.addAttribute("user",userDetails);
		return "register";
	}
	
	@RequestMapping("/check")
	public String saveUser(Model model, @ModelAttribute UserDetails userDetails)
	{
		UserDetails userDetails2=confirmationTokenService.findUser(userDetails.getEmail());
		if(userDetails2!=null)
		{
			/*
			 * String error="Email Already Exists!!!"; model.addAttribute("userErr", error);
			 * return "register";
			 */
			
			System.out.println("Email Already Exists!!!");
			return "redirect:/register";
		}
		confirmationTokenService.saveUser(userDetails);
		ConfirmationToken confirmationToken=new ConfirmationToken(userDetails);
		confirmationTokenService.saveToken(confirmationToken);
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setTo(userDetails.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("muthurajrmuthu32@gmail.com");
		mailMessage.setText("To confirm  Registration click here "+"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

		confirmationTokenService.sendMail(mailMessage);
		
		model.addAttribute("user", userDetails);
		
		return "validate";
	}
	
	@RequestMapping(value =  "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
	public String confirmAccount(@RequestParam String token, Model model)
	{
		System.out.println(token);
		ConfirmationToken confirmationToken=confirmationTokenService.findByToken(token);
		
		if(confirmationToken!=null)
		{
			UserDetails userDetails=confirmationTokenService.findUser(confirmationToken.getUserDetails().getEmail());
			
			userDetails.setEnabled(true);
			
			confirmationTokenService.saveUser(userDetails);
			
			model.addAttribute("user", userDetails);
			
			return "home";
		}
		
		
		
		return "error";
	}
}
