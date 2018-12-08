package com.nusretozates.wake_up.Utils

import java.util.*

class RandomStringGenerator {

    internal var generator = Random()


    fun generate(size: Int): String {
        val builder = StringBuilder()
        for (i in 0 until size) {
            val randomletter = (generator.nextInt(26) + 97).toChar()
            builder.append(randomletter)
        }

        return builder.toString()


    }


}
