package com.yuki.TODO

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.also {
            //it.setTitleTextColor(getColor(R.color.white))
            setSupportActionBar(it)
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.contentFrame, MainFragment.newInstance())
        }.commit()


//        val chart = findViewById<com.github.mikephil.charting.charts.BarChart>(R.id.bar_chart)
//        //表示データ取得
//        chart.data = BarData(getBarData())
//
//        //Y軸(左)の設定
//        chart.axisLeft.apply {
//            axisMinimum = 0f
//            axisMaximum = 100f
//            labelCount = 5
//            setDrawTopYLabelEntry(true)
//            setValueFormatter { value, axis -> "" + value.toInt() }
//        }
//
//        //Y軸(右)の設定
//        chart.axisRight.apply {
//            setDrawLabels(false)
//            setDrawGridLines(false)
//            setDrawZeroLine(false)
//            setDrawTopYLabelEntry(true)
//        }
//
//        //X軸の設定
//        val labels = arrayOf("", "国語", "数学", "英語") //最初の””は原点の値
//        chart.xAxis.apply {
//            valueFormatter = IndexAxisValueFormatter(labels)
//            labelCount = 3//表示させるラベル数
//            position = XAxis.XAxisPosition.BOTTOM
//            setDrawLabels(true)
//            setDrawGridLines(false)
//            setDrawAxisLine(true)
//        }
//
//        //グラフ上の表示
//        chart.apply {
//            setDrawValueAboveBar(true)
//            description.isEnabled = false
//            isClickable = false
//            legend.isEnabled = false //凡例
//            setScaleEnabled(false)
//            animateY(1200,Easing.EasingOption.EaseInBack) //したからにょきっとあがってくる
//        }
//
//    }
//
//    private fun getBarData(): ArrayList<IBarDataSet> {
//        //表示させるデータ
//        val entries = ArrayList<BarEntry>().apply {
//            add(BarEntry(1f, 60f))
//            add(BarEntry(2f, 80f))
//            add(BarEntry(3f, 70f))
//        }
//
//        val dataSet = BarDataSet(entries, "bar").apply {
//            //整数で表示
//            valueFormatter = IValueFormatter { value, _, _, _ -> "" + value.toInt() }
//            //ハイライトさせない
//            isHighlightEnabled = false
//            //Barの色をセット
//            setColors(intArrayOf(R.color.material_blue, R.color.material_green, R.color.material_yellow), this@MainActivity)
//        }
//
//        val bars = ArrayList<IBarDataSet>()
//        bars.add(dataSet)
//        return bars
    }
}