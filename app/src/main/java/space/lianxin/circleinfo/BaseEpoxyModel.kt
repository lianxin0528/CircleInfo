package space.lianxin.circleinfo

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.aimymusic.ambase.ui.holder.BaseEpoxyHolder
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.blankj.utilcode.util.SizeUtils
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.backgroundResource

/**
 *description: Model的基类.
 *@date 2019/5/18 17:12.
 *@author: YangYang.
 */
abstract class BaseEpoxyModel<T : BaseEpoxyHolder> :
  EpoxyModelWithHolder<T>() {

  @EpoxyAttribute
  var topMarginDp: Float? = null

  @EpoxyAttribute
  var leftMarginDp: Float? = null

  @EpoxyAttribute
  var rightMarginDp: Float? = null

  @EpoxyAttribute
  var bottomMarginDp: Float? = null

  @EpoxyAttribute
  var topPaddingDp: Float? = null

  @EpoxyAttribute
  var leftPaddingDp: Float? = null

  @EpoxyAttribute
  var rightPaddingDp: Float? = null

  @EpoxyAttribute
  var bottomPaddingDp: Float? = null

  @EpoxyAttribute
  var heightDp: Float? = null

  @EpoxyAttribute
  var widthDp: Float? = null

  @EpoxyAttribute
  @ColorInt
  var bgColor: Int? = null

  @EpoxyAttribute
  @ColorRes
  var bgColorRes: Int? = null

  @EpoxyAttribute
  var bgDrawable: Drawable? = null

  @EpoxyAttribute
  @DrawableRes
  var bgDrawableRes: Int? = null

  override fun bind(holder: T) {
    super.bind(holder)
    setSize(holder.view)
    setBgColor(holder.view)
    setBgDrawable(holder.view)
    setMargin(holder.view)
    setPadding(holder.view)
    onBind(holder)
    onBind(holder.view)
  }

  private fun setSize(view: View) {
    if (heightDp == null && widthDp == null) {
      return
    }
    val layoutParams = view.layoutParams
    heightDp?.let {
      layoutParams.height = SizeUtils.dp2px(it)
    }
    widthDp?.let {
      layoutParams.width = SizeUtils.dp2px(it)
    }
    view.layoutParams = layoutParams
  }

  private fun setBgColor(view: View) {
    bgColor?.let {
      view.backgroundColor = it
    }
    bgColorRes?.let {
      view.backgroundColorResource = it
    }
  }

  private fun setBgDrawable(view: View) {
    bgDrawable?.let {
      view.background = it
    }
    bgDrawableRes?.let {
      view.backgroundResource = it
    }
  }

  private fun setPadding(view: View) {
    if (leftPaddingDp == null
      && topPaddingDp == null
      && rightPaddingDp == null
      && bottomPaddingDp == null
    ) {
      return
    }
    view.setPadding(
      SizeUtils.dp2px(leftPaddingDp ?: 0f),
      SizeUtils.dp2px(topPaddingDp ?: 0f),
      SizeUtils.dp2px(rightPaddingDp ?: 0f),
      SizeUtils.dp2px(bottomPaddingDp ?: 0f)
    )
  }

  private fun setMargin(view: View) {
    if (leftMarginDp == null
      && topMarginDp == null
      && rightMarginDp == null
      && bottomMarginDp == null
    ) {
      return
    }
    if (view.layoutParams is ViewGroup.MarginLayoutParams) {
      val marginLayoutParams = (view.layoutParams as ViewGroup.MarginLayoutParams)
      val left = if (leftMarginDp == null) {
        marginLayoutParams.leftMargin
      } else {
        SizeUtils.dp2px(leftMarginDp!!)
      }
      val top = if (topMarginDp == null) {
        marginLayoutParams.topMargin
      } else {
        SizeUtils.dp2px(topMarginDp!!)
      }
      val right = if (rightMarginDp == null) {
        marginLayoutParams.rightMargin
      } else {
        SizeUtils.dp2px(rightMarginDp!!)
      }
      val bottom = if (bottomMarginDp == null) {
        marginLayoutParams.bottomMargin
      } else {
        SizeUtils.dp2px(bottomMarginDp!!)
      }
      marginLayoutParams.setMargins(
        left,
        top,
        right,
        bottom
      )
      view.layoutParams = marginLayoutParams
    }
  }

  override fun onFailedToRecycleView(holder: T): Boolean {
    return super.onFailedToRecycleView(holder)
  }

  /**
   * 需要使用自定义Holder时重写该方法
   */
  open fun onBind(holder: T) {}

  /**
   * 不需要使用自定义Holder是只是使用ItemView重写该方法
   * 使用anko的话简化步骤，参数view就是holder的ItemView
   */
  open fun onBind(itemView: View) {}
}