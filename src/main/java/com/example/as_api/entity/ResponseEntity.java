package com.example.as_api.entity;

import com.example.as_api.util.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseEntity {
    private int code;
    private String message;
    private Object data;
    private Map<String, Object> extra = new HashMap<>();

    public static ResponseEntity of(ResponseCode responseCode) {
        return new ResponseEntity(responseCode);
    }

    private ResponseEntity(ResponseCode responseCode) {
        this.message = responseCode.msg();
        this.code = responseCode.code();
        //HiConfigDelegate.bindConfig(extra);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Map<String, Object> getExtra() {
        return extra == null || extra.isEmpty() ? null : extra;
    }


    /*
     * return this是为了方便链式调用
     * */
    public ResponseEntity setCode(int code) {
        this.code = code;
        return this;
    }

    public ResponseEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntity setData(Object data) {
        this.data = data;
        return this;
    }

    public ResponseEntity addParams(String key, Object value) { // 这个方法提供了，给data里面添加数据的方法
        if (data == null) data = new HashMap<>();
        ((Map) data).put(key, value);
        return this;

    }

    public static ResponseEntity success(Object data) { //这个方法是response会返回数据时调用
        return ResponseEntity.of(ResponseCode.RC_SUCCESS).setData(data);
    }

    public static ResponseEntity successMessage(String msg) {//这个方法是response仅返回msg时调用
        return ResponseEntity.of(ResponseCode.RC_SUCCESS).setMessage(msg);
    }
}

