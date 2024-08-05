package iner

/** 天空伊纳 */
open class PetIner(count: Int) : Iner(count) {
    override val name: String = "天空伊纳"

    companion object {
        var extraPrints: Int = 0
    }

    override val value: Int = 1
        get() = field + extraPrints


    override fun setExtraPrints(value: Int) {
        extraPrints += value
    }
}

/** 天空伊纳I */
class PetInerI(count: Int) : PetIner(count) {
    override val name: String = "天空伊纳I"

    constructor(count: Int, extra: Int) : this(count) {
        setExtraPrints(extra)
    }

    override val value: Int = 3
        get() = field + extraPrints
}

/** 天空伊纳II */
class PetInerII(count: Int) : PetIner(count) {
    override val name: String = "天空伊纳II"

    constructor(count: Int, extra: Int) : this(count) {
        setExtraPrints(extra)
    }

    override val value: Int = 22
        get() = field + extraPrints
}

/** 天空伊纳III */
class PetInerIII(count: Int) : PetIner(count) {
    override val name: String = "天空伊纳III"
    override var dopingFlag: Boolean = false

    constructor(count: Int, extra: Boolean) : this(count) {
        dopingFlag = extra
    }

    override val value: Int = 105
        get() = field + extraPrints
}