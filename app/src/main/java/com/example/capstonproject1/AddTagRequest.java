package com.example.capstonproject1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddTagRequest extends StringRequest{
//서버 URL 설정 (php 파일 연결)
    final static private String URL = "http://rkdruddud.dothome.co.kr/AddTag.php";
    private Map<String, String> map;

    public AddTagRequest(String userID, String tagName, String tagID, String latitude, String longitude, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("tagName", tagName);
        map.put("tagID", tagID);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}
