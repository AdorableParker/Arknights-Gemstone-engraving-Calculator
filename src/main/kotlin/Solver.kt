import iner.*
import craft.Craft

class Solver(
    private var nesre: Int,
    private var gabe: Int,
    private var pet: Int,
    private var shay: Int,
    private var processStepsMax: Int,
    vararg crafts: Craft,
) {
    private var crafts: List<Craft> = crafts.sortedBy { it.typeId + it.objId }

    /** 重设求解器参数 */
    fun reSet(
        nesre: Int = this.nesre,
        gabe: Int = this.gabe,
        pet: Int = this.pet,
        shay: Int = this.shay,
        processStepsMax: Int = this.processStepsMax,
        vararg crafts: Craft,
    ) {
        this.nesre = nesre
        this.gabe = gabe
        this.pet = pet
        this.shay = shay
        this.processStepsMax = processStepsMax
        this.crafts = crafts.sortedBy { it.typeId + it.objId }
    }

    private var console: Array<Craft> = arrayOf()
    private var value: Int = 0
    private val processAvailabilityStatus = Array(crafts.size) { true }
    private var progressValue = 0
    private val progressMaxValue = setProgressMaxValue()

    // 初始化分数
    init {
        InerSet.resetState(
            arrayOf(NesreIner(nesre), GabeIner(gabe), PetIner(pet), ShayIner.init(shay)),
            this.crafts.toTypedArray(),
            arrayOf()
        )
        value = InerSet.appraisal(progressValue >= progressMaxValue)
        println(value)
    }

    private fun setProgressMaxValue(): Int {
        val (n, m) = if (crafts.size <= processStepsMax) crafts.size to crafts.size else crafts.size to processStepsMax
        return (0..m).reduce { sum, k ->
            sum + (n downTo n - k + 1).fold(1) { acc, i -> acc * i }
        } + 1
    }

    fun run(list: ArrayList<Craft>) {
        if (list.size >= processStepsMax) {
            return
        }

        println("进度: $progressValue / $progressMaxValue")
        crafts.forEachIndexed { index, n ->

            if (processAvailabilityStatus[index]) {
                processAvailabilityStatus[index] = false
                progressValue++
                list.add(n)
                val craftArea = crafts.filter { it !in list }

                InerSet.resetState(
                    arrayOf(NesreIner(nesre), GabeIner(gabe), PetIner(pet), ShayIner.init(shay)),
                    craftArea.toTypedArray(),
                    list.toTypedArray()
                )
//                if (progressValue >= progressMaxValue) {
//                    println(craftArea.joinToString(",", "[", "]") { "${it.name}${it.level}" })
//                    println(list.joinToString(",", "[", "]") { "${it.name}${it.level}" })
//                    println(console.joinToString(",", "[", "]") { "${it.name}${it.level}" })
//                }

                val totalValue = InerSet.appraisal(progressValue >= progressMaxValue)
//                println(list.joinToString(",", "[", "]") { "${it.name}-${it.level}" })
//                println(totalValue)
                if (totalValue > value || totalValue == value && list.size < console.size) {
                    console = list.toTypedArray()
                    println("$totalValue > $value 更新最优解")
                    value = totalValue
                    outputTheOptimalProcess()
                } // 记录最优解

                run(list)
                list.removeAt(list.size - 1)
                processAvailabilityStatus[index] = true
            }
        }

    }

    fun outputTheOptimalProcess() {
        println(crafts.filter { it !in console }.toTypedArray().joinToString(",", "[", "]") { "${it.name}${it.level}" })
        println(console.joinToString(",", "[", "]") { "${it.name}${it.level}" })
        println("预期分数：${value}")
        println("======")
    }

    fun optimalProcess(): Triple<Array<Iner>, Array<Craft>, Array<Craft>> {
        return Triple(
            arrayOf(NesreIner(nesre), GabeIner(gabe), PetIner(pet), ShayIner.init(shay)),
            crafts.filter { it !in console }.toTypedArray(),
            console
        )
    }
}