package ru.egmohf.cyberjur.api.helpers;

public interface ResponseCallback {
    void onSuccess(ResponseWrapper wrapper);
    void onFailure(ResponseWrapper wrapper);
}
