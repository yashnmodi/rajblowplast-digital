package com.rajblowplast.digital.sms.service;

import com.rajblowplast.digital.sms.model.Otp;
import com.rajblowplast.digital.sms.repository.OtpRepo;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class OtpService {
    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    OtpRepo otpRepo;

    static String numbers = "0123456789";

    public int generateOtpId(){
        logger.debug("OTP ID generated successfully.");
        return 123;
    }

    public Otp generateOtp(String email){
        Random random = new Random();
        char[] otp = new char[6];
        for (int i=0;i<6;i++){
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        logger.debug("OTP generated successfully.");
        int id = generateOtpId();
        Otp obj = new Otp(email, id, String.valueOf(otp));
        persistOtp(obj);
        return obj;
    }

    public void persistOtp(Otp obj){
        LocalDateTime current = LocalDateTime.now();
        obj.setGeneratedOn(current.format(AppConstants.dtf));
        otpRepo.save(obj);
        logger.debug("OTP persisted successfully.");
    }

    public void discardOtp(Otp otp){
        otpRepo.delete(otp);
    }

    public String verifyOtp(String userEmail, int id, String otp){
        List<Otp> otpData = otpRepo.findByEmail(userEmail);
        for(Otp o : otpData){
            if(id == o.getOtpId() && otp.equalsIgnoreCase(o.getOtp())){
                LocalDateTime current = LocalDateTime.now();
                LocalDateTime generatedOn = LocalDateTime.parse(o.getGeneratedOn(), AppConstants.dtf);
                long timeDiff = Duration.between(generatedOn, current).toMinutes();
                if(timeDiff > 1)
                    return "expired";
                discardOtp(o);
                return "matched";
            }
        }
        return "invalid";
    }

}
