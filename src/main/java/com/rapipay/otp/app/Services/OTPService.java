package com.rapipay.otp.app.Services;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rapipay.otp.app.OTPRepository;
import com.rapipay.otp.app.OTPResponse;
import com.rapipay.otp.controller.OTPController;
import com.rapipay.otp.entity.OTP;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidAttemptsException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.exception.InvalidOTPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OTPService.class);
	
	@Value("${attempts}")
	private int attemptpermitted;
	
	@Value("${verifytime}")
	private int timelimit;

    @Autowired
    public OTPRepository dao;

    @Autowired
    public EmailService emailService;

    public Integer generateOTP() {
        return (int) ((Math.random() * 900000) + 100000);
    }
    public boolean checkEmail(String email) {
    	
        String regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w{2,}([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(regex);
            //Creating a Matcher object
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
       
        }
    
    
    public String sendOTP(OTP enteredotp) {
    	
    	List<OTP>cEmail=dao.findByEmailAndVerified(enteredotp.getEmail(), false);
    	int i=1;
    	
    	if(cEmail.size()>0)
    	{
        for(OTP b:cEmail) {
        	System.out.println("----------");
        	if (b.getCreated_at() > (new Date().getTime() / 1000) - timelimit) {
        		i++;
        		if(i>attemptpermitted) {
        			throw new InvalidAttemptsException("Maximum Attempt Limit Reached");
        		}
        		
        	}
        }
    	}
    	if(checkEmail(enteredotp.getEmail())) {
    		 LOGGER.info("Inside Send OTP Method in service");
    		OTP otp = new OTP();
    		otp.setChannelname(enteredotp.getChannelname());
            otp.setEmail(enteredotp.getEmail());
            otp.setOrderid();
            otp.setOtp(generateOTP());
            otp.setVerified(false);
            otp.setCreated_at(new Date().getTime() /1000);
            sendOTPMail(otp);
            dao.saveAndFlush(otp);
           
            return "OTP sent successfully";
    	}
        throw new InvalidEmailException("Entered Email is Wrong!!");
    }

    public String verifyOTP(OTP otp){
    	LOGGER.info("Inside Verify Email Method in service");
        OTPResponse response = new OTPResponse();
        List<OTP>checkEmail=dao.findByEmailAndVerified(otp.getEmail(), false);
        
        if(checkEmail==null || checkEmail.size()==0) {
        	response.setMessage("-------------");
        	response.setStatus(false);
        	System.out.println("-----------------------------------");
        	throw new EmailNotFoundException("Email Not Exixts");
        	  //return response.toString();
        }
     // System.out.println("------------------------------------------");
       
        List<OTP> otps;
        otps = (dao.findByEmailAndOtpAndVerified(otp.getEmail(),otp.getOtp(),false));
        
       
        if (otps == null || otps.size() == 0) {
            response.setMessage("OTP verification failed");
                response.setStatus(false);
                response.toString();
                throw new InvalidOTPException("Entered OTP is Wrong!");
        }

        for(OTP o : otps) {
                if (o.getCreated_at() > (new Date().getTime() / 1000) - 300) {
                    o.setVerified(true);
                    dao.save(o);
                    response.setMessage("OTP verified successfully");
                    response.setStatus(true);
                    return "OTP verified successfully";
                }
            }
            return "This OTP has expired. Please request a new one";
            
        }

        public void sendOTPMail(OTP otp) {
        
            emailService.sendSimpleMessage(otp.getEmail(),"OTP for email verification","The otp for verifying your email address is: "+otp.getOtp());

        }
		public String sendsmsOTP(OTP enteredotp) {
			LOGGER.info("Inside Send SMS OTP Method in service");
			// TODO Auto-generated method stub
			OTP otp = new OTP();
			otp.setChannelname(enteredotp.getChannelname());
            otp.setPhonenumber(enteredotp.getPhonenumber());
            otp.setOrderid();
            otp.setOtp(generateOTP());
            otp.setVerified(false);
            otp.setCreated_at(new Date().getTime() /1000);
            System.out.println(otp.getOtp());
            dao.saveAndFlush(otp);
            return "OTP sent successfully";
			
		}
    }
