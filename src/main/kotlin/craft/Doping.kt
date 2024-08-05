package craft

import iner.*
import tools.Tools
import kotlin.math.ceil

abstract class Doping : Craft() {
    override val typeId: Int = 30
    override val name: String = "交糅工艺"
        get() = field + Tools.intToRoman(objId)

    /** 常规
     * case 1. pet > auxiliary => pet^: auxiliary pet: pet-auxiliary auxiliary: 0
     * case 2. pet < auxiliary => pet^: pet pet: 0 auxiliary: auxiliary-pet
     * case 3. pet = auxiliary => pet^: pet pet: 0 auxiliary: 0
     * */
    fun routine(pet: Int, auxiliary: Int): Triple<Int, Int, Int> = when {
        pet > auxiliary -> Triple(auxiliary, pet - auxiliary, 0)
        pet < auxiliary -> Triple(pet, auxiliary - pet, 0)
        else -> Triple(auxiliary, 0, 0)
    }

    /** 等分 */
    fun equalization(pet: Int, shay: Int): Triple<Int, Int, Int> = Triple(ceil((pet + shay) * 0.5).toInt(), 0, 0)

}

/** 交糅工艺I */
class DopingI(override val level: Int) : Doping() {
    override val objId: Int = 1
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetIner }?.count ?: 0
        val shayCount = material.find { it is ShayIner }?.count ?: 0

        val (product, pet, shay) = if (level != 1 && petCount != 0 && shayCount != 0)
            equalization(petCount, shayCount)
        else
            routine(petCount, shayCount)
        val inerSet = material.filterNot { it is PetIner }.toMutableSet()
        val extra = if (level == 3) 5 else 0
        // 更新产物
        ShayIner.init(shay)
        inerSet.add(PetIner(pet))
        inerSet.add(PetInerI(product, extra))
        return inerSet
    }
}

/** 交糅工艺II */
class DopingII(override val level: Int) : Doping() {
    override val objId: Int = 2
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetInerI }?.count ?: 0
        val gabeCount = material.find { it is GabeInerI }?.count ?: 0

        val (product, pet, gabe) = if (level == 3 && petCount != 0 && gabeCount != 0)
            equalization(petCount, gabeCount)
        else
            routine(petCount, gabeCount)
        val inerSet = material.filterNot { it is PetInerI || it is GabeInerI }.toMutableSet()
        val extra = if (level >= 2) 15 else 0
        // 更新产物
        inerSet.add(GabeInerI(gabe))
        inerSet.add(PetInerI(pet))
        inerSet.add(PetInerII(product, extra))
        return inerSet
    }
}

/** 交糅工艺III */
class DopingIII(override val level: Int) : Doping() {
    override val objId: Int = 3
    override fun buildCore(material: Set<Iner>): MutableSet<Iner> {
        val petCount = material.find { it is PetInerII }?.count ?: 0
        val nesreCount = material.find { it is NesreInerIII }?.count ?: 0

        val (product, pet, nesre) = if (level != 1 && petCount != 0 && nesreCount != 0)
            equalization(petCount, nesreCount)
        else
            routine(petCount, nesreCount)
        val inerSet = material.filterNot { it is PetInerII || it is NesreInerIII }.toMutableSet()

        // 更新产物
        inerSet.add(NesreInerIII(nesre))
        inerSet.add(PetInerII(pet))
        inerSet.add(PetInerIII(product, level == 3))

        return inerSet
    }
}