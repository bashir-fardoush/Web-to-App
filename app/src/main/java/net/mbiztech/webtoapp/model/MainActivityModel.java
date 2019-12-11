package net.mbiztech.webtoapp.model;

import net.mbiztech.webtoapp.common.RequestCompleteListener;

public interface MainActivityModel {

    boolean isPermissionGranted();
    boolean isNetworkConnected();
    void sendImeiToDb(int userId, RequestCompleteListener<String> callback);

}
