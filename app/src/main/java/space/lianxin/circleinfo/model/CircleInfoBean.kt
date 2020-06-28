package space.lianxin.circleinfo.model

/**
 * description: 圈子信息实体
 * @date: 2020/6/24 12:08
 * @author: lianxin
 */
data class CircleInfoBean(
  /** 圈子ID(主键) */
  val id: Int?,
  /** 上次操作时间 */
  val lastExcuteTime: Long?,
  /** 是否置顶圈子 */
  val isTopLevel: Boolean?,
  /** 是否有人@我 */
  val hasAtMe: Boolean?,
  /** 新增动态数 */
  val newDynamicCount: Int = 0,
  /** 新增消息数 */
  val newMsgCount: Int = 0,
  /** 是否开启免打扰 */
  val isDisturb: Boolean?,
  /** 圈子名称 */
  val name: String?,
  /** 圈子头像地址 */
  val iconUrl: String?,
  /** 在圈子中的身份标识：CIRCLE_MEMBERS(0)-成员，CIRCLE_ADMIN(1)-管理员，CIRCLE_OWN(2)-圈主 */
  val mark: Int?,
  /** 圈子最后一条消息 */
  val lastMsg: MessageBean?
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