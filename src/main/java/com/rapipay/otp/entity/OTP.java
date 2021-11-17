package com.rapipay.otp.entity;

import java.sql.Date;
import java.util.Random;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer userid;
    private int orderid;
    private String channelname;
    private String phonenumber;
    private String email;
    private Integer otp;
    private boolean verified;
    private long created_at;

    public OTP() {
    }

    public OTP( String email, Integer otp, long created_at,boolean verified) {
    	this.orderid=1000+new Random().nextInt(9000);
        this.email = email;
        this.otp = otp;
        this.verified = verified;
        this.created_at = created_at;
    }
    
    
    
    

	public OTP(String channelname, String phonenumber, Integer otp, boolean verified, long created_at) {
		super();
		this.channelname = channelname;
		this.phonenumber = phonenumber;
		this.otp = otp;
		this.verified = verified;
		this.created_at = created_at;
	}

	public Integer getId() {
        return userid;
    }

    public void setId(Integer id) {
        this.userid = id;
    }

    public int getOrderid() {
		return orderid;
	}

	public void setOrderid() {
		this.orderid = 1000+new Random().nextInt(9000);
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

	@Override
	public String toString() {
		return "OTP [userid=" + userid + ", orderid=" + orderid + ", email=" + email + ", otp=" + otp + ", verified="
				+ verified + ", created_at=" + created_at + "]";
	}

    


    
}
