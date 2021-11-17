package com.rapipay.otp.app.Services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rapipay.otp.app.OTPRepository;
import com.rapipay.otp.entity.OTP;

@SpringBootTest
class OTPServiceTest {
	
	@Autowired
	private OTPService otpservice;
	
	@Autowired
	private OTPRepository dao;

	@Test
	@DisplayName("Validate Email 1")
	void testCheckEmail1() {
		//fail("Not yet implemented");
		//OTPService otpService=new OTPService();
	    boolean expected=true;
	    boolean actual=otpservice.checkEmail("abc@gmail.com");
	    assertEquals(expected,actual);
	}
	
	@Test
	@DisplayName("Validate Email 2")
	void testCheckEmail2() {
		//fail("Not yet implemented");
		//OTPService otpService=new OTPService();
	    boolean expected=false;
	    boolean actual=otpservice.checkEmail("abc@gmail.");
	    assertEquals(expected,actual);
	}

	@Test
	void testSendOTP() {
		//fail("Not yet implemented");
		//OTPService otpService=new OTPService();
		assertThrows(Exception.class, ()->{
			otpservice.sendOTP(new OTP("maymail",897428,823,true));
	});
	}
	
	@Test
	void testSendOTP2() {
		String expected = "OTP sent successfully";
		assertEquals(expected,otpservice.sendOTP(new OTP("mayank1@gmail.com",835987,7452,false)));
	}
	
	@Test
	void testsendOTP3() {
		
		
		assertThrows(Exception.class,()->{
			otpservice.sendOTP(new OTP("may@gmail.com",897428,823,true));
	});
	}

	@Test
	void testVerifyOTP() {
		//fail("Not yet implemented");
		
		//OTPService otpService=new OTPService();
		assertThrows(Exception.class, ()->{
			otpservice.verifyOTP(new OTP("mayank@gmail.com",984934,83489129,false));
	});
		
	}
	
	@Test
	void testVerifyOTP2() {
		//fail("Not yet implemented");
		
		//OTPService otpService=new OTPService();
		List<OTP> otps;
        otps = (dao.findByEmailAndOtpAndVerified("abc8@gmail.com",773836,false));
		String expected="This OTP has expired. Please request a new one";
		assertEquals(expected,otpservice.verifyOTP(otps.get(0)));
				
	}
//	
//	
	@Test
	void testVeifyOTP3() {
		
		String otpmsg = otpservice.sendOTP(new OTP("mayank@gmail.com",835987,7452,false));
		String expected = "OTP verified successfully";
		List<OTP> otps;
        otps = (dao.findByEmailAndOtpAndVerified("mayank@gmail.com",835987,false));
		
		assertEquals(expected,otpservice.verifyOTP(otps.get(0)));
		
	}
	
	@Test
	void testsendsmsOTP() {
		
	    String expected = "OTP sent successfully";
	    assertEquals(expected,otpservice.sendsmsOTP(new OTP("sms","8794595040",986596,false,8584959)));
	}
	
	

}
