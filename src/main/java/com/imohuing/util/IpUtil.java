package com.imohuing.util;

/**
 * @Author Yangws
 * @Date 2022-11-25 15:32
 * @Description
 * @Version 1.0.0
 **/
public class IpUtil {

    /**
     * 子网掩码位数默认24位
     */
    public static final int maskIndex = 24;

    /**
     * 根据掩码位数计算掩码
     * @return 子网掩码
     */
    public static String getNetMask() {
        StringBuilder mask = new StringBuilder();
        Integer inetMask = maskIndex;
        if (inetMask > 32) {
            return null;
        }
        // 子网掩码为1占了几个字节
        int num1 = inetMask / 8;
        // 子网掩码的补位位数
        int num2 = inetMask % 8;
        int array[] = new int[4];
        for (int i = 0; i < num1; i++) {
            array[i] = 255;
        }
        for (int i = num1; i < 4; i++) {
            array[i] = 0;
        }
        for (int i = 0; i < num2; num2--) {
            array[num1] += 1 << 8 - num2;
        }
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                mask.append(array[i]);
            } else {
                mask.append(array[i] + ".");
            }
        }
        return mask.toString();
    }
}
