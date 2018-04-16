package fr.androidmakers.buildsrc

object Helper {
    @JvmStatic
    @JvmOverloads
    fun ImThirsty(count: Int = 42) {
        for (i in count downTo 0) {
            println("$i bottles of beer on the wall, \n" +
                    "Take one down and pass it around…")
        }
    }
}