package com.practice.random;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator {
    public static Set<Integer> uniqueNumberGenerator(int max){
        Random random = new Random();
       /* if(max < max){
            throw new IllegalArgumentException("Dont ask more than available!");
        }*/
        Set<Integer> generator = new LinkedHashSet<Integer>();
        while(generator.size() < max){
            Integer next = random.nextInt(max) + 1;
            generator.add(next);

        }
        return generator;
    }
    public static void main(String args[]){
       Set<Integer> result = RandomNumberGenerator.uniqueNumberGenerator(30);
       System.out.println(result);
    }
}
