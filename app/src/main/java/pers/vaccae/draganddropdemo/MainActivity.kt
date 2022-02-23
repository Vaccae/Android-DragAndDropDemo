package pers.vaccae.draganddropdemo

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat
import androidx.core.view.DragStartHelper
import androidx.core.view.OnReceiveContentListener
import androidx.draganddrop.DropHelper
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pers.vaccae.draganddropdemo.adapter.DrugsAdapter
import pers.vaccae.draganddropdemo.adapter.DrugsAdapterNew
import pers.vaccae.draganddropdemo.bean.CDrugs

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler_view) }

    val drugslist = mutableListOf<CDrugs>()
    lateinit var adapter: DrugsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初始化数据
        initdata()

        adapter = DrugsAdapter(R.layout.rcl_item, drugslist)

        val gridLayoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            for(item in drugslist){
                Log.i("main", item.drugs_ckcode.toString() + " "+ item.drugs_code
                        +" "+item.drugs_name)
            }
        }

    }

    fun initdata() {
        var ckcode = 1000
        for (i in 1..9) {
            val item = CDrugs()
            if (i % 3 == 0) {
                item.drugs_ckcode = ckcode + 3
                ckcode += 1000
            } else {
                item.drugs_ckcode = ckcode + i % 3
            }
            item.drugs_code = "00000$i"
            item.drugs_name = "测试药品$i"
            item.drugs_specs = "50ml"
            item.qty = i

            drugslist.add(item)
        }
    }
}