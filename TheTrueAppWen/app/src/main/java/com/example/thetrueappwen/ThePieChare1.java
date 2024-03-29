package com.example.thetrueappwen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class ThePieChare1 extends AppCompatActivity {
    private PieChart pieChart;
    private Context context;
    private Intent intent2;
    private String username;
    private int shipin=0,yiwu=0,chuxing=0,qita=0;
    private DBOpenMessage dbOpenMessage;
    private TextView shipintxt,yiwutxt,chuxingtxt,qitatxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piechare1);
        //关于数据库操作
        context=this;
        intent2=getIntent();
        username=intent2.getStringExtra("username");//经检验已经成功
        dbOpenMessage=new DBOpenMessage(ThePieChare1.this,"db_wen2",null,1);
        getMessage1(username);
        shipintxt=(TextView)findViewById(R.id.wentext11) ;
        yiwutxt=(TextView)findViewById(R.id.wentext12);
        chuxingtxt=(TextView)findViewById(R.id.wentext13);
        qitatxt=(TextView)findViewById(R.id.wentext14);
        shipintxt.setText(Integer.toString(shipin));
        yiwutxt.setText(Integer.toString(yiwu));
        chuxingtxt.setText(Integer.toString(chuxing));
        qitatxt.setText(Integer.toString(qita));


        //关于图标操作
        pieChart = (PieChart) findViewById(R.id.wenpie1);
        pieChart.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
        pieChart.setDescription("所有金额支出情况");//设置描述
        pieChart.setDescriptionTextSize(20);//设置描述字体大小

        pieChart.setExtraOffsets(5, 5, 5, 5);//设置饼状图距离上下左右的偏移量

        pieChart.setDrawCenterText(true);//是否绘制中间的文字
        pieChart.setCenterTextColor(Color.RED);//中间的文字颜色
        pieChart.setCenterTextSize(15);//中间的文字字体大小

        pieChart.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
        pieChart.setHoleColor(Color.WHITE);//饼状图中间的圆的绘制颜色
        pieChart.setHoleRadius(40f);//饼状图中间的圆的半径大小

        pieChart.setTransparentCircleColor(Color.BLACK);//设置圆环的颜色
        pieChart.setTransparentCircleAlpha(100);//设置圆环的透明度[0,255]
        pieChart.setTransparentCircleRadius(40f);//设置圆环的半径值

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);//设置饼状图是否可以旋转(默认为true)
        pieChart.setRotationAngle(10);//设置饼状图旋转的角度

        pieChart.setHighlightPerTapEnabled(true);//设置旋转的时候点中的tab是否高亮(默认为true)

        //右边小方框部分
        Legend l = pieChart.getLegend(); //设置比例图
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);//设置每个tab的显示位置（这个位置是指下图右边小方框部分的位置 ）
//        l.setForm(Legend.LegendForm.LINE);  //设置比例图的形状，默认是方形
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);//设置tab之间Y轴方向上的空白间距值
        l.setYOffset(0f);

        //饼状图上字体的设置
        // entry label styling
        pieChart.setDrawEntryLabels(true);//设置是否绘制Label
        pieChart.setEntryLabelColor(Color.RED);//设置绘制Label的颜色
        pieChart.setEntryLabelTextSize(20f);//设置绘制Label的字体大小

//        pieChart.setOnChartValueSelectedListener(this);//设值点击时候的回调
        pieChart.animateY(3400, Easing.EasingOption.EaseInQuad);//设置Y轴上的绘制动画
        ArrayList<PieEntry> pieEntries = new ArrayList<PieEntry>();

        pieEntries.add(new PieEntry(shipin, "食品"));
        pieEntries.add(new PieEntry(yiwu, "衣物"));
        pieEntries.add(new PieEntry(chuxing, "出行"));
        pieEntries.add(new PieEntry(qita, "其他"));


        String centerText = "总支出\n¥" + (shipin+yiwu+chuxing+qita);
        pieChart.setCenterText(centerText);//设置圆环中间的文字
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        pieDataSet.setColors(colors);

        pieDataSet.setSliceSpace(0f);//设置选中的Tab离两边的距离
        pieDataSet.setSelectionShift(5f);//设置选中的tab的多出来的
        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);

        //各个饼状图所占比例数字的设置
        pieData.setValueFormatter(new PercentFormatter());//设置%
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.YELLOW);

        pieChart.setData(pieData);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
    private void getMessage1(String username) {
        Cursor cursor=dbOpenMessage.getAllCostData(username);
        if(cursor!=null){
            while(cursor.moveToNext()){
                Message message2=new Message();
                message2.userkind=cursor.getString(cursor.getColumnIndex("userkind"));
                message2.usermoney=cursor.getString(cursor.getColumnIndex("usermoney"));
                message2.userchoice=cursor.getString(cursor.getColumnIndex("userchoice"));
                if(message2.userchoice.equals("支出"))
                {
                    if(message2.userkind.equals("食品"))
                    {
                        shipin+=Integer.parseInt(message2.usermoney);
                    }
                    else if(message2.userkind.equals("衣物"))
                    {
                        yiwu+=Integer.parseInt(message2.usermoney);
                    }
                    else if(message2.userkind.equals("出行"))
                    {
                        chuxing+=Integer.parseInt(message2.usermoney);
                    }
                    else if(message2.userkind.equals("其他"))
                    {
                        qita+=Integer.parseInt(message2.usermoney);
                    }
                }

            }
        }
    }
}