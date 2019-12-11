package net.mbiztech.webtoapp.common;

public interface RequestCompleteListener<T> {
    void onRequestSuccess(T object);
    void onRequestFailed(String message);

}
