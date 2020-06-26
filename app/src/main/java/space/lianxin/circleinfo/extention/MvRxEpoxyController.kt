package space.lianxin.circleinfo.extention

import com.aimymusic.ambase.mvvm.MvRxViewModel
import com.aimymusic.ambase.ui.activity.BaseActivity
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MvRxState

/**
 * description:
 * @date: 2020/6/25 20:15
 * @author: lianxin
 */
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
  com.airbnb.mvrx.withState(viewModel) { state ->
    buildModels(state)
  }
}