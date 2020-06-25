package space.lianxin.circleinfo.model

/**
 * description:
 * @date: 2020/6/24 12:18
 * @author: lianxin
 */
data class MessageBean(
  /** 消息id */
  val id: Long?,
  /** 消息实体 */
  val msg: String?,
  /** 消息发送者id */
  val fromId: Int?,
  /** 消息接收者id */
  val toId: Int?
)