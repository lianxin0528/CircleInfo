package space.lianxin.circleinfo.viewmodel

import android.util.Log
import com.aimymusic.ambase.mvvm.MvRxViewModel
import space.lianxin.circleinfo.model.CircleInfoBean
import space.lianxin.circleinfo.model.MessageBean

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

  /**
   * 首次加载或刷新
   */
  fun refresh() {
    setState {
      val circleinfoBeans: List<CircleInfoBean> = listOf(
        CircleInfoBean(1, "name1", IMG_URL_1, 0),
        CircleInfoBean(2, "name2", IMG_URL_1, 1),
        CircleInfoBean(3, "name3", IMG_URL_2, 2),
        CircleInfoBean(4, "name4", IMG_URL_2, 0),
        CircleInfoBean(5, "name5", IMG_URL_3, 1),
        CircleInfoBean(6, "name6", IMG_URL_3, 2))
      Log.d("qingyi", "CircleInfoViewModel::refresh: circleinfoBeans=$circleinfoBeans")
      val msg: List<MessageBean> = listOf(MessageBean(id = 1L, msg = "222", fromId = 1, toId = 2))
      val msgs: List<List<MessageBean>> = listOf(msg, msg, msg, msg, msg, msg)
      Log.d("qingyi", "CircleInfoViewModel::refresh: this.circleinfoBeans=${this.circleinfoBeans}")
      Log.d("qingyi", "CircleInfoViewModel::refresh: this.messages=${this.messages}")
      copy(circleinfoBeans = circleinfoBeans, messages = msgs)
    }
  }

  /**
   * 圈子信息改变
   */
  fun circleInfoBeanChange() {
  }

  /**
   * 添加一条圈子信息
   */
  fun addCircleInfo(circle: CircleInfoBean) {
    setState {
      copy(circleinfoBeans = circleinfoBeans + circle, messages = messages + emptyList())
    }
  }


}
