package com.example.kok.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String email,String name, HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        String code = createCode();

        Cookie cookie = new Cookie("code", code);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        Cookie emailCookie = new Cookie("email", email);
        emailCookie.setPath("/");
        emailCookie.setMaxAge(60 * 30);
        response.addCookie(emailCookie);

        String receiver = email;
        String sender = "sinyoung3095@gmail.com";
        String title = "콕 인증메일";

        StringBuilder body = new StringBuilder();
        body.append("<html><body style='margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f5f6f8;'>");
        body.append("<div style='max-width:600px; margin:40px auto; background:#ffffff; border-radius:8px; padding:40px; border:1px solid #e5e5e5;'>");

        body.append("<h2 style='color:#333333; margin-top:0; text-align:center;'>이메일 인증 요청</h2>");
        body.append("<p style='font-size:16px; color:#555555; line-height:1.6;'><strong>" + name +
                "</strong>님,<br>아래 버튼을 클릭하여 이메일 인증을 완료해 주세요.</p>");

        body.append("<div style='text-align:center; margin:40px 0;'>");
        body.append("<a href='http://localhost:10000/mail/find-password-ok?code=" + code +
                "' style='display:inline-block; background-color:#4A7BFF; color:#ffffff; padding:14px 28px; text-decoration:none; border-radius:6px; font-size:16px; font-weight:bold;'>");
        body.append("인증하러 가기</a></div>");

        body.append("</div>");
        body.append("</body></html>");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(body.toString(), true);

//        FileSystemResource fileSystemResource = new FileSystemResource(new File("", ""));
//        mimeMessageHelper.addInline("", fileSystemResource);


        javaMailSender.send(mimeMessage);
    }

    private String createCode(){
        String codes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code = "";
        Random random = new Random();

        for(int i=0; i<10; i++){
            code += codes.charAt(random.nextInt(codes.length()));
        }

        return code;
    }

    public void sendMailByNotice(String email,String name,String noticeTitle,String companyName) throws MessagingException {


        String receiver = email;
        String sender = "sinyoung3095@gmail.com";
        String title = "(콕)알림 : "+ noticeTitle;

        StringBuilder body = new StringBuilder();
        body.append("<html><body>");
        body.append("<p>"+name+"님 팔로우 하신 "+companyName+ "기업의 공지가 올라왔습니다."+"</p>");
        body.append("<img src='https://camo.githubusercontent.com/1c71bb5df10b2235cf4f2b61b098f43cade4b78581d639fdd1bcc6faacc719fe/68747470733a2f2f696d616765732e756e73706c6173682e636f6d2f70686f746f2d313530373637393739393938372d6337333737393538376363663f69786c69623d72622d342e312e3026697869643d4d3377784d6a4133664442384d48787761473930627931775957646c664878386647567566444238664878386641253344253344266175746f3d666f726d6174266669743d63726f7026713d383026773d31313731' alt='공지 이미지' "
                + "style='max-width:600px; width:100%; border-radius:8px; margin-top:20px;'>");

        body.append("</body></html>");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(body.toString(), true);



        javaMailSender.send(mimeMessage);
    }
}















