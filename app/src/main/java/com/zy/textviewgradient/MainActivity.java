package com.zy.textviewgradient;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * https://blog.csdn.net/u013278099/article/details/50881431
 */
public class MainActivity extends AppCompatActivity {
    private TextView tv_text_horizontal,tv_text_vertical;
    private ProgressBar pb1;
    private int progress;
    private SongTextView tv_songtext;
    private Timer timer=null;
    private TimerTask task=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text_horizontal= (TextView) findViewById(R.id.tv_text_horizontal);
        tv_text_vertical= (TextView) findViewById(R.id.tv_text_vertical);
        ViewTreeObserver vto = tv_text_horizontal.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int btWidth = tv_text_horizontal.getMeasuredWidth();
                int btHeight = tv_text_horizontal.getMeasuredHeight();
                Shader shader_horizontal= new LinearGradient(btWidth/4, 0, btWidth, 0, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
                tv_text_horizontal.getPaint().setShader(shader_horizontal);
                Shader shader_vertical=new LinearGradient(0, btHeight/4, 0, btHeight, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
                tv_text_vertical.getPaint().setShader(shader_vertical);
            }
        });

        pb1= (ProgressBar) findViewById(R.id.pb1);
          //设置滚动条可见
        setProgressBarIndeterminateVisibility(true);
        progress=pb1.getProgress();
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                progress+=10;
                if(progress>100){
                    progress=0;
                }
                handler.sendEmptyMessage(0);

            }
        };
        timer.schedule(task,1000,300);
    }
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        pb1.setProgress(progress);
    }
};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer=null;
        task=null;
        handler.removeCallbacksAndMessages(null);
    }
}
