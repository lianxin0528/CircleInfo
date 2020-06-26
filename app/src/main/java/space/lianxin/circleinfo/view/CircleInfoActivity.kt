package space.lianxin.circleinfo.view

import android.util.Log
import android.view.View
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.activity_circle_info.*
import kotlinx.android.synthetic.main.layout_title.*
import space.lianxin.circleinfo.R
import space.lianxin.circleinfo.extention.CommMvRxEpoxyActivity
import space.lianxin.circleinfo.extention.simpleController
import space.lianxin.circleinfo.model.circleInfoItem
import space.lianxin.circleinfo.viewmodel.CircleInfoState
import space.lianxin.circleinfo.viewmodel.CircleInfoViewModel

/**
 * 圈子消息列表视图
 */
open class CircleInfoActivity : CommMvRxEpoxyActivity(), View.OnClickListener {
  /** 指定布局id */
  override fun layoutResId(): Int = R.layout.activity_circle_info

  /** viewModel */
  private val viewModel: CircleInfoViewModel by lazy { CircleInfoViewModel(4345) }
//  private val viewModel: CircleInfoViewModel by lazy { CircleInfoViewModel() }
//  private val viewModel by activityViewModel(CircleInfoViewModel::class)
//  private val viewModel by activityViewModel(CircleInfoViewModel::class)
//  override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

  /** 初始化视图 */
  override fun initView() {
//    erlCircleInfoList.setController(epoxyController)

    btn1.setOnClickListener(this)
    btn2.setOnClickListener(this)
    btn3.setOnClickListener(this)
    btn4.setOnClickListener(this)
    btn5.setOnClickListener(this)

    // 设置刷新监听
    srlCircleInfoRefresh.setOnRefreshListener {
      //      viewModel.refresh()
      // 取消刷新UI
      srlCircleInfoRefresh.isRefreshing = false
    }
    erlCircleInfoList.setController(epoxyController)
  }

  /** 初始化数据 */
  override fun initData() {
    // 观察数据
    selectSubscribe()
    // 刷新或者首次加载
//    viewModel.refresh()
  }

  /** 加载Controller */
  override fun epoxyController() = simpleController(viewModel) {
    withState(viewModel) { state ->
      if (state.circleinfoBeans.isNotEmpty()) {
        for (i in state.circleinfoBeans.indices) {
          circleInfoItem {
            id(state.circleinfoBeans[i].id)
            circleInfoBean(state.circleinfoBeans[i])
            if (state.messages.isNotEmpty()) {
              state.messages[i]?.let {
                msgBeans(it)
              }
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
      prop1 = CircleInfoState::circleinfoBeans,
      uniqueOnly = true
    ) {
      Log.d("qingyi", "CircleInfoActivity::selectSubscribe: circleinfoBeans")
    }
    // 圈子消息
    viewModel.selectSubscribe(
      this,
      prop1 = CircleInfoState::messages,
      uniqueOnly = true
    ) {
      Log.d("qingyi", "CircleInfoActivity::selectSubscribe: messages")
    }
  }

  /** 点击事件 */
  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn1 -> {
      }
      R.id.btn2 -> {
      }
      R.id.btn3 -> {
      }
      R.id.btn4 -> {
      }
      R.id.btn5 -> {
      }
    }
  }

}