package com.cxf.moudule_common.Socket;


import android.util.Log;

import com.cxf.module_resource.IP_Config;
import com.cxf.moudule_common.format.BCDUtil;
import com.cxf.moudule_common.format.Convert;
import com.cxf.moudule_common.format.HexUtil;
import com.cxf.moudule_common.java8583.util.EncodeUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SoketUtil {

    public static final int SUCCEED = 0;




    Socket socket;
    OutputStream outputStream;
    OutputStreamWriter outputStreamWriter;
    InputStream inputStream;
    InputStreamReader inputStreamReader;


    public SoketUtil() {
        try {
            this.socket = new Socket();
            SocketAddress address = new InetSocketAddress(InetAddress.getByName(IP_Config.ip), IP_Config.port);
            socket.connect(address,1*60*1000);
            if(socket.isConnected()){
                Log.w("TAG","------------socket connected-----------");
            }
        }catch (ConnectException e){
          e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class Singleton{
        public static SoketUtil INSTANCE = new SoketUtil();
    }
    public static SoketUtil getINSTANCE() {
        return Singleton.INSTANCE;
    }



    /*
    *   传输数据
    * input:  bytes
    * return: 0 成功 ；-1 失败
    *
     */

    public int sentData(byte[] data){
        try{

            if(!socket.isConnected()){
                Log.e("error","socket is not connect!");
                return -1;
            }


            outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();

//            socket.shutdownOutput();
//            if(socket.isOutputShutdown()){
//                return SUCCEED;
//            }else{
//                return -1;
//            }

            return SUCCEED;
        }catch (Exception e){
            Log.e("error","------soket error-------"+e.getMessage());
            e.printStackTrace();
            return -1;
        }

    }


    /*
     *   传输数据
     *
     * return: bytes; null
     *
     */

    public String getData(){

        try {
            byte[] size = new byte[2];
            byte[] bytes;
            if(!socket.isConnected()){
                Log.e("error","socket is not connect!");
                return null;
            }



            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            inputStream.read(size);
            String dataSize = HexUtil.bytesToHexString(size);
            int c= Convert.byteArray2Short(HexUtil.hexStringToByte(dataSize),0);
            bytes = new byte[c];
            inputStream.read(bytes);
            String data = HexUtil.bytesToHexString(bytes);

            return dataSize+data;



        }catch (Exception e){
            Log.e("error","------soket error-------"+e.getMessage());
            e.printStackTrace();
            return null;
        }

    }



}
