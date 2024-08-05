package iner

/** 火焰伊纳 */
class NesreIner(count: Int) : Iner(count) {
    override val name: String = "火焰伊纳"
    override val value: Int = 1
}

/** 火焰伊纳I */
class NesreInerI(count: Int) : Iner(count) {
    override val name: String = "火焰伊纳I"
    override val value: Int = 2
}

/** 火焰伊纳II */
class NesreInerII(count: Int) : Iner(count) {
    override val name: String = "火焰伊纳II"
    override val value: Int = 10
}

/** 火焰伊纳III */
class NesreInerIII(count: Int) : Iner(count) {
    override val name: String = "火焰伊纳III"
    override val value: Int = 35
}

/** 火焰伊纳IV */
class NesreInerIV(count: Int) : Iner(count) {
    override val name: String = "火焰伊纳IV"
    override var emptyWorkbenchReward: Int = 0

    constructor(count: Int, extra: Int) : this(count) {
        emptyWorkbenchReward = extra
    }

    override val value: Int = 85

}