package com.hb.ki_pro;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    // 이미지 uri를 이용해서 실제 이미지의 경로 얻어오기
    public static String getRealPathFromImageURI(Context context,Uri contentURI){
        Cursor cursor = context.getContentResolver().query(contentURI,null,null, null, null);
        cursor.moveToFirst();

        int idx  = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    public static String saveBitmapToPngFileSdcard(Context context, Bitmap image, String saveFileName){
        boolean isSuccess = false; // 저장 성공 여부
        // 외부 저장장치의 상태값 얻어오기
        String state = Environment.getExternalStorageState();
        String externalPath = null; // 외부 저장장치의 경로
        if(state.equals(Environment.MEDIA_MOUNTED)){
            externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else if(state.equals(Environment.MEDIA_UNMOUNTABLE)){
            return null;
        }else if(state.equals(Environment.MEDIA_UNMOUNTED)){
            return null;
        }
        // 디렉토리 만들기(패키지명을 디렉토리명으로 한다. 겹치지 않도록)
        String dirName= context.getPackageName();
        File file = new File(externalPath+"/"+dirName);
        file.mkdir(); // 디렉토리 만들기

        String path = externalPath+"/"+dirName+"/"+saveFileName;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try{
            fos = new FileOutputStream(path);
            image.compress(Bitmap.CompressFormat.JPEG,100, fos);
            isSuccess = true;
        }catch (Exception e){

        }finally {
            try{
                if(pw != null) pw.close();
                if(fos != null) fos.close();
            }catch (Exception e){

            }
        }
        if(isSuccess)
            return  path;// 성공이면 저장된 절대경로와 파일명을 리턴.
        else
            return  null;
    }

    public static void httpFileUp(Handler handler, String url, String fileName){
        new HttpFileUPThread(handler,url,fileName).start();
    }

    static class HttpFileUPThread extends  Thread{
        Handler handler;
        String url;
        String fileName;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        // 생성자
        public HttpFileUPThread(){}
        public HttpFileUPThread(Handler handler, String url, String fileName){
            this.handler = handler;
            this.url = url;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try{
                // 선택한 파일 절대 경로 이용해 파일 입력 스트림 객체를 얻음
                FileInputStream fis = new FileInputStream(fileName);

                // 파일 업로드할 서버의 url 주소를 이용해  URL 객체 생성
                URL connectUrl = new URL(url);

                // Connection 객체 얻어 오기
                conn = (HttpURLConnection)connectUrl.openConnection();
                conn.setDoInput(true);// 입력받을 수 있도록
                conn.setDoOutput(true);// 출력할 수 있도록
                conn.setUseCaches(false); // 캐쉬 사용하지 않음
                conn.setRequestMethod("POST"); // post전송

                // 파일 업로드 할수 있도록 설정
                conn.setRequestProperty("Connection","Keep-Alive");
                conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                // DataOutputStream 객체 생성
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                // 전송할 데이터 시작을 알림
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-Disposition:form-data; name\"myFile\";filename=\""+fileName
                +"\""+lineEnd);
                dos.writeBytes(lineEnd);

                // 한번에 읽어들일 수 있는 스트림 크기 얻기
                int bytesAvailable = fis.available();
                // byte 단위로 읽기 위해
                byte[] buffer = new byte[bytesAvailable];
                int byteRead = 0;
                while (true){
                    byteRead = fis.read(buffer);
                    if(byteRead == -1)break;
                    dos.write(buffer,0,byteRead);
                    dos.flush();
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
                dos.close();
                fis.close();

                int responseCode = conn.getResponseCode();
                StringBuilder builder = null;
                if(responseCode == HttpURLConnection.HTTP_OK){
                    builder = new StringBuilder();
                    InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);

                    while (true){
                        String line = br.readLine();
                        if(line == null) break;
                        builder.append(line);
                    }
                    isr.close();
                    br.close();

                }
                // 서버가 출력한 문자열  //ex1) {"isSuccess":"yes"}    ex2) <result><isSuccess>yes</isSuccess></result>
                String resultMsg = builder.toString();
                if(resultMsg.contains("yes")){  // yes 단어가 포함 되어 있냐
                    handler.sendEmptyMessage(1);
                }else{
                    handler.sendEmptyMessage(-1);
                }
            }catch (Exception e){
                handler.sendEmptyMessage(-1);
            }finally {
                conn.disconnect(); // 접속해제
            }
        }
    }
}
