package craft

import iner.*
import tools.Tools
import kotlin.math.ceil


abstract class Filtration : Craft() {
    abstract val extraShay: Boolean
    override val typeId: Int = 20
    override val name: String = "滤纯工艺"
        get() = field + Tools.intToRoman(objId)
}

/** 滤纯工艺I */
class FiltrationI(override val level: Int) : Filtration() {
    override val extraShay = level == 3
    override val objId: Int = 1
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is GabeIner }?.count ?: 0
        val gabeShayRatio = when (level) {
            1 -> 0.5
            2 -> 0.8
            else -> 1.0
        }
        val gabeCount = ceil(rawCount * gabeShayRatio).toInt()
        val shayCount = rawCount - gabeCount +
                if (extraShay) gabeCount else 0 // extraShay = gabeCount * 1
        return material.filterNot { it is GabeIner }.toMutableSet().apply {
            add(GabeInerI(gabeCount))
            add(ShayIner.init(ShayIner.count + shayCount))
        }
    }

}

/** 滤纯工艺II */
class FiltrationII(override val level: Int) : Filtration() {
    override val extraShay = level == 3
    override val objId: Int = 2
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is GabeInerI }?.count ?: 0
        val gabeShayRatio = when (level) {
            1 -> 0.4
            2 -> 0.6
            else -> 0.8
        }
        val gabeCount = ceil(rawCount * gabeShayRatio).toInt()
        val shayCount = rawCount - gabeCount +
                if (extraShay) gabeCount * 2 else 0 // extraShay = gabeCount * 2
        return material.filterNot { it is GabeInerI }.toMutableSet().apply {
            add(GabeInerII(gabeCount))
            add(ShayIner.init(ShayIner.count + shayCount))
        }
    }
}

/** 滤纯工艺III */
class FiltrationIII(override val level: Int) : Filtration() {
    override val extraShay = level == 3
    override val objId: Int = 3
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is GabeInerII }?.count ?: 0
        val gabeShayRatio = when (level) {
            1 -> 0.3
            2 -> 0.5
            else -> 0.7
        }
        val gabeCount = ceil(rawCount * gabeShayRatio).toInt()
        val shayCount = rawCount - gabeCount +
                if (extraShay) gabeCount * 2 else 0 // extraShay = gabeCount * 2
        return material.filterNot { it is GabeInerII }.toMutableSet().apply {
            add(GabeInerIII(gabeCount))
            add(ShayIner.init(ShayIner.count + shayCount))
        }
    }
}