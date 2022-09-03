package com.tool.canal.util;

/**
 * @Class EmailUtil
 * @Description 发送邮件工具类
 * @Author 17600
 * @Date 2022-09-03 15:03
 * @Version 1.0
 **/
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.util.StringUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailUtil{
    public static String result = null;
    public String sendMail(String address, String subject, String msg, Boolean isSSl) throws EmailException {

        Properties pro = System.getProperties();
        pro.put("mail.smtp.auth","true");
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(msg)) {
            throw new EmailException();
        }
        try {
//            HtmlEmail email = new HtmlEmail();
            Email email = new SimpleEmail();
            List<String> list = new ArrayList<>();
            list.add(address);
            String[] tos = list.toArray(new String[list.size()]);

            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com";qq如下：smtp.exmail.qq.com
            email.setHostName("smtp.163.com");
            if (isSSl) {
                email.setSSLOnConnect(true);
                //默认端口为25
                email.setSmtpPort(25);
            }
            // 字符编码集的设置
            email.setCharset("UTF-8");
            // 收件人的邮箱
            email.addTo(tos);
            // 发送人的邮箱以及发件人名称
            email.setFrom("17600905781@163.com", "17600905781@163.com");
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
//            email.setAuthentication("17600905781@163.com", "41121117@zxl");
            // 创建验证器
            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("17600905781@163.com",
                            "客户端授权码");
                }};

            // 要发送的邮件主题
            email.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
//            email.setHtmlMsg(msg);
            email.setMsg(msg);
            // 发送邮件结果
            result = email.send();

        } catch (Exception e) {
            e.printStackTrace();
            throw new EmailException();
        }
        return result;
    }

    public static void main(String[] args) throws EmailException {
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.sendMail("17600905781@163.com","测试邮件","SQL",true);
    }
}
