package com.w.sportmanager.util;
import lombok.Data;

@Data
public class JSONResult<T> {

    /**
     * 相应状态码status
     */
    private Integer status;

    /**
     * 报错信息message
     */
    private String message;

    /**
     * 数据部分data
     */
    private T data;

    public JSONResult() {
        this.status = 200;
        this.message = "Successfully!";
    }

    /**
     * 自定义状态码以、返回信息
     * @param status
     * @param message
     */
    public JSONResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 自定义状态码以、返回信息、返回值
     * @param status
     * @param message
     * @param data
     */
    public JSONResult(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
