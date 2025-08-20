package com.mokiu.common.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 这个 Result 类是一个通用 API 响应封装工具，用于标准化后端接口的返回格式（符合 RESTful 风格）
@Data // 生成get set方法
@NoArgsConstructor // 无参构造
@AllArgsConstructor // 全参构造
public class Result <T> {

    private Integer code; // 状态码
    private String message; // 返回消息
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }

    public static <T> Result<T> success(T data,String message){
        return new Result<>(20000,message,data);
    }

    public static <T> Result<T> success(String message){
        return new Result<>(20000,message,null);
    }

    public static<T>  Result<T> fail(){
        return new Result<>(20001,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code, String message){
        return new Result<>(code,message,null);
    }

    public static<T>  Result<T> fail( String message){
        return new Result<>(20001,message,null);
    }
}
