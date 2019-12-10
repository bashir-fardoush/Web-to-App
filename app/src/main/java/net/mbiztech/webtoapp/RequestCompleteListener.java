package net.mbiztech.webtoapp;

public interface RequestCompleteListener<T> {
    void onRequestSuccess(T object);
    void onRequestFailed(String message);

}
