package net.mbiztech.webtoapp;

public interface MainActivityModel {

    boolean isPermissionGranted();
    boolean isNetworkConnected();
    void sendImeiToDb(int userId, RequestCompleteListener<String> callback);

}
