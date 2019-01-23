package com.gnetop.ltgamewechat;

import android.content.Context;

import com.gnetop.ltgamecommon.impl.OnPlayResultedListener;
import com.gnetop.ltgamecommon.login.LoginBackManager;
import com.gnetop.ltgamecommon.model.AliPlayBean;
import com.gnetop.ltgamecommon.model.WeChatBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.WeakHashMap;

public class WeChatPlayManager {


    /**
     * 微信支付
     */
    public static void weChatPlay(Context context, String url, String appID,
                                   WeakHashMap<String, String> params) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        api.registerApp(appID);
        LoginBackManager.weChatPlay(url, params, new OnPlayResultedListener() {
            @Override
            public void onPlayError(Throwable ex) {

            }

            @Override
            public void onPlayComplete() {

            }

            @Override
            public void onAliPlayResult(AliPlayBean result) {

            }

            @Override
            public void onWeChatPlayResult(WeChatBean mBean) {
                PayReq req = new PayReq();
                req.appId = mBean.getAppid();
                req.partnerId = mBean.getPartnerid();
                req.prepayId = mBean.getPrepayid();
                req.nonceStr = mBean.getNoncestr();
                req.timeStamp = mBean.getTimestamp();
                req.packageValue = mBean.getPackageX();
                req.sign = mBean.getSign();
                req.extData = "app data"; // optional
                api.sendReq(req);
            }
        });


    }
}
