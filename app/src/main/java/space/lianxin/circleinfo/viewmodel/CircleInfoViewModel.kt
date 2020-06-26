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

  /**
   * 首次加载或刷新
   */
  fun refresh() {
    withState {
      setState {
        val circleinfoBeans: List<CircleInfoBean> = emptyList()
        circleinfoBeans + CircleInfoBean(1, "name1", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 0)
        circleinfoBeans + CircleInfoBean(2, "name2", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 1)
        circleinfoBeans + CircleInfoBean(3, "name3", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 2)
        circleinfoBeans + CircleInfoBean(4, "name4", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 0)
        circleinfoBeans + CircleInfoBean(5, "name5", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 1)
        circleinfoBeans + CircleInfoBean(6, "name6", "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532", 2)
        val msg: List<MessageBean> = listOf(MessageBean(id = 1L, msg = "222", fromId = 1, toId = 2))
        val msgs: List<List<MessageBean>> = listOf(msg, msg, msg, msg, msg, msg)
        copy(circleinfoBeans = circleinfoBeans, messages = msgs)
      }
      Log.d("qingyi", "CircleInfoViewModel::refresh: ${it.circleinfoBeans}")
      Log.d("qingyi", "CircleInfoViewModel::refresh: ${it.messages}")
    }
  }

  /**
   * 圈子信息改变
   */
  fun circleInfoBeanChange() {
  }


}
