package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProbabilityBox {
    private final List<String> possibilities = new ArrayList<String>();
    private boolean initializing = true;
    private boolean contradictionFound = false;
    public void possible(String... givenPossibilities) {
        if(contradictionFound) return;
       if(containsAny(possibilities, givenPossibilities)) {
           possibilities.retainAll(Arrays.asList(givenPossibilities));
       } else {
           possibilities.addAll(List.of(givenPossibilities));
           if(initializing) {
               initializing = false;
           } else {
               contradictionFound = true;
               possibilities.clear();
           }
       }
    }

    public List<String> getPossibilities() {
        return possibilities;
    }
    private static boolean containsAny(List<String> a, String[] b) {
        for (String element : b) {
            if (a.contains(element)) {
                return true; // Found a match
            }
        }
        return false; // No match found
    }
}
