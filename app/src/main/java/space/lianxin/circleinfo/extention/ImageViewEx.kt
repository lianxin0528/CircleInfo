package space.lianxin.circleinfo.extention

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView

/**
 * description: ImageView的扩展.
 * @date: 2020/6/24 14:31
 * @author: lianxin
 */
/**
 * 加载url图片,默认CenterCrop和CrossFade效果
 */
fun ImageView.load(url: String?, onSuccess: ((bitmap: Bitmap?) -> Unit)? = null) {
  url?.let {
    Log.d("lianxin", "ImageView::load: $url")
  }
}