package space.lianxin.circleinfo.view

import android.util.Log
import android.view.View
import android.widget.Toast
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.activity_circle_info.*
import kotlinx.android.synthetic.main.layout_title.*
import space.lianxin.circleinfo.R
import space.lianxin.circleinfo.extention.CommMvRxEpoxyActivity
import space.lianxin.circleinfo.extention.simpleController
import space.lianxin.circleinfo.model.CircleInfoBean
import space.lianxin.circleinfo.model.MessageBean
import space.lianxin.circleinfo.model.circleInfoItem
import space.lianxin.circleinfo.viewmodel.CircleInfoState
import space.lianxin.circleinfo.viewmodel.CircleInfoViewModel
import java.util.*

/**
 * 圈子消息列表视图
 */
open class CircleInfoActivity : CommMvRxEpoxyActivity(), View.OnClickListener {
  /** 指定布局id */
  override fun layoutResId(): Int = R.layout.activity_circle_info

  /** viewModel */
  private val viewModel: CircleInfoViewModel by lazy { CircleInfoViewModel(4345) }

  /** 初始化视图 */
  override fun initView() {
    // 监听
    btn1.setOnClickListener(this)
    btn2.setOnClickListener(this)
    btn3.setOnClickListener(this)
    btn4.setOnClickListener(this)
    btn5.setOnClickListener(this)

    // 设置刷新监听
    srlCircleInfoRefresh.setOnRefreshListener {
      viewModel.refresh() // 刷新
      srlCircleInfoRefresh.isRefreshing = false // 取消刷新条
    }
    erlCircleInfoList.setController(epoxyController)
  }

  /** 初始化数据 */
  override fun initData() {
    selectSubscribe() // 观察数据
    viewModel.refresh() // 刷新或者首次加载
  }

  /** 加载Controller */
  override fun epoxyController() = simpleController(viewModel) {
    Log.d("qingyi", "CircleInfoActivity::epoxyController: simpleController")
    withState(viewModel) { state ->
      if (state.circleInfoBeans.isNotEmpty()) {
        for (i in state.circleInfoBeans.indices) {
          circleInfoItem {
            id(state.circleInfoBeans[i].id) // id标识
            circleInfoBean(state.circleInfoBeans[i]) // 圈子信息
            // 单击item条目
            click {
              // 清除未读
              viewModel.clearNotRead(state.circleInfoBeans[i])
            }
            // 长按item条目
            longClick {
              // 长按 置顶/取消置顶
              viewModel.topLevel(state.circleInfoBeans[i])
              true
            }
          }
        }
      }
    }
  }

  /**
   * 观察属性变化，做出相应变化
   */
  private fun selectSubscribe() {
    // 圈子信息
    viewModel.selectSubscribe(
      this,
      prop1 = CircleInfoState::circleInfoBeans,
      uniqueOnly = true
    ) {
      Log.d("qingyi", "CircleInfoActivity::selectSubscribe: circleinfoBeans=${it}")
//      erlCircleInfoList.requestModelBuild()
      postInvalidate()
    }
  }

  /** 虚拟圈子id */
  private var circleId = 10

  /** 点击事件 */
  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn1 -> { // 第1个按钮
        Toast.makeText(this, "新增一个圈子信息", Toast.LENGTH_SHORT).show()
        val lastMsg: MessageBean? = MessageBean(152L, "我是一条消息$circleId", 12, "故乡的Sakura", 12, Date())
        viewModel.addCircleInfo(CircleInfoBean(circleId++, Date().time, isTopLevel = false, hasAtMe = false, newDynamicCount = 0, newMsgCount = 1, isDisturb = true, name = "newName", iconUrl = CircleInfoViewModel.IMG_URL_1, mark = 0, lastMsg = lastMsg))
      }
      R.id.btn2 -> { // 第2个按钮
        Toast.makeText(this, "开启/关闭消息", Toast.LENGTH_SHORT).show()
        viewModel.receiveMsg()
      }
      R.id.btn3 -> { // 第3个按钮
        Toast.makeText(this, "开启/关闭topItem免打扰", Toast.LENGTH_SHORT).show()
        viewModel.disturb(CircleInfoBean(null, null, null, null, 0, 0, null, null, null, null, null))
      }
      R.id.btn4 -> { // 第4个按钮
      }
      R.id.btn5 -> { // 第5个按钮
      }
    }
  }

  override fun invalidate() {
    Log.d("qingyi", "CircleInfoActivity::invalidate: ")
    super.invalidate()
  }

}