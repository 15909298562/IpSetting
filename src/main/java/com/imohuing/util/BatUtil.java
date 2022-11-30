package com.imohuing.util;

import com.imohuing.IpSetting;
import com.imohuing.frame.IpSettingFrame;

import java.io.*;
import java.net.MalformedURLException;

/**
 * @Author Yangws
 * @Date 2022-11-25 14:27
 * @Description
 * @Version 1.0.0
 **/
public class BatUtil {

    /**
     * 运行基本bat文件，从bat文件获得输出，运行完会抱一个错：请求的操作需要提升(作为管理员运行)，但是实际上已经已管理员身份执行了，所以不用管
     * @param batPath bat文件路径
     * @param argStrings bat文件参数
     * @return
     */
    public static String runBat(String batPath, String... argStrings){
        File tempFile = new File(System.getProperty("user.dir")+"\\temp.bat");
        //读取文件并创建新的文件
        try {
            FileWriter writer = new FileWriter(tempFile);
            InputStream staticStream = IpSetting.class.getResourceAsStream(batPath);
            BufferedReader staticReader = new BufferedReader(new InputStreamReader(staticStream));
            String line;
            while((line=staticReader.readLine()) != null){
                writer.write(line + "\n");
            }
            writer.close();
            staticReader.close();
            staticStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            String cmd = "cmd.exe /c " + tempFile.getPath();
            if (argStrings != null && argStrings.length > 0) {
                for (String arg : argStrings) {
                    cmd += " " + arg;
                }
            }
            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = ps.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=bufferedReader.readLine())!=null) {
                sb.append(line + "\n");
            }
            inputStream.close();
            try {
                ps.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            inputStream.close();
            //执行完成之后删除临时文件
            tempFile.delete();
            return sb.toString().trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 自动获取当前正在使用的网络适配器
     * @return 网络适配器名称
     */
    public static String getCurrentAdapter() throws MalformedURLException {
        String batPath = IpSettingFrame.GetAdapterUrl;
        String result = runBat(batPath);
        String[] res = result.split("adapter=");
        if(res.length > 0){
            return res[1];
        }
        return "";
    }

}
