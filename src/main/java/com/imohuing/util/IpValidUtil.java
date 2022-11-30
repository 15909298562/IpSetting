package com.imohuing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Yangws
 * @Date 2022-11-25 15:49
 * @Description
 * @Version 1.0.0
 **/
public class IpValidUtil {

    public static final String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

    /**
     * 验证ip地址
     * @param ip
     * @return
     */
    public static boolean valid(String ip){
        Pattern pattern= Pattern.compile(regex);
        Matcher matcher=pattern.matcher(ip);
        boolean bool=matcher.matches();
        return bool;
    }

}
