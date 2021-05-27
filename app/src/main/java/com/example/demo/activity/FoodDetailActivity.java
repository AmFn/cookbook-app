package com.example.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.demo.R;
import com.example.demo.entity.CookBook;
import com.example.demo.entity.User;
import com.just.agentweb.core.AgentWeb;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailActivity extends AppCompatActivity {

        private Context context = this;

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
//
//    @BindView(R.id.iv_detail_image)
//    ImageView topBanner;
//
//    @BindView(R.id.tv_detail_content)
//    TextView content;
//
//    @BindView(R.id.rv_materials_list)
//    RecyclerView materialsRecyclerView;
//    MaterialsAdapter materialsAdapter;
//
//    @BindView(R.id.rv_process_list)
//    RecyclerView processRecyclerView;
//    ProcessAdapter processAdapter;
//
//    @BindView(R.id.my_multiple_status_view)
//    MultipleStatusView multipleStatusView;

    CookBook pageCookData;
    CookBook cookBook;
    int bid;
    int uid;
    AgentWeb mAgentWeb;
    private Handler handler = new Handler();

    class AndroidInterface {

        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }



        @JavascriptInterface
        public  void toUserDetail(){
            int uid = 1;
            Log.d("toUserDetail:","toUserDetail");
            Intent intent  = new Intent(context, UserHomeActivity.class);
            intent.putExtra("userId",uid);
            startActivity(intent);

        }

        @JavascriptInterface
        public int getID(){

            Log.d("uid:",""+bid);
            return bid;
        }

        @JavascriptInterface
        public int getUserID(){

            Log.d("uid:",""+uid);
            return bid;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail2);
        ButterKnife.bind(this);
        Intent intent =getIntent();
        cookBook= intent.getParcelableExtra("cookBook");
        bid=cookBook.getBookId();
        initTopBar(cookBook.getName());
        View view = findViewById(R.id.webView);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) view, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
//                .go("https://www.baidu.com");
                .go("http://app.chenfengkk.com/");

        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new FoodDetailActivity.AndroidInterface(mAgentWeb,this));
//        mAgentWeb.getJsAccessEntrace().quickCallJs("SetScanValue","barcode2d",barCode);
//        multipleStatusView.showLoading();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadPageData(cookBook);
//            }
//        }, 500);






    }

//    private void initMaterialsRecyclerView() {
//        materialsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            materialsAdapter = new MaterialsAdapter();
//            materialsRecyclerView.setAdapter(materialsAdapter);
//            if(pageCookData.getMaterials()!=null){
//                materialsAdapter.setData(pageCookData.getMaterials());
//            }
//
//
//    }
//
//    private void initProcessRecyclerView() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//
//        processRecyclerView.setLayoutManager(linearLayoutManager);
//        processRecyclerView.setNestedScrollingEnabled(false);
//        processAdapter =new ProcessAdapter();
//        processRecyclerView.setAdapter(processAdapter);
//        if(pageCookData.getProcesses()!=null){
//            processAdapter.setData(pageCookData.getProcesses());
//        }
//    }

//    private void loadPageData(CookBook cookBook) {
//        if(!isDestroy((Activity)context)){
//            Glide.with(this)
//                    .load(cookBook.getPic())
//
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            Log.v("glide", "Error loading image", e);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
//                    .into(topBanner);
//        }
//        content.setText("    "+cookBook.getContent());
//
//
//
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ConstantUtil.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        CookBookApi api = retrofit.create(CookBookApi.class);
//        Call<BaseResponse> repos = api.getFoodDetail(cookBook.getBookId());
//        repos.enqueue(new Callback<BaseResponse>() {
//            @Override
//            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
//                BaseResponse baseResponse = response.body();
//
//                if(baseResponse.getSuccess() ) {
//
//                    CookBook result =  baseResponse.getData().getFoodDetail();
//                    Log.e("cook_detail",result.getMaterials().toString());
//                    multipleStatusView.showContent();
//                    pageCookData = result;
//                    initMaterialsRecyclerView();
//                    initProcessRecyclerView();
////                    onFailure(null,null);
//                }
//
//
//            }
//
//
//
//            @Override
//            public void onFailure(Call<BaseResponse> call, Throwable t) {
//                Log.e("errMsg", String.valueOf(t.getCause()));
//
//                showError();
//
//
//            }
//        });
//
//
//
//    }
//
//    private void showError() {
//        multipleStatusView.showError();
//        multipleStatusView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reload();
//            }
//        });
//    }
//
//
//    void reload(){
//        multipleStatusView.showLoading();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadPageData(cookBook);
//            }
//        }, 1000);
//        multipleStatusView.setOnClickListener(null);
//
//    }

    private void initTopBar(String name) {
        QMUIStatusBarHelper.translucent(this);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.cook_app_yellow));
        mTopBar.setTitle(name).setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 判断Activity是否Destroy

     */
    public static boolean isDestroy(Activity mActivity) {
        if (mActivity== null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }
}