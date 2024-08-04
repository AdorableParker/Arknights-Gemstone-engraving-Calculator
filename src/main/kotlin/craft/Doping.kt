package org.example.craft

import org.example.iner.*
import org.example.tools.Tools
import kotlin.math.ceil

abstract class Doping : Craft() {
    override val typeId: Int = 30
    override val name: String = "交糅工艺"
        get() = field + Tools.intToRoman(objId)

    fun routine(pet: Int, shay: Int): Pair<Int, Int> {
        val remainingShay = shay - pet
        return if (remainingShay >= 0) Pair(pet, remainingShay) else Pair(shay, 0)
    }

    fun equalization(pet: Int, shay: Int): Pair<Int, Int> = ceil((pet + shay) * 0.5).toInt() to 0

}

/** 交糅工艺I */
class DopingI(override val level: Int) : Doping() {
    override val objId: Int = 1
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetIner }?.count ?: 0
        val shayCount = material.find { it is ShayIner }?.count ?: 0

        val (pet, shay) = if (level == 1) routine(petCount, shayCount) else equalization(petCount, shayCount)
        val inerSet = material.filterNot { it is PetIner }.toMutableSet()
        ShayIner.init(shay)
        val extra = if (level == 3) 5 else 0
        inerSet.add(PetInerI(pet, extra))
        return inerSet
    }
}

/** 交糅工艺II */
class DopingII(override val level: Int) : Doping() {
    override val objId: Int = 2
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetInerI }?.count ?: 0
        val gabeCount = material.find { it is GabeInerI }?.count ?: 0

        val (pet, gabe) = if (level == 3) equalization(petCount, gabeCount) else routine(petCount, gabeCount)
        val inerSet = material.filterNot { it is PetInerI || it is GabeInerI }.toMutableSet()
        inerSet.add(GabeInerI(gabe))
        val extra = if (level >= 2) 15 else 0
        inerSet.add(PetInerII(pet, extra))
        return inerSet
    }
}

/** 交糅工艺III */
class DopingIII(override val level: Int) : Doping() {
    override val objId: Int = 3
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetInerII }?.count ?: 0
        val nesreCount = material.find { it is NesreInerIII }?.count ?: 0

        val (pet, nesre) = if (level == 1) routine(petCount, nesreCount) else equalization(petCount, nesreCount)
        val inerSet = material.filterNot { it is PetInerII || it is NesreInerIII }.toMutableSet()
        inerSet.add(NesreInerIII(nesre))
        inerSet.add(PetInerIII(pet, level == 3))
        return inerSet
    }
}