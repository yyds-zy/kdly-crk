package com.crk.kdly.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crk.kdly.R;

public class AlterDialog extends Dialog {

    private Context context;
    private OnOkClickListener onOkClickListener;

    EditText etInput;
    TextView tvCancel,tvOk;

    String num = "";

    public AlterDialog( Context context,String num,OnOkClickListener onOkClickListener) {
        super(context);
        this.context = context;
        this.num = num;
        this.onOkClickListener = onOkClickListener;

        setContentView(R.layout.dialog_alter);
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //处理dialog样式，宽度铺满
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = display.getWidth();// 设置dialog宽度为屏幕
//        lp.height = display.getHeight();
        dialogWindow.setAttributes(lp);
    }


    private void initView(){
        etInput = findViewById(R.id.et_input);
        tvCancel = findViewById(R.id.tv_cancel);
        tvOk= findViewById(R.id.tv_ok);

        etInput.setText(num);
        if (!TextUtils.isEmpty(num)){
            etInput.setSelection(num.length());
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = etInput.getText().toString().trim();
                if (TextUtils.isEmpty(num)){
                    Toast.makeText(context,"请输入修改内容",Toast.LENGTH_SHORT).show();
                    return;
                }
                onOkClickListener.onclick(num);
                dismiss();
            }
        });
    }

}
