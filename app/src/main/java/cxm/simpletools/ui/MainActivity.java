package cxm.simpletools.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.lidroid.xutils.util.LogUtils;

import cxm.simpletools.R;
import cxm.simpletools.view.CircleView;

public class MainActivity extends Activity {

    private CircleView cusView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.customTagPrefix = "cxm";
//        initData();

        cusView = (CircleView) findViewById(R.id.cusView);
//        handler.sendEmptyMessageDelayed(1, 1000);
//        new Thread(cusView).start();
    }
    
    int i = 0 ;
    
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            i++;
            cusView.setCurrentProgress(i);
            handler.sendEmptyMessageDelayed(1, 50);
        }
    };

    /*private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addHeader("apikey", "6d50cddfbe6c33056d943737f2e134fc");
        String address = "";
        try {
            address = URLEncoder.encode("朝阳", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.addBodyParameter("cityname", address);
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://apis.baidu.com/apistore/weatherservice/citylist", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                textview.setText("suc-"+responseInfo.result+" --- "+responseInfo.statusCode);
                LogUtils.v(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
//                textview.setText("fail"+s);
            }
        });
    }*/

}
