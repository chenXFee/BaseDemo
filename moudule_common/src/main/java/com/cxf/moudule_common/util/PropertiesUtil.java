package com.cxf.moudule_common.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/27.
 */

public class PropertiesUtil {
    
    /***
     * 如果当前的文件不存在获取Properties为空
     * @param context
     * @param file
     * @return
     */
    public static Properties loadConfig(Context context, String file) {
        File File=new File(file);
        if(!File.exists()){
            return null;
        }
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
