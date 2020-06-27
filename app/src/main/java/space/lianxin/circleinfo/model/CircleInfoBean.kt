package space.lianxin.circleinfo.model

/**
 * description: 圈子信息实体
 * @date: 2020/6/24 12:08
 * @author: lianxin
 */
data class CircleInfoBean(
  /** 圈子ID */
  val id: Int?,
  /** 圈子名称 */
  val name: String?,
  /** 圈子头像地址 */
  val iconUrl: String?,
  /** 在圈子中的身份标识：CIRCLE_MEMBERS(0)-成员，CIRCLE_ADMIN(1)-管理员，CIRCLE_OWN(2)-圈主 */
  val mark: Int?
) {
  companion object {
    /** 圈子成员 */
    const val CIRCLE_MARK_MEMBERS: Int = 0
    /** 圈子管理员 */
    const val CIRCLE_MARK_ADMIN: Int = 1
    /** 圈主 */
    const val CIRCLE_MARK_OWN: Int = 2
  }
}