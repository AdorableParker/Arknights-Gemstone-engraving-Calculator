package org.example.craft

import org.example.iner.*
import org.example.tools.Tools
import kotlin.math.ceil

abstract class Quenching : Craft() {
    override val typeId: Int = 10
    override val name: String = "淬雕工艺"
        get() = field + Tools.intToRoman(objId)

    override fun craft(material: Set<Iner>, outInfo: Boolean): MutableSet<Iner>? {
        if (craftTable) return null
        if (outInfo) println("通过工艺区进行淬雕")
        return build(material, outInfo)
    }
}

/** 淬雕工艺I */
class QuenchingI(override val level: Int) : Quenching() {
    override var craftTable = (level == 1)
    override val objId: Int = 1
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is NesreIner }?.count ?: 0
        val count = if (level == 3) rawCount * 2 else rawCount
        val inerSet = material.filterNot { it is NesreIner }.toMutableSet()
        inerSet.add(NesreInerI(count))
        return inerSet
    }
}

/** 淬雕工艺II */
class QuenchingII(override val level: Int) : Quenching() {
    override var craftTable = (level != 3)
    override val objId: Int = 2
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is NesreInerI }?.count ?: 0
        val count = if (level >= 2) rawCount * 2 else rawCount
        val inerSet = material.filterNot { it is NesreInerI }.toMutableSet()
        inerSet.add(NesreInerII(count))
        return inerSet
    }
}

/** 淬雕工艺III */
class QuenchingIII(override val level: Int) : Quenching() {
    override var craftTable = (level != 3)
    override val objId: Int = 3
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is NesreInerII }?.count ?: 0
        val count = if (level >= 2) ceil(rawCount * 2.4).toInt() else rawCount
        val inerSet = material.filterNot { it is NesreInerII }.toMutableSet()
        inerSet.add(NesreInerIII(count))
        return inerSet
    }
}

/** 淬雕工艺IV */
class QuenchingIV(override val level: Int) : Quenching() {
    override val objId: Int = 4
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val rawCount = material.find { it is NesreInerIII }?.count ?: 0
        val inerSet = material.filterNot { it is NesreInerIII }.toMutableSet()
        val value = if (level == 3) 5000 else if (level == 2) 1500 else 0
        inerSet.add(NesreInerIV(rawCount, value))
        return inerSet
    }
}

