package craft

import iner.Iner
import iner.ShayIner
import tools.Tools


abstract class Deposition : Craft() {
    override val typeId: Int = 40
    override val name: String = "落晶工艺"
        get() = field + Tools.intToRoman(objId)
}

/** 落晶工艺I */
class DepositionI(override val level: Int) : Deposition() {
    override val objId: Int = 1
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is ShayIner }?.count ?: 0
        val count = when (level) {
            1 -> rawCount * 2
            2 -> rawCount * 3
            else -> rawCount * 5
        }
        val inerSet = material.filterNot { it is ShayIner }.toMutableSet()
        inerSet.add(ShayIner.init(count))
        return inerSet
    }
}

/** 落晶工艺II */
class DepositionII(override val level: Int) : Deposition() {
    override val objId: Int = 2
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is ShayIner }?.count ?: 0
        val count = when (level) {
            1 -> rawCount * 3
            2 -> rawCount * 5
            else -> rawCount * 8
        }
        val inerSet = material.filterNot { it is ShayIner }.toMutableSet()
        inerSet.add(ShayIner.init(count))
        return inerSet
    }
}

/** 落晶工艺III */
class DepositionIII(override val level: Int) : Deposition() {
    override val objId: Int = 3
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is ShayIner }?.count ?: 0
        val count = if (level == 1) rawCount * 5 else rawCount * 9
        val inerSet = material.filterNot { it is ShayIner }.toMutableSet()
        inerSet.add(ShayIner.init(count, level == 3))
        return inerSet
    }
}