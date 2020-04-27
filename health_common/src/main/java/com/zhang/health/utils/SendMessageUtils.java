package com.zhang.health.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/27 10:57
 */
public class SendMessageUtils {

    /**
     * 区域id
     */
    private final static String REGION_ID = "cn-hangzhou";

    /**
     * ACCESS_KEY_ID
     */
    private final static String ACCESS_KEY_ID = "LTAI4FmFyKXJJgBhKiXa3Cdr";

    /**
     * SECRET
     */
    private final static String SECRET = "lqLbGYRNhDxfUIijsc6fOcsGxxPnxK";

    /**
     * 预约认证时的TEMPLATE_CODE
     */
    public final static String VALIDATE_TEMPLATE_CODE = "SMS_176911792";

    /**
     * 预约成功的TEMPLATE_CODE
     */
    public final static String NOTICE_TEMPLATE_CODE = "SMS_189028108";

    /**
     * 验证码方式的短信
     */
    public final static String CODE_STYLE = "CODE_STYLE";

    /**
     * 提醒日期方式的短信
     */
    public final static String DATE_STYLE = "DATE_STYLE";

    public static String sendMessage(String templateCode, String phoneNumber, String style, String date) {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", REGION_ID);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "小汤圆");
        request.putQueryParameter("TemplateCode", templateCode);


        try {
            //短信内容
            String param = null;

            //注意,下面这两个判断与自己aliyun官网上的短信模板的设置有关
            if (CODE_STYLE.equalsIgnoreCase(style)) {
                Integer integerCode = ValidateCodeUtils.generateValidateCode(4);
                param = integerCode + "";
                request.putQueryParameter("TemplateParam", "{\"code\":\"" + param + "\"}");
            }
            if (DATE_STYLE.equalsIgnoreCase(style)) {
               /* Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                param = simpleDateFormat.format(date);*/
                param = date;
                request.putQueryParameter("TemplateParam", "{\"date\":\"" + date + "\"}");
            }
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("短信已发送给"+phoneNumber+",内容是:"+param);
            return param;
        } catch (Exception e) {
            e.printStackTrace();
            return "发送短信失败";
        }
    }
}
