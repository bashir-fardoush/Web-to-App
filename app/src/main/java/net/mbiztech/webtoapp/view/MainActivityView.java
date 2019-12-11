package net.mbiztech.webtoapp.view;

public interface MainActivityView {
    void handleProgressBarVisibility(boolean visible);

    void setMessage(String message);
    void onImeiSendSuccess(String message);
    void onImeiSendFailed(String message);
    void toggleRetryButtonVisibility(boolean visible);
    void askPhoneStatePermission();


}
