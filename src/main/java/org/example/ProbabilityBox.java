package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProbabilityBox {
    private final List<String> possibilities = new ArrayList<String>();
    private boolean initializing = true;

    public void possible(String... givenPossibilities) {
        if(possibilities.size() == 1) return;
        if(initializing) {
            Collections.addAll(possibilities, givenPossibilities);
            initializing = false;
        } else {
            possibilities.retainAll(Arrays.asList(givenPossibilities));
        }
    }

    public List<String> getPossibilities() {
        return possibilities;
    }

    public boolean shouldBother() {
        return getPossibilities().size() != 1;
    }
}
