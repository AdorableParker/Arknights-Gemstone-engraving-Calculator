import craft.*

fun main() {

    val solver = Solver(
        30, 10, 20, 40, 5,  // 火 叶 空 沙 四项原料 以及 操作台数量
        // 输入拥有工艺
        QuenchingI(3), QuenchingII(3), QuenchingIII(2),// QuenchingIV(3),    // 淬雕
        //FiltrationI(1), FiltrationII(1),
        FiltrationIII(2),                      // 滤纯
        DopingI(1),// DopingII(3),
        DopingIII(1),                                  // 交糅
        //DepositionI(3), DepositionII(1), //DepositionIII(3)                       // 落晶
    )
    solver.run(ArrayList()) // 计算结果

    solver.outputTheOptimalProcess() // 输出最优结果


    val (materialArray, craftAreaArray, craftConsoleArray) = solver.optimalProcess() // 获取最优结果数据
    println(craftAreaArray.joinToString(",", "工艺区: [", "]") { "${it.name}-${it.level}" })
    println(craftConsoleArray.joinToString(",", "操作台: [", "]") { "${it.name}-${it.level}" })

    // 最优结果流程
    InerSet.resetState(materialArray, craftAreaArray, craftConsoleArray)
    val totalValue = InerSet.appraisal(true)
    val nowScore = 4428
    println("当前积分：$nowScore,预期结果分数:${nowScore + totalValue},预期增加分数：$totalValue")

}



