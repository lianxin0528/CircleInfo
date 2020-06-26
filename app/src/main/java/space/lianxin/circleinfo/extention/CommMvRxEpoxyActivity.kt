package space.lianxin.circleinfo.extention

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxView

/**
 *description: MvRx和Epoxy都使用的Activity的基类.
 *@date 2019/5/13 10:32.
 *@author: YangYang.
 */
abstract class CommMvRxEpoxyActivity : CommMvRxActivity(), MvRxView {

  protected val epoxyController by lazy { epoxyController() }
  override fun onStart() {
    super.onStart()
    postInvalidate()
  }

  protected fun subscribeVM(vararg viewModels: BaseMvRxViewModel<*>) {
    viewModels.forEach {
      it.subscribe(owner = this, subscriber = {
        postInvalidate()
      })
    }
  }

  override fun invalidate() {
    epoxyController.requestModelBuild()
  }

  abstract fun epoxyController(): AsyncEpoxyController
}