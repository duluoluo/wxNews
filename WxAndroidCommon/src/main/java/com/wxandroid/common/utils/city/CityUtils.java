package com.wxandroid.common.utils.city;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by wenxin
 */
public class CityUtils {

    public static int provinceFindIndex(String name) {
        for (int i = 0; i < AddressData.PROVINCES.length; i++) {
            if (AddressData.PROVINCES[i].contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int cityFindIndex(int Pindex, String name) {
        for (int i = 0; i < AddressData.CITIES[Pindex].length; i++) {
            if (AddressData.CITIES[Pindex][i].contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int counyFindIndex(int Pindex, int Cindex, String name) {
        for (int i = 0; i < AddressData.COUNTIES[Pindex][Cindex].length; i++) {
            if (AddressData.COUNTIES[Pindex][Cindex][i].contains(name)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList NameGetCode(String province, String city, String area) {
        ArrayList<String> marray = new ArrayList<String>();
        int Pindex = CityUtils.provinceFindIndex(province.trim());
        int Cindex = CityUtils.cityFindIndex(Pindex, city.trim());
        int CCindex = CityUtils.counyFindIndex(Pindex, Cindex, area.trim());

        marray.add(AddressData.P_ID[Pindex] + "0000");
        marray.add(AddressData.C_ID[Pindex][Cindex] + "00");
        marray.add(AddressData.C_C_ID[Pindex][Cindex][CCindex] + "");
        return marray;
    }

    public static String GetCityLocation(Context context, String province, String city) {
        String Location = null;
        try {
            String CityString = readAssets(context, "CityCenter.json");
            if(TextUtils.isEmpty(CityString)){
                return Location;
            }
            JSONObject person = new JSONObject(CityString);
            JSONArray Jarr = person.getJSONArray("provinces");
            for (int i = 0; i < Jarr.length(); i++) {
                JSONObject jsonPerovince = Jarr.getJSONObject(i);
                if (province.contains(jsonPerovince.getString("n"))) {
                    Location = jsonPerovince.getString("g");
                    JSONArray jarrcity = jsonPerovince.getJSONArray("cities");
                    for (int j = 0; j < jarrcity.length(); j++) {
                        JSONObject jsoncity = jarrcity.getJSONObject(j);
                        if (city.contains(jsoncity.getString("n"))) {
                            Location = jsoncity.getString("g");
                        }
                    }
                }
            }
        } catch (JSONException ex) {

        } finally {
            return Location;
        }
    }


    /**
     * 读取文本数据
     *
     * @param context
     *            程序上下文
     * @param fileName
     *            文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readAssets(Context context, String fileName)
    {
        InputStream is = null;
        String content = null;
        try
        {

            is = context.getResources().getAssets().open(fileName);
            if (is != null)
            {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true)
                {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            content = null;
        }
        finally
        {
            try
            {
                if (is != null) is.close();
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        return content;
    }
}
