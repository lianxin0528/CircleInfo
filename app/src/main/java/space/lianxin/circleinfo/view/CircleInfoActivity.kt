package space.lianxin.circleinfo.view

import android.os.Bundle
import android.os.PersistableBundle
import com.aimymusic.ambase.mvvm.MvRxViewModel
import com.aimymusic.ambase.ui.activity.BaseActivity
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewModelStore
import kotlinx.android.synthetic.main.activity_circle_info.*
import space.lianxin.circleinfo.R
import space.lianxin.circleinfo.viewmodel.CircleInfoState
import space.lianxin.circleinfo.viewmodel.CircleInfoViewModel


open class CircleInfoActivity : BaseActivity(), MvRxView {

  override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

  protected val epoxyController by lazy {
    simpleController(viewModel) {
    }
  }

  private val viewModel: CircleInfoViewModel by lazy {
    CircleInfoViewModel(4345)
  }

  override fun onStart() {
    postInvalidate()
    super.onStart()
  }

  protected fun subscribeVM(vararg viewModels: BaseMvRxViewModel<*>) {
    viewModels.forEach {
      it.subscribe(owner = this, subscriber = {
        postInvalidate()
      })
    }
  }

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
    super.onCreate(savedInstanceState, persistentState)
  }

  override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    mvrxViewModelStore.saveViewModels(outState)
    super.onSaveInstanceState(outState, outPersistentState)
  }

  override fun initData() {
    selectSubscribe()

  }

  override fun initView() {
    erlCircleInfoList.setController(CircleInfoController(viewModel))
  }

  override fun invalidate() {}

  /** 指定布局id */
  override fun layoutResId(): Int = R.layout.activity_circle_info

  /**
   * 观察属性变化，做出相应变化
   */
  private fun selectSubscribe() {
    // 圈子信息
    viewModel.selectSubscribe(this,
      prop1 = CircleInfoState::circleinfoBeans,
      uniqueOnly = true) {
      erlCircleInfoList.requestModelBuild()
    }
    // 消息
    viewModel.selectSubscribe(this,
      prop1 = CircleInfoState::messages,
      uniqueOnly = true) {

    }
  }

}

open class MvRxEpoxyController(
  val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {

  override fun buildModels() {
    buildModelsCallback()
  }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 * When models are built the current type of the viewmodel will be provided.
 */
fun <S : MvRxState, A : MvRxViewModel<S>> BaseActivity.simpleController(
  viewModel: A,
  buildModels: EpoxyController.(state: S) -> Unit
) = MvRxEpoxyController {
  //    if (isDestroyed) return@MvRxEpoxyController
  com.airbnb.mvrx.withState(viewModel) { state ->
    buildModels(state)
  }
}
