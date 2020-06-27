package space.lianxin.circleinfo.view

import android.util.Log
import android.view.View
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_circle_info.*
import kotlinx.android.synthetic.main.layout_title.*
import space.lianxin.circleinfo.R
import space.lianxin.circleinfo.extention.CommMvRxEpoxyActivity
import space.lianxin.circleinfo.extention.simpleController
import space.lianxin.circleinfo.model.CircleInfoBean
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
    withState(viewModel) { state ->
      if (state.circleinfoBeans.isNotEmpty()) {
        for (i in state.circleinfoBeans.indices) {
          circleInfoItem {
            id(state.circleinfoBeans[i].id) // id标识
            circleInfoBean(state.circleinfoBeans[i]) // 圈子信息
//            msgBeans(state.messages[i])
//            if (state.messages.isNotEmpty()) {
//              msgBeans(state.messages[i])
//            }
            // 单击item条目
            click { Log.d("qingyi", "CircleInfoActivity::epoxyController: click") }
            // 长按item条目
            longClick {
              Log.d("qingyi", "CircleInfoActivity::epoxyController: longClick")
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
      prop1 = CircleInfoState::circleinfoBeans,
      uniqueOnly = true
    ) {
      Log.d("qingyi", "CircleInfoActivity::selectSubscribe: circleinfoBeans=${it}")
      erlCircleInfoList.requestModelBuild()
//      postInvalidate()
    }
    // 圈子消息
    viewModel.selectSubscribe(
      this,
      prop1 = CircleInfoState::messages,
      uniqueOnly = true
    ) {
      Log.d("qingyi", "CircleInfoActivity::selectSubscribe: messages=$it")
      erlCircleInfoList.requestModelBuild()
    }
  }

  /** 点击事件 */
  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.btn1 -> { // 第1个按钮
        viewModel.addCircleInfo(CircleInfoBean(7, "华胥引", CircleInfoViewModel.IMG_URL_1, 0))
      }
      R.id.btn2 -> { // 第2个按钮
      }
      R.id.btn3 -> { // 第3个按钮
      }
      R.id.btn4 -> { // 第4个按钮
      }
      R.id.btn5 -> { // 第5个按钮
      }
    }
  }

  override fun invalidate() {
    super.invalidate()
    Log.d("qingyi", "CircleInfoActivity::invalidate: ")
//    erlCircleInfoList.requestModelBuild()
  }

}