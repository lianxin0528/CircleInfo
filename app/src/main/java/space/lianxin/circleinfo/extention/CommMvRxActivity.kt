package space.lianxin.circleinfo.extention

import android.os.Bundle
import com.airbnb.mvrx.MvRxViewModelStore

/**
 *description: MvRx的Activity的基类.
 *@date 2019/5/13 10:44.
 *@author: YangYang.
 */
abstract class CommMvRxActivity : CommActivity() {
    override val mvrxViewModelStore by lazy { MvRxViewModelStore(viewModelStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewModelStore.restoreViewModels(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewModelStore.saveViewModels(outState)
    }
}