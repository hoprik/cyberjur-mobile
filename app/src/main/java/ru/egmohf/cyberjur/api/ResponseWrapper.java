package ru.egmohf.cyberjur.api;

import com.google.gson.Gson;
import okhttp3.Response;

public class ResponseWrapper {
    public final boolean isError;
    public final String errorMessage;

    public final String answer;
    public final Response response;

    public ResponseWrapper(boolean isError, String errorMessage){
        this.isError = isError;
        this.errorMessage = errorMessage;
        this.answer = "";
        this.response = null;
    }

    public ResponseWrapper(String answer, Response response){
        this.answer = answer;
        this.isError = false;
        this.errorMessage = "";
        this.response = response;
    }

    public boolean isError(){
        return isError;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getAnswer(){
        return answer;
    }

    public Response getResponse() {
        return response;
    }

    public <T> T getParsedResponse(Class<T> responseType){
        Gson gson = new Gson();
        return gson.fromJson(this.answer, responseType);
    }
}
