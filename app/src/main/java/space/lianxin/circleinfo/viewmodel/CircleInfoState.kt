package space.lianxin.circleinfo.viewmodel

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import space.lianxin.circleinfo.model.CircleInfoBean

/**
 * description: 圈子信息state
 * @date: 2020/6/24 15:11
 * @author: lianxin
 */
data class CircleInfoState(
  /** 圈子信息列表 */
  val circleInfoBeans: List<CircleInfoBean> = emptyList(),
  /** 请求状态 */
  val request: Async<List<CircleInfoBean>> = Uninitialized
) : MvRxState