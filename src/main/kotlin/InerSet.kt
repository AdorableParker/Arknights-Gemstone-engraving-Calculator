import craft.Craft
import iner.Iner
import iner.PetIner
import iner.PetInerIII
import iner.ShayIner

object InerSet {

    private var outInfo: Boolean = false

    /** 原料 */
    private val material: MutableSet<Iner> = mutableSetOf()

    /** 添加原料 */
    private fun setMaterial(vararg raw: Iner) {
        material.addAll(raw)
    }

    /** 工艺台 */
    private val craftArea: MutableList<Craft?> = mutableListOf()

    /** 设置工艺台 */
    private fun setCraftArea(vararg craft: Craft) {
        craftArea.addAll(craft)
    }

    /** 操作台 */
    private val craftConsole: Array<Craft?> = Array(6) { null }
    private var stationsAvailableNum: Int = 2

    /** 设置操作台 */
    private fun setCraftConsole(vararg crafts: Craft) {
        crafts.forEachIndexed { it, craft ->
            if (it <= 5) craftConsole[it] = craft
        }
    }

    /** 最终产品 */
    private val product: MutableSet<Iner>
        get() = processing()

    val outProductInfo by lazy { createNextFunction() }
    private fun createNextFunction(): (Set<Iner>) -> Unit {
//        val lastResult = material.associateBy({ it.name }, { it.count })

        return { newValue ->
            newValue.filter {
                it.count != 0
//                it.name !in lastResult.keys ||
//                        it.name in lastResult.keys && it.count != lastResult[it.name]
            }.forEach {
                println("${it.name}:${it.count} -> value: ${it.count * it.value}")
            }
            println("-----")
        }
    }


    /** 产品价值评定 */
    fun appraisal(info: Boolean = false): Int {
        outInfo = info
        var sumValue = 0
        var dopingFlag = false
        val inerList = product
        for (iner in inerList) {
            sumValue += iner.count * iner.value

            if (iner.emptyWorkbenchRewardValue != 0) {
                val i = iner.emptyWorkbenchRewardValue *
                        (stationsAvailableNum - craftConsole.filter { it != null }.size)
                sumValue += i
            }
            if (iner.dopingFlag) dopingFlag = true
        }

        if (!dopingFlag || inerList.size != 1) return sumValue

        return sumValue + 100 * (inerList.find { it is PetInerIII }?.count ?: 0)
    }

    fun resetState(
        materialArray: Array<Iner>,
        stationsAvailableNum: Int,
        craftAreaArray: Array<Craft>,
        craftConsoleArray: Array<Craft>,
    ) {
        // 清空旧值
        material.clear()
        craftArea.clear()
        craftConsole.fill(null)
        PetIner.extraPrints = 0
        ShayIner.resetState()
        // 设置新值
        this.stationsAvailableNum = stationsAvailableNum
        setMaterial(*materialArray)
        setCraftArea(*craftAreaArray)
        setCraftConsole(*craftConsoleArray)
    }

    /** 处理 */
    private fun processing(): MutableSet<Iner> {
        var cache = material
        if (outInfo) {
            println(craftArea.joinToString(", ", "[", "]") { it?.let { "${it.typeId + it.objId}" } ?: "" })
            println(craftConsole.joinToString(", ", "[", "]") { it?.let { "${it.typeId + it.objId}" } ?: "" })
        }

        for (craft in craftArea) {
            cache = craft?.craft(cache, outInfo)?.also { if (outInfo) outProductInfo(cache) } ?: cache
        }     // 检查工艺台
        for (craft in craftConsole) {
            craft?.let {
                cache = it.build(cache, outInfo)
                if (outInfo) outProductInfo(cache)
            }
        }  // 执行操作台
        return cache.filterNot { it.count == 0 }.toMutableSet()             // 处理空元素
    }
}