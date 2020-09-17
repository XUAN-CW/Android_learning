package com.example.lab05_counter2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
/**
 * 如果系统由于系统约束（而不是正常的应用程序行为）而破坏了Activity，
 * 那么尽管实际 Activity实例已经消失，但是系统还是会记住它已经存在，
 * 这样如果用户导航回到它，系统会创建一个新的实例的Activity使用一组保存的数据来描述Activity在被销毁时的状态。
 * 系统用于恢复以前状态的已保存数据称为“实例状态”，是存储在Bundle对象中的键值对的集合。
 *
 * 链接：https://www.jianshu.com/p/27181e2e32d2
 */
public class MainActivity extends AppCompatActivity {

    private int count = 0;//当前数字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnCount = findViewById(R.id.btnCount);//获取 id 为  btnCount 的 Button 控件
        final TextView tvCount = findViewById(R.id.tvTextView);//获取 id 为  tvTextView 的 TextView 控件
        //设置事件监听
        btnCount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tvCount.setText(Integer.toString(++count));//当前数字先加一再设置为 tvCount 显示的文本
            }
        });
    }

     private static final  String COUNT_VALUE = "count_value";//计数器的键
    //重写 onSaveInstanceState(Bundle outState) 方法，保存 Bundle
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(COUNT_VALUE, count);// 保存用户自定义的状态
        super.onSaveInstanceState(outState);// 调用父类交给系统处理，这样系统能保存视图层次结构状态
    }
    //重写 onRestoreInstanceState(Bundle savedInstanceState) 方法，根据 Bundle 恢复状态
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt(COUNT_VALUE);
        final TextView tvCount = findViewById(R.id.tvTextView);//获取 id 为  tvTextView 的 TextView 控件
        if (tvCount != null) {
            // 从已保存状态恢复成员的值
            tvCount.setText(Integer.toString(count));
        }
    }
}
