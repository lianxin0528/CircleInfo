package space.lianxin.circleinfo.viewmodel

import com.aimymusic.ambase.mvvm.MvRxViewModel

/**
 * description:
 * @date: 2020/6/24 15:18
 * @author: lianxin
 */
class CircleInfoViewModel(
  /** 用户id */
  val userId: Int,
  /** MvRxState */
  initState: CircleInfoState = CircleInfoState()
) : MvRxViewModel<CircleInfoState>(initState) {

  /**
   * 首次加载或刷新
   */
  fun refresh() {

  }

  /**
   * 圈子信息改变
   */
  fun circleinfoBeanChange() {

  }


}
