package pers.vaccae.draganddropdemo.adapter

import android.content.ClipData
import android.content.Intent
import android.view.DragEvent
import android.view.View
import androidx.core.view.DragStartHelper
import androidx.draganddrop.DropHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import pers.vaccae.draganddropdemo.R
import pers.vaccae.draganddropdemo.bean.CDrugs


/**
 * 作者：Vaccae
 * 邮箱：3657447@qq.com
 * 创建时间： 14:49
 * 功能模块说明：
 */
class DrugsAdapter(layoutId: Int, drugslist: MutableList<CDrugs>? = null) :
    BaseQuickAdapter<CDrugs, BaseViewHolder>(layoutId, drugslist) {

    var mDragEventListener = DragEventListener(this)

    override fun convert(holder: BaseViewHolder, item: CDrugs) {
        holder.setText(R.id.rcl_drugs_ckcode, item.drugs_ckcode.toString())
        holder.setText(R.id.rcl_drugs_code, item.drugs_code)
        holder.setText(R.id.rcl_drugs_name, item.drugs_name)
        holder.setText(R.id.rcl_drugs_specs, item.drugs_specs)
        holder.setText(R.id.rcl_qty, item.qty.toString())

        holder.itemView.setTag(holder.adapterPosition)
        holder.itemView.setOnDragListener(mDragEventListener)

        holder.itemView.setOnLongClickListener {
            //定义Intent
            val intent = Intent()
            intent.putExtra("pos", holder.adapterPosition)
            val dragdata = ClipData.newIntent("olditem", intent)
            val shadow: View.DragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(dragdata, shadow, null, 0)
        }
    }
}
