package space.lianxin.circleinfo.viewmodel

import com.airbnb.mvrx.MvRxState
import space.lianxin.circleinfo.model.CircleInfoBean
import space.lianxin.circleinfo.model.MessageBean

/**
 * description:
 * @date: 2020/6/24 15:11
 * @author: lianxin
 */
data class CircleInfoState(
  /** 圈子信息列表 */
  val circleinfoBeans: List<CircleInfoBean> = emptyList(),
  /** 对应圈子列表的消息列表 */
  val messages: List<List<MessageBean>> = emptyList()
) : MvRxState