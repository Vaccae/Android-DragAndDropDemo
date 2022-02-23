package pers.vaccae.draganddropdemo.adapter

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.view.DragEvent
import android.view.View
import androidx.core.view.ContentInfoCompat
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
class DrugsAdapterNew(layoutId: Int, drugslist: MutableList<CDrugs>? = null) :
    BaseQuickAdapter<CDrugs, BaseViewHolder>(layoutId, drugslist) {

    override fun convert(holder: BaseViewHolder, item: CDrugs) {
        holder.setText(R.id.rcl_drugs_ckcode, item.drugs_ckcode.toString())
        holder.setText(R.id.rcl_drugs_code, item.drugs_code)
        holder.setText(R.id.rcl_drugs_name, item.drugs_name)
        holder.setText(R.id.rcl_drugs_specs, item.drugs_specs)
        holder.setText(R.id.rcl_qty, item.qty.toString())

        holder.itemView.setTag(holder.adapterPosition)

        //设置拖拽效果
        DragStartHelper(
            holder.itemView
        ) { v, helper ->
            //定义Intent
            val intent = Intent()
            intent.putExtra("pos", holder.adapterPosition)
            val dragdata = ClipData.newIntent("olditem", intent)
            val shadow: View.DragShadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(dragdata, shadow, null, 0)
        }.attach()

        //拖拽放下效果
        DropHelper.configureView(
            this.context as Activity, holder.itemView,
            arrayOf(ClipDescription.MIMETYPE_TEXT_INTENT, "olditem")
        ) { view, payload ->
            val intent = payload.clip.getItemAt(0).intent
            val oldpos = intent.getIntExtra("pos", -1)
            //记录现在格的数据

            val nowpos = view?.getTag() as Int
            //将现在格数据存到临时变量中
            val nowitem = CDrugs()
            nowitem.drugs_ckcode = data[nowpos].drugs_ckcode
            nowitem.drugs_code = data[nowpos].drugs_code
            nowitem.drugs_name = data[nowpos].drugs_name
            nowitem.drugs_specs = data[nowpos].drugs_specs
            nowitem.qty = data[nowpos].qty

            //修改现在格的数据
            data[nowpos].drugs_ckcode = nowitem.drugs_ckcode;
            data[nowpos].drugs_code = data[oldpos].drugs_code
            data[nowpos].drugs_name = data[oldpos].drugs_name
            data[nowpos].drugs_specs = data[oldpos].drugs_specs
            data[nowpos].qty = data[oldpos].qty

            //修改原来的格数据
            data[oldpos].drugs_code = nowitem.drugs_code
            data[oldpos].drugs_name = nowitem.drugs_name
            data[oldpos].drugs_specs = nowitem.drugs_specs
            data[oldpos].qty = nowitem.qty

            notifyItemChanged(nowpos)
            notifyItemChanged(oldpos)

            return@configureView ContentInfoCompat.Builder(
                payload.clip,
                ContentInfoCompat.SOURCE_APP
            ).build()
        }
    }
}
