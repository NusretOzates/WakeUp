package com.nusretozates.wake_up.Utils;

import java.util.Random;

public class RandomStringGenerator
{

    Random generator = new Random();


    public String generate(int size)
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<size;i++)
        {
            char randomletter = (char) (generator.nextInt(26) + 97);
            builder.append(randomletter);
        }

        return builder.toString();


    }


}
