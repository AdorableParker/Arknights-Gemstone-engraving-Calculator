package iner

/**
 * 宝石类
 */
abstract class Iner(
    /** 宝石数量 */
    open var count: Int,
) {
    /** 宝石名称 */
    abstract val name: String

    /** 宝石单价 */
    abstract val value: Int

    /** 空闲工作台价值 */
    open val emptyWorkbenchRewardValue: Int = 0

    /** 额外积分 */
    private var extraPrints: Int = 0
    /** 交鞣奖励分数 */
    open val dopingFlag: Boolean = false
    fun getExtraPrints(): Int = extraPrints
    open fun setExtraPrints(value: Int) {
        extraPrints += value
    }
}