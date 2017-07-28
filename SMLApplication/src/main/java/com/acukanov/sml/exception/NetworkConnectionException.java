package com.acukanov.sml.exception;


import com.acukanov.sml.utils.LogUtils;
import com.google.gson.annotations.SerializedName;

import java.net.UnknownHostException;

import retrofit2.HttpException;

public class NetworkConnectionException {
    private static final String LOG_TAG = LogUtils.makeLogTag(NetworkConnectionException.class);
    private static final String NAME = "name";
    private static final String MESSAGE = "message";
    private static final String CODE = "code";
    private static final String STATUS = "status";
    private static final String TYPE = "type";

    @SerializedName(NAME)
    private String name;
    @SerializedName(MESSAGE)
    private String message;
    @SerializedName(CODE)
    private int code;
    @SerializedName(STATUS)
    private int status;
    @SerializedName(TYPE)
    private String type;

    public NetworkConnectionException() {}

    public NetworkConnectionException(HttpException exception) {
        this.message = exception.message();
        this.code = exception.code();
        this.type = "";
        this.name = "";
    }

    public NetworkConnectionException(Throwable exception) {
        this.message = exception.getMessage();
        this.code = 0;
        this.type = "";
        this.name = "";
    }

    // region getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    // endregion

    public static NetworkConnectionException getError(UnknownHostException exception) {
        return new NetworkConnectionException(new Throwable("No internet connection"));
    }

    public static NetworkConnectionException getError(Throwable exception) {
        if (exception instanceof UnknownHostException) {
            return getError((UnknownHostException) exception);
        } else {
            return new NetworkConnectionException(exception);
        }
    }
}
