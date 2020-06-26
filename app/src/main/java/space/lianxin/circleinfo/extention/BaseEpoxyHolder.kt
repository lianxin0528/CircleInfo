package space.lianxin.circleinfo.extention

import android.view.View
import com.airbnb.epoxy.EpoxyHolder

open class BaseEpoxyHolder : EpoxyHolder() {
  lateinit var itemView: View
  override fun bindView(itemView: View) {
    this.itemView = itemView
  }
}