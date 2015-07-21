package com.ankitgohel.crystalball;

import java.util.Random;

public class CrystalBall
{
    // Member Variables

    public String mAnswers[]={
            "All signs say Yes",
            "It is certain",
            "Without a doubt",
            "Most likely",
            "Reply hazy try again",
            "It is doubtful",
            "I cannot answer right now",
            "My reply is No",
            "Don't count on it",
            "Concentrate and ask again",
            "Ask again later"};

    //Methods

    public String getAnAnswer()
    {
        String answer = " ";

        // Randomly select one of the answers from the array

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mAnswers.length);
        answer = mAnswers[randomNumber];

        return answer;

    }
}
