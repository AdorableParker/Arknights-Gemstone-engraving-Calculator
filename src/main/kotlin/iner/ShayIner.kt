package iner

/** 沙伊纳 */
object ShayIner : Iner(0) {
    override val name: String = "沙伊纳"
    override var count: Int = 0
    override var value: Int = 1

    fun init(count: Int): Iner {
        ShayIner.count = count
        return this
    }

    fun init(count: Int, upgraded: Boolean): Iner {
        ShayIner.count = count
        value = if (upgraded) 2 else 1
        return this
    }

    fun resetState() {
        value = 1
    }
}