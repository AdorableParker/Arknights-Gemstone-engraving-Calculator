package craft

import iner.Iner

/**
 * 工艺类
 */
abstract class Craft {
    /** 工艺等级 */
    abstract val level: Int
    abstract val name: String

    /** 工艺操作台占用 */
    open val craftTable: Boolean = true

    abstract val typeId: Int
    abstract val objId: Int

    /** 工艺方法 */
    open fun craft(material: Set<Iner>, outInfo: Boolean): MutableSet<Iner>? {
        return null
    }

    fun build(material: Set<Iner>, outInfo: Boolean): MutableSet<Iner> {
        if (outInfo) println(name)
        return buildCore(material)
    }

    /** 操作方法 */
    abstract fun buildCore(material: Set<Iner>): MutableSet<Iner>
}