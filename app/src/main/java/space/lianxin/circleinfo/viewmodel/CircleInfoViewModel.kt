package space.lianxin.circleinfo.viewmodel

import android.os.Handler
import android.os.Message
import android.util.Log
import com.aimymusic.ambase.mvvm.MvRxViewModel
import space.lianxin.circleinfo.model.CircleInfoBean
import space.lianxin.circleinfo.model.MessageBean
import java.util.*
import kotlin.math.round

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
    const val IMG_URL_1 = "http://qlogo4.store.qq.com/qzone/1693563015/1693563015/100?1543370680"

    /** 假定图片路径2 */
    const val IMG_URL_2 = "http://qlogo1.store.qq.com/qzone/1657708280/1657708280/100?1531368272"

    /** 假定图片路径3 */
    const val IMG_URL_3 = "https://qlogo1.store.qq.com/qzone/43450340/43450340/100?1462622532"
  }

  /** 首次加载或刷新 */
  fun refresh() {
    setState {
      val lastMsg: MessageBean? = MessageBean(152L, "我是一条消息", 12, "故乡的Sakura", 12, Date())
      val circleinfoBeans: MutableList<CircleInfoBean> = mutableListOf(
        CircleInfoBean(1, 1564L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name1", iconUrl = IMG_URL_1, mark = 0, lastMsg = null),
        CircleInfoBean(2, 4654L, isTopLevel = true, hasAtMe = false, newDynamicCount = 1, newMsgCount = 24, isDisturb = false, name = "name2", iconUrl = IMG_URL_1, mark = 1, lastMsg = lastMsg),
        CircleInfoBean(3, 1523L, isTopLevel = false, hasAtMe = true, newDynamicCount = 0, newMsgCount = 2, isDisturb = false, name = "name3", iconUrl = IMG_URL_2, mark = 2, lastMsg = lastMsg),
        CircleInfoBean(4, 8451L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name4", iconUrl = IMG_URL_2, mark = 0, lastMsg = null),
        CircleInfoBean(5, 4715L, isTopLevel = true, hasAtMe = true, newDynamicCount = 1, newMsgCount = 1, isDisturb = false, name = "name5", iconUrl = IMG_URL_3, mark = 1, lastMsg = lastMsg),
        CircleInfoBean(6, 8456L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = true, name = "name6", iconUrl = IMG_URL_3, mark = 2, lastMsg = null),
        CircleInfoBean(7, 4574L, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 0, isDisturb = false, name = "name7", iconUrl = IMG_URL_1, mark = 0, lastMsg = null)
      )
      sortCircleInfoList(circleinfoBeans)
      copy(circleInfoBeans = circleinfoBeans)
    }
  }

  /** 添加一条圈子信息 */
  fun addCircleInfo(circle: CircleInfoBean) {
    setState {
      copy(circleInfoBeans = sortCircleInfoList((circleInfoBeans + circle) as MutableList<CircleInfoBean>))
    }
  }

  /**
   * 需求：模拟点击列表item后，清除消息未读数、动态未读数和@未读标志
   * clear未读
   * @param circle 需要操作的圈子
   */
  fun clearNotRead(circle: CircleInfoBean) {
    setState {
      var infoBeans = circleInfoBeans
      // 移除旧数据
      val minus = infoBeans.minus(circle)
      // 插入新数据
      infoBeans = minus.plus(circle.copy(hasAtMe = false, newDynamicCount = 0, newMsgCount = 0))
      /**
       * 默认插入是末尾，所以需要排序
       * 或者在方法传入index,直接插入指定位置，避免排序
       * 在获取index到插入之前可能存在新消息到来
       */
      sortCircleInfoList(infoBeans as MutableList<CircleInfoBean>)
      copy(circleInfoBeans = infoBeans)
    }
  }

  /**
   * 需求：模拟置顶和取消置顶功能，置顶后，圈子位置在第一条。取消置顶后到未置顶的第一条。
   * 圈子置顶/取消置顶
   * @param circle 需要操作的圈子
   */
  fun topLevel(circle: CircleInfoBean) {
    // 修改置顶属性和最后一次操作时间
    setState {
      var infoBeans = circleInfoBeans
      // 移除旧数据
      val minus = infoBeans.minus(circle)
      // 插入新数据
      infoBeans = minus.plus(circle.copy(isTopLevel = !(circle.isTopLevel
        ?: false), lastExcuteTime = Date().time))
      // 排序
      sortCircleInfoList(infoBeans as MutableList<CircleInfoBean>)
      copy(circleInfoBeans = infoBeans)
    }
  }

  /**
   * 需求：模拟改变消息免打扰后消息列表对应的item出现或者消息免打扰标志
   * 圈子开启/关闭免打扰
   * @param circle 需要操作的圈子
   */
  fun disturb(circle: CircleInfoBean) {
    setState {

      /** 假定开启/关闭第一个item的免打扰 */
      val cib = circleInfoBeans[0]

      var infoBeans = circleInfoBeans

//      // 移除旧数据
//      val minus = infoBeans.minus(circle)
//      // 插入新数据
//      infoBeans = minus.plus(circle.copy(isDisturb = !(circle.isDisturb ?: false)))

      // 移除假定item数据
      val minus = infoBeans.minus(cib)
      // 插入假定item数据
      infoBeans = minus.plus(cib.copy(isDisturb = !(cib.isDisturb ?: false)))

      // 考虑同上：clearNotRead()
      sortCircleInfoList(infoBeans as MutableList<CircleInfoBean>)
      copy(circleInfoBeans = infoBeans)
    }
  }

  /**
   * 对圈子列表数据进行排序
   * @param list 需要排序的列表(一定是可变列表)
   */
  private fun sortCircleInfoList(list: MutableList<CircleInfoBean>): MutableList<CircleInfoBean> {
    // plan1：取负值降序排列
    list.sortWith(compareBy({ !(it.isTopLevel ?: false) }, { -(it.lastExcuteTime ?: 0L) }))
    // plan2：默认升序排列取反
//    list.sortWith(compareBy({ !(it.isTopLevel ?: false) }, { -(it.lastExcuteTime ?: 0L) }))
//    list.reverse()
    return list
  }

  /** 是否开启虚拟消息 */
  private var isOpenMsg = false

  /** 虚拟 message ID */
  private var msgID: Long = 9000L

  /** 虚拟消息发射 */
  private val handle: Handler = object : Handler() {
    override fun handleMessage(msg: Message) {
      when (msg.what) {
        1 -> {
          val toid = (1..7).random()
          Log.d("qingyi", "CircleInfoViewModel::handleMessage: messageToId = $toid")
          val msgBean = MessageBean(msgID++, "msg:$msgID", 22, "故乡的Sakura", toid, Date())
          setState {
            var infoBeans = circleInfoBeans
            // 旧数据
            var oldData: CircleInfoBean?
            var minus: List<CircleInfoBean>?
            // 选择接收的圈子
            for (bean in circleInfoBeans) {
              if (bean.id == msgBean.toId) {
                // 保存旧数据
                oldData = bean
                // 移除旧数据
                minus = circleInfoBeans.minus(bean)
                // 修改数据，插入新数据
                infoBeans = minus.plus(oldData.copy(newMsgCount = oldData.newMsgCount + 1, lastMsg = msgBean, lastExcuteTime = msgBean.time?.time))
              }
            }
            // 排序并更新
            sortCircleInfoList(infoBeans as MutableList<CircleInfoBean>)
            copy(circleInfoBeans = infoBeans)
          }
        }
      }
      sendEmptyMessageDelayed(1, 3000)
    }
  }

  /**
   * 需求：模拟动态消息到来时，显示动态数目加1的逻辑；模拟新消息到来时，该消息条目到置顶/非置顶的第一条去。
   * 开启/关闭消息
   */
  fun receiveMsg() {
    if (isOpenMsg) {
      handle.removeMessages(1)
    } else {
      handle.sendEmptyMessageDelayed(1, 3000)
    }
    isOpenMsg = !isOpenMsg
  }

}
