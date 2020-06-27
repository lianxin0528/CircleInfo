package space.lianxin.circleinfo.model

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import kotlinx.android.synthetic.main.item_circle_info.view.*
import org.jetbrains.anko.backgroundColor
import space.lianxin.circleinfo.extention.BaseEpoxyHolder
import space.lianxin.circleinfo.extention.BaseEpoxyModel
import space.lianxin.circleinfo.R
import space.lianxin.circleinfo.extention.load


/**
 * description: 圈子信息item
 * @date: 2020/6/24 09:02
 * @author: lianxin
 */
@EpoxyModelClass(layout = R.layout.item_circle_info)
abstract class CircleInfoItem : BaseEpoxyModel<BaseEpoxyHolder>() {

  @EpoxyAttribute
  /** 圈子信息 */
  lateinit var circleInfoBean: CircleInfoBean

  @EpoxyAttribute
  /** 消息 */
  lateinit var msgBeans: List<MessageBean>

  @EpoxyAttribute
    /** 点击监听 */
  var click: ((v: View) -> Unit)? = null

  @EpoxyAttribute
    /** 长按监听 */
  var longClick: ((v: View) -> Boolean)? = null


  /**
   * 绑定视图
   */
  override fun onBind(itemView: View) {
    super.onBind(itemView)
    // 圈子头像
    itemView.ivCircleInfoIcon.load(circleInfoBean.iconUrl, null)
    // 圈子身份
    setCircleMark(itemView.tvCircleInfoMark, circleInfoBean.mark)
    // 显示最后一条消息
//    if (!this::msgBeans.isInitialized && msgBeans.isNotEmpty()) {
//      msgBeans.let {
//        itemView.tvCircleInfoMsg.text = it.last().msg
//      }
//    } else {
//      itemView.tvCircleInfoMsg.text = ""
//    }

    // 监听调用
    itemView.setOnClickListener(click)
    itemView.setOnLongClickListener(longClick)
  }

  /** 设置圈子的身份显示，管理员/圈主就显示，成员就隐藏 */
  private fun setCircleMark(textView: TextView, mark: Int?) {
    when (mark) {
      CircleInfoBean.CIRCLE_MARK_ADMIN -> {
        // 标识为管理员
        textView.backgroundColor = Color.rgb(0, 211, 144)
        textView.text = "管理员"
      }
      CircleInfoBean.CIRCLE_MARK_OWN -> {
        // 标识为圈主
        textView.backgroundColor = Color.rgb(255, 105, 41)
        textView.text = "圈主"
      }
      else -> {
        // 其他情况
        textView.visibility = View.GONE
      }
    }
  }

}