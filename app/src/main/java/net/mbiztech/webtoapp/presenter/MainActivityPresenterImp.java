package net.mbiztech.webtoapp.presenter;

import net.mbiztech.webtoapp.common.RequestCompleteListener;
import net.mbiztech.webtoapp.model.MainActivityModel;
import net.mbiztech.webtoapp.view.MainActivityView;

public class MainActivityPresenterImp implements MainActivityPresenter {

    private MainActivityView view;
    private MainActivityModel model;

    public MainActivityPresenterImp(MainActivityView view, MainActivityModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onReceiveUserId(int user_id) {

        if (user_id == 0){
            view.setMessage("This is simple app for communicate from a website.");
        }else if (!model.isNetworkConnected()){
            view.setMessage("not network");
            view.toggleRetryButtonVisibility(true);
        }else if (!model.isPermissionGranted()){
            view.askPhoneStatePermission();
        }else {

            view.handleProgressBarVisibility(true);
            view.toggleRetryButtonVisibility(false);
            view.setMessage("Please wait while sending information...");

            model.sendImeiToDb(user_id, new RequestCompleteListener<String>() {
                @Override
                public void onRequestSuccess(String message) {
                    view.onImeiSendSuccess(message);
                    view.handleProgressBarVisibility(false);

                }

                @Override
                public void onRequestFailed(String message) {

                    view.onImeiSendFailed(message);
                    view.handleProgressBarVisibility(false);
                    view.toggleRetryButtonVisibility(true);
                }
            });


        }
    }

    @Override
    public void detachView() {

        view = null;
    }
}
