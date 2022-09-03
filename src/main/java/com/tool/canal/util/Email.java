package com.tool.canal.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Class Email
 * @Description 发送邮件
 * @Author 17600
 * @Date 2022-09-03 16:19
 * @Version 1.0
 **/
public class Email {
    /**
     * 邮箱host
     */
    @Value("${spring.mail.host}")
    private String host;

    /**
     * 发件邮箱地址
     */
    @Value("${spring.mail.username}")
    private String username;

    /**
     * 发件邮箱开启POP3之后设置的客户端授权码
     */
    @Value("${spring.mail.password}")
    private String password;

    /**
     * 发送邮件
     * @param email 接收邮箱(建议使用163邮箱)
     * @param userName 接收者姓名
     */
    public void sendEmail(String email,String userName,String msg) {
        try {
            StringBuilder sb = new StringBuilder(100).append("hello，sql由"+userName+"触发变更啦!"+"<br>"+msg);
            JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
            senderImpl.setHost("smtp.163.com");
            senderImpl.setUsername("17600905781@163.com");
            senderImpl.setPassword("PPXDNUWJHIPKKHHR");
            //建立邮件消息,发送简单邮件和html邮件的区别
            MimeMessage mailMessage = senderImpl.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,"utf-8");
            //设置收件人，寄件人
            messageHelper.setTo(email);
            messageHelper.setFrom("17600905781@163.com");
            messageHelper.setSubject("sql变更啦!");
            messageHelper.setText(sb.toString(),true);
            Properties prop = new Properties();
            //将这个参数设为true，让服务器进行认证,认证用户名和 密码是否正确
            prop.put("mail.smtp.auth","true");
            //设置超时时间
            prop.put("mail.smtp.timeout", "25000");
            senderImpl.setJavaMailProperties(prop);
            //发送邮件
            senderImpl.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Email email = new Email();
        email.sendEmail("17600905781@163.com","tester","testData");
    }

}
