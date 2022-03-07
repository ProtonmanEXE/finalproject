package finalproject.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String userName, String to) {
        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("teammegaraves@gmail.com");
        message.setTo(to); 
        message.setSubject("MegaRaves: Sign-up Confirmation"); 
        message.setText(            
            "Dear " +userName + ",\n\nThank you for signing up with MegaRaves. " +
            "Please proceed to log in to your account to enjoy our games.\n\n" +
            "Yours sincerely,\n" + "Team MegaRaves"
        );
        
        emailSender.send(message);

    }
}
