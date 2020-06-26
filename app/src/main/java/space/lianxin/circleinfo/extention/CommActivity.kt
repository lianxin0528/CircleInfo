package space.lianxin.circleinfo.extention

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.inputmethod.InputMethodManager
import com.aimymusic.ambase.extention.setStatusBarBlackText
import com.aimymusic.ambase.ui.activity.BaseActivity
import com.blankj.utilcode.util.ActivityUtils
import io.reactivex.disposables.Disposable

/**
 *description: 项目中通用的Activity基类，会增加一些统计相关的代码.
 *@date 2019/3/27 16:09.
 *@author: YangYang.
 */
abstract class CommActivity : BaseActivity() {

  private var configDisposable: Disposable? = null

  fun isLoadingShowing(): Boolean {
    return (supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_LOADING) != null
      || supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_LOADING_ACTION) != null)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (window != null) {
      window.navigationBarColor = Color.WHITE
    }
    savedInstanceState?.let {
      ActivityUtils.getActivityList().clear()
      finish()
    }
  }

  /**
   * 收起键盘
   */
  fun hideInputMethod() {
    currentFocus?.let {
      val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onPause() {
    super.onPause()
    hideInputMethod()
  }

  override fun onDestroy() {
    configDisposable?.dispose()
    super.onDestroy()
  }

  override fun initStatus() {
    setStatusBarBlackText()
  }

  companion object {
    private const val TAG_FRAGMENT_LOADING = "LoadingDialog"
    private const val TAG_FRAGMENT_LOADING_ACTION = "ActionLoadingDialog"
  }

  /**
   * 统计用到的页面Id
   */
  open fun pageId(): String {
    return ""
  }

  /**
   * fragment和activity通信
   *
   * @param message
   */
  open fun onHandlerFragmentMessage(message: Message) {
  }
}