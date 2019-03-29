package com.cxf.moudule_common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.cxf.moudule_common.util.encryption.DesUtil;

import java.io.File;

/**
 * 类作用描述:
 * 作者: chenjf
 * 创建时间:2017-02-21:10:03
 * 修改人:
 * 修改时间:
 * 修改内容:
 */
public class FileCacheData {

    private FileCacheData() {

    }


    //秒
    public static int saveDataTime=60*10;
//    public static int saveDataTime=0;

    public static File file;
    private static ACache aCache;


    public FileCacheData(Context context){
        if(file==null){
            file = new File(FileIOUtils.getDiskFileDir(context) + "/" + AndroidInfoUil.getPackageName(context) + "/");
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        aCache = ACache.get(file);
    }





    public void putEncrypt(@NonNull String key, @NonNull String value) {
        String date = DesUtil.encode(value);
        aCache.put(key, date);
    }

    public String getStringNOEncrypt(@NonNull String key) {
        String dat = aCache.getAsString(key);
        if (!TextUtils.isEmpty(dat)) {
            try {
                return DesUtil.decode(dat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public ACache getACache() {
        return aCache;
    }


}