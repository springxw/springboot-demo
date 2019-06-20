package com.xe.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.xe.demo.common.annotation.Authority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/wg/pay/")
public class PayController {

    @Authority(opCode = "0401", opName = "支付宝购手机页面")
    @RequestMapping("zfb")
    public String payZfbPage(Map<String, Object> map) {
        return "auth/wg/zfb";
    }

    @Authority(opCode = "0401", opName = "支付宝购手机")
    @RequestMapping("zfb_pay")
    @ResponseBody
    public void payZfbDeal(@RequestParam String money, @RequestParam String zfb_pp, HttpServletResponse httpResponse) {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(URL,
                APP_ID, RSA_RRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://www.baidu.com");
        alipayRequest.setNotifyUrl("http://www.baidu.com");
        //填充业务参数
        String subject = zfb_pp.equals("1") ? "HuaWei" : "iPhoneXS";
        alipayRequest.setBizContent("{" +
                "\"out_trade_no\":\"" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "\"total_amount\":\"" + money + "\"," +
                "\"subject\":\"" + zfb_pp + "\"," +
                "\"body\":\"" + subject + "\"" +
                "}");

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            System.out.println("data:" + form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        try {
            httpResponse.setContentType("text/html;charset=" + CHARSET);
            httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String URL = "https://openapi.alipaydev.com/gateway.do";
    public static final String APP_ID = "2016092200571453";
    public static final String FORMAT = "json";
    public static final String CHARSET = "utf-8";
    public static final String SIGN_TYPE = "RSA2";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1cW4eRfVUP+3e8gPJ8ZZ1F7u3UJ3mwjDHmliPrj5jmxfswA4bJFr9wAFb4QLIDoqTi3dS3WFO5k0o/bSD25y7PZ9ru9R1vrjKhMRp06od+8f+Z2xugRISCyYJhYU7zTIDW2NQZdXK1aq62oRjiErV3hqkU5xP6P2Y2yMZR0dmAhR5GdbzhFW7cF5RDnY9wzHTry8HKoGrTsZRszwqzbYTu3WVNCAYpruvGO6R+M+KYOtNsPiN2hbRKnJyExQEtV4Fc+0UkiluM/lbnxq8ryDnmBYEub9VlRpsLbmyhCpL5NZ5OCRFzOM7KyS6p2sQ4r5LWZLIIdAR4IVWfDk39pC1QIDAQAB";
    public static final String RSA_RRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCIJSUhXxne2z4uSxtSHguROxDdom5hCz83ZfMq76hn945bdL0YGrvN6VdKAGgVYQ9G89EAb0nr5SMYNYj+ZBjb8tjJCXF4TCFDQvx/eofTlN2HBGOT202/iXozkHHrdmPOpkSj+hQYdGIoVC0md/aPStX9hQ54YRVYkSIpZSAtARpv9MID0R9VxZJ/wMwb5EguJ+SjL369x52sTHXu8ler/ykpNM7bU8HZ19wPGWYNKL14FciTLsoFBWGvwB2QbBiJUosfSbIYWt7DZ98QcjwkFcpaVB/JZe6GSRD1tKgSjGZDtwTxg1BlnBjhDX0qXnjBRPwShPM/ZC30AvAeSqgVAgMBAAECggEACRN42zWs0teI2Ks3Ig1Qthja8fuPIjcOutG4GUeq0WuGeQBaTs4RcRO6Nvu59Jro2tEMQNwqcGhxI38GJ/diVN9mplhT0TI6EXLYxGLVxTwqXILlZIj9MOSoWeGqRok49CDPeKMGTe8MUCX0cJkUvc6+X01MfqfyNVpPjbTpJTbRfa0ySG/UhqC4evDicXwjngJLxQvWCuoRuD4kaOitdKgUBy0rH8PPhgMVmW1KgNqdDpHCwXP51LpxoIDVry0kSwaQGFtwrXm5QxCfJ2VIfrptli1OFlrRI1tNa6InbTmW8F6B3rV0sNKrZVnI+XAFcH0sYu88yBbH6MGxGZ/67QKBgQDV/ZJypDafpZmhis1uQ2i4ykk0FhpdWPNlwDJmOnfwNURltyUDavpEwaq3TwfnSxupMtai+SrwsdF8YgaTIgS7NXWMl21TTyq6U++L3KwA606jDdszu5miHpZsRVNtqTTif0K37PRgOKG2v+KDnLZ09bQYdtUUXva4FGSbRT/y/wKBgQCi31Rs7Tha3ShrUoFCotWdNbPPtBIyDKMyOGZuNxSamOrjXV3EPFJDvZysA+1nqck7V6f0Ip1oqjMc+LxIoPsr3SixA8HTKOh/F/66cerrAUsXF1VW81GhiG0pwtyu/hzaFGjrYu9rj0ddJdhHWp7hmseBsK+YIkFHumArwSFo6wKBgQCACoAMVLHSiNYKUPFm3rLUj0WKAwe3fzf0VDJT9Pw7IMRSUGm3R9WEOpWZpZJHFSvx69d+ILVO3DdKw6THeP13vx8dBqaFyML6Rqr49wt8aU/eRLY6mC/vT3oWQs0OusZIGHmlcAUxZfFtVheFRMnv5ezjDoek7sWVk643MhJ3pQKBgQCKlkfLS+rY/jhHYfPVFKN5Wtm+0bVt2uz+bZlTDRE+HwI3JxoDQWSqeWwXvlvDCmjNUt/5z35rMe++JjRoBHrUryHRxaWmnLeAdZVtZkODZh1T76nRC8eZEHH6x/FqAZXhRt6j9BJPTqJlsy8eQBDTXRWH2CUB6Zvsgb0VXgekswKBgQCMzT7+IFT3RZ7s+OMjEv+zl1toHqHWVmI9QE6VXEPzkk2IXtlBiL0xjuLIEE/KaPy6D+fzMYzCkPYYxcp+zoa7CoDsXMM3Op7D1FbkYRcc/RTUvPQJJtTK2p7fhENo5LM03HgDjnIzTe9bZGOdd/HjosKc0KIFHQbpCb2S3VJBEw==";

}
