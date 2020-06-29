package space.lianxin.circleinfo.model

import android.graphics.Color
import android.util.Log
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
import java.text.SimpleDateFormat


/**
 * description: 圈子信息item
 * @date: 2020/6/24 09:02
 * @author: lianxin
 */
@EpoxyModelClass(layout = R.layout.item_circle_info)
abstract class CircleInfoItem : BaseEpoxyModel<BaseEpoxyHolder>() {

  /** 圈子信息 */
  @EpoxyAttribute
  lateinit var circleInfoBean: CircleInfoBean

  /** 点击监听 */
  @EpoxyAttribute
  var click: ((v: View) -> Unit)? = null

  /** 长按监听 */
  @EpoxyAttribute
  var longClick: ((v: View) -> Boolean)? = null

  /** 绑定视图 */
  override fun onBind(itemView: View) {
    super.onBind(itemView)

    Log.d("qingyi", "CircleInfoItem::onBind: ")

    // 是否置顶圈子,根据置顶修改item背景色
    if (circleInfoBean.isTopLevel == true) {
      itemView.setBackgroundColor(Color.rgb(0, 255, 255))
    } else {
      itemView.setBackgroundColor(Color.rgb(255, 255, 255))
    }

    // 有人@我
    if (circleInfoBean.hasAtMe == true) {
      itemView.tvCircleInfoAtMe.visibility = View.VISIBLE
    } else {
      itemView.tvCircleInfoAtMe.visibility = View.GONE
    }

    // 新增动态数
    if (circleInfoBean.newDynamicCount > 0) {
      itemView.tvCircleInfoDynamic.text = "[动态+${circleInfoBean.newDynamicCount}]"
      itemView.tvCircleInfoDynamic.visibility = View.VISIBLE
    } else {
      itemView.tvCircleInfoDynamic.visibility = View.GONE
    }

    // 新增消息数
    if (circleInfoBean.isDisturb == true) { // 开启了免打扰
      // 隐藏具体消息数，并显示免打扰
      itemView.tvCircleInfoMsgCount.visibility = View.GONE
      itemView.ivCircleInfoDisturb.visibility = View.VISIBLE
      // 判断是否有新消息
      if (circleInfoBean.newMsgCount > 0) {
        itemView.tvCircleInfoDisturbMsg.visibility = View.VISIBLE
      } else {
        itemView.tvCircleInfoDisturbMsg.visibility = View.GONE
      }
    } else { // 未开启免打扰
      itemView.tvCircleInfoDisturbMsg.visibility = View.GONE
      itemView.ivCircleInfoDisturb.visibility = View.GONE
      if (circleInfoBean.newMsgCount > 0) {
        itemView.tvCircleInfoMsgCount.text = circleInfoBean.newMsgCount.toString()
        itemView.tvCircleInfoMsgCount.visibility = View.VISIBLE
      } else {
        itemView.tvCircleInfoMsgCount.visibility = View.GONE
      }
    }

    // 圈子中最后一条消息
    if (circleInfoBean.lastMsg == null) {
      itemView.tvCircleInfoTime.visibility = View.GONE
      itemView.tvCircleInfoMsg.visibility = View.GONE
    } else {
      itemView.tvCircleInfoTime.visibility = View.VISIBLE
      itemView.tvCircleInfoMsg.visibility = View.VISIBLE
      itemView.tvCircleInfoMsg.text = "${circleInfoBean.lastMsg!!.fromName}:${circleInfoBean.lastMsg!!.msg}"
      circleInfoBean.lastMsg!!.time?.let { date ->
        itemView.tvCircleInfoTime.text = format.format(date)
      }
    }

    // 圈子头像
    itemView.ivCircleInfoIcon.load(circleInfoBean.iconUrl, null)
    // 圈子身份
    setCircleMark(itemView.tvCircleInfoMark, circleInfoBean.mark)
    // 圈子名称
    itemView.tvCircleInfoName.text = circleInfoBean.name

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

  /** 时间格式化 */
  private val format: SimpleDateFormat = SimpleDateFormat("HH:mm")
}