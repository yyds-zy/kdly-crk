package com.crk.kdly.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.crk.kdly.R;
import com.crk.kdly.base.BaseFragment;
import com.crk.kdly.dialog.AlterDialog;
import com.crk.kdly.dialog.OnOkClickListener;
import com.crk.kdly.http.Ck_Scan_Details;
import com.crk.kdly.http.Comfim_Result;
import com.crk.kdly.net_connection.NetUtils;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.TLog;
import com.crk.kdly.tool.Utils;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.fragment
 * @date on 2020/8/27 11:25
 * @wechat 18813158027
 */
public class Scan_Out_Ck_Fragment extends BaseFragment implements Server_Response.OnGetSeverLister {


    @BindView(R.id.guige)
    TextView guige;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.bm)
    TextView bm;
    @BindView(R.id.et_number_show)
    TextView etNumberShow;
    @BindView(R.id.dw)
    TextView dw;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_comfirm)
    Button btnComfirm;
    Unbinder unbinder;
    private static Scan_Out_Ck_Fragment fragment;
    @BindView(R.id.scan_et)
    EditText scanEt;
    Unbinder unbinder1;
    private String type;
    private String chorc;
    private String cinvCode;
    private String result;
    private String textString;

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    public int loadWindowLayout() {
        return R.layout.scan_out_fragment;
    }

    public static Fragment newInstance(String type, String chorc) {
        fragment = new Scan_Out_Ck_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("spec_number", type);
        bundle.putString("chorc", chorc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews(View rootView) {

        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("spec_number");
            chorc = bundle.getString("chorc");
        }

        scanEt.addTextChangedListener(watcher);

        runnable = new Runnable() {
            @Override
            public void run() {
                scanEt.requestFocus();
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {//扫描结果

            String scan_result = scanEt.getText().toString();




         /*   try {
                result = new String(scan_result.getBytes("ISO-8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/

            /*if (result.contains("?")){
             result=result.replace("?","");
            }*/
            if (!scan_result.equals("")) {
                scan_Detaisl(scan_result);

            }


        }
    };



    private void scan_Detaisl(String message) {//获取信息详情
        TLog.e("扫描结果", message);
        showProgress();
        NetUtils.scanGetDetails(this, message, chorc);
    }

    /** 出库子标签 - 确认按钮 */
    private void comfim() {
        showProgress();
        NetUtils.comfiom_ck(this, type, cinvCode, etNumberShow.getText().toString(),getUsername());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_clear, R.id.btn_comfirm, R.id.et_number_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                guige.setText("");
                name.setText("");
                bm.setText("");
                scanEt.setText("");
                scanEt.requestFocus();
                etNumberShow.setText("");
                etNumberShow.clearFocus();
                dw.setText("");

                break;
            case R.id.btn_comfirm://确认按钮
                comfim();
                break;
            case R.id.et_number_show://修改数量
                String num = etNumberShow.getText().toString().trim();
                AlterDialog dialog = new AlterDialog(getContext(), num, new OnOkClickListener() {
                    @Override
                    public void onclick(String text) {
                        etNumberShow.setText(text);
                    }
                });
                dialog.show();
                break;
        }
    }


    @Override
    public void onSuccess(Object response) {
        scanEt.setText("");
        hideProgress();
        if (response instanceof Ck_Scan_Details) {
            btnComfirm.setEnabled(true);
            Ck_Scan_Details reslut = (Ck_Scan_Details) response;
            if (reslut.getTorf().equals("t")) {
                guige.setText(reslut.getCinvStd());
                name.setText(reslut.getCinvName());
                cinvCode = reslut.getCinvCode();
                bm.setText(cinvCode);
                etNumberShow.setText(reslut.getQty());
                dw.setText(reslut.getUnitName());
            } else {
                btnComfirm.setEnabled(false);
                tostShow(reslut.getTorf());
            }
        } else if (response instanceof Comfim_Result) {
            Comfim_Result comfim_result = (Comfim_Result) response;
            if (comfim_result.getTorf().equals("t")) {
                Utils.showDialogVersion3(getContext(), comfim_result.getList());
            } else {
                Utils.showDialogVersion2(getContext(), comfim_result.getTorf());
            }
        }
    }

    @Override
    public void onFaire(String error) {
        scanEt.setText("");
        hideProgress();
        btnComfirm.setEnabled(false);
        tostShow(error);

    }


}
