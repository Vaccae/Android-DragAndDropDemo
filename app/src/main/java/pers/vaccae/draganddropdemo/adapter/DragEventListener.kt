package pers.vaccae.draganddropdemo.adapter

import android.view.DragEvent
import android.view.View
import pers.vaccae.draganddropdemo.bean.CDrugs

/**
 * 作者：Vaccae
 * 邮箱：3657447@qq.com
 * 创建时间： 09:25
 * 功能模块说明：
 */
class DragEventListener(adapter: DrugsAdapter) : View.OnDragListener {

    var mAdapter = adapter

    override fun onDrag(p0: View?, p1: DragEvent?): Boolean {
        p1?.let {
            when (it.action) {
                DragEvent.ACTION_DROP -> {
                    val intent = it.clipData.getItemAt(0).intent
                    val oldpos = intent.getIntExtra("pos", -1)
                    //记录现在格的数据
                    val nowpos = p0?.getTag() as Int
                    //将现在格数据存到临时变量中
                    val nowitem = CDrugs()
                    nowitem.drugs_ckcode = mAdapter.data[nowpos].drugs_ckcode
                    nowitem.drugs_code = mAdapter.data[nowpos].drugs_code
                    nowitem.drugs_name = mAdapter.data[nowpos].drugs_name
                    nowitem.drugs_specs = mAdapter.data[nowpos].drugs_specs
                    nowitem.qty = mAdapter.data[nowpos].qty

                    //修改现在格的数据
                    mAdapter.data[nowpos].drugs_ckcode = nowitem.drugs_ckcode;
                    mAdapter.data[nowpos].drugs_code = mAdapter.data[oldpos].drugs_code
                    mAdapter.data[nowpos].drugs_name = mAdapter.data[oldpos].drugs_name
                    mAdapter.data[nowpos].drugs_specs = mAdapter.data[oldpos].drugs_specs
                    mAdapter.data[nowpos].qty = mAdapter.data[oldpos].qty

                    //修改原来的格数据
                    mAdapter.data[oldpos].drugs_code = nowitem.drugs_code
                    mAdapter.data[oldpos].drugs_name = nowitem.drugs_name
                    mAdapter.data[oldpos].drugs_specs = nowitem.drugs_specs
                    mAdapter.data[oldpos].qty = nowitem.qty

                    mAdapter.notifyItemChanged(nowpos)
                    mAdapter.notifyItemChanged(oldpos)
                }
            }
        }
        return true
    }
}