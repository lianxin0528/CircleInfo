package space.lianxin.circleinfo.model

import java.util.*

/**
 * description: 消息实体
 * @date: 2020/6/24 12:18
 * @author: lianxin
 */
data class MessageBean(
  /** 消息id(主键) */
  val id: Long?,
  /** 消息实体 */
  val msg: String?,
  /** 消息发送者id */
  val fromId: Int?,
  /** 消息发送者名字 */
  val fromName: String?,
  /** 消息接收者id */
  val toId: Int?,
  /** 消息产生的时间 */
  val time: Date?
)