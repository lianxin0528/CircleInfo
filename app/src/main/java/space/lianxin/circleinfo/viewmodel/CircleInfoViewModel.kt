package space.lianxin.circleinfo.viewmodel

import android.util.Log
import com.aimymusic.ambase.mvvm.MvRxViewModel
import space.lianxin.circleinfo.model.CircleInfoBean
import java.util.*

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

  init {
    // 打印 State 变化日志
    logStateChanges()
    Log.d("qingyi", "CircleInfoViewModel::init: ")
  }

  companion object {
    /** 假定图片路径1 */
    const val IMG_URL_2 = "http://qlogo4.store.qq.com/qzone/1693563015/1693563015/100?1543370680"

    /** 假定图片路径2 */
    const val IMG_URL_3 = "http://qlogo1.store.qq.com/qzone/1657708280/1657708280/100?1531368272"

    /** 假定图片路径3 */
    const val IMG_URL_1 = "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532"
  }

  /** 首次加载或刷新 */
  fun refresh() {
    setState {
      val circleinfoBeans: MutableList<CircleInfoBean> = mutableListOf(
        CircleInfoBean(1, 1564L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name1", iconUrl = IMG_URL_1, mark = 0, lastMsg = null),
        CircleInfoBean(2, 4654L, isTopLevel = true, hasAtMe = false, newDynamicCount = 1, newMsgCount = 0, isDisturb = false, name = "name2", iconUrl = IMG_URL_1, mark = 1, lastMsg = null),
        CircleInfoBean(3, 1523L, isTopLevel = false, hasAtMe = true, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name3", iconUrl = IMG_URL_2, mark = 2, lastMsg = null),
        CircleInfoBean(4, 8451L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name4", iconUrl = IMG_URL_2, mark = 0, lastMsg = null),
        CircleInfoBean(5, 4715L, isTopLevel = true, hasAtMe = true, newDynamicCount = 1, newMsgCount = 0, isDisturb = false, name = "name5", iconUrl = IMG_URL_3, mark = 1, lastMsg = null),
        CircleInfoBean(6, 8456L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = true, name = "name6", iconUrl = IMG_URL_3, mark = 2, lastMsg = null),
        CircleInfoBean(7, 4574L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name7", iconUrl = IMG_URL_1, mark = 0, lastMsg = null)
      )
      sortCircleInfoList(circleinfoBeans)
      Log.d("qingyi", "CircleInfoViewModel::refresh: circleinfoBeans=$circleinfoBeans")
      Log.d("qingyi", "CircleInfoViewModel::refresh: this.circleinfoBeans=${this.circleinfoBeans}")
      copy(circleinfoBeans = circleinfoBeans)
    }
  }

  /** 添加一条圈子信息 */
  fun addCircleInfo(circle: CircleInfoBean) {
    setState {
      copy(circleinfoBeans = sortCircleInfoList((circleinfoBeans + circle) as MutableList<CircleInfoBean>))
    }
  }

  /**
   * 对圈子列表数据进行排序
   * @param list 需要排序的列表(一定是可变列表)
   */
  private fun sortCircleInfoList(list: MutableList<CircleInfoBean>): MutableList<CircleInfoBean> {
    list.sortWith(compareBy({ it.isTopLevel }, { it.lastExcuteTime }))
    list.reverse()
    return list
  }

}
