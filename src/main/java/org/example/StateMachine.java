package org.example;

public class StateMachine {
    private State currentState;

    public enum State {
        S_0, S_1, S_2, S_3, F
    }

    public StateMachine() {
        this.currentState = State.S_0;
    }

    public State processString(String input) {
        for (char c : input.toCharArray()) {
            currentState = nextState(currentState, c);
        }
        return currentState;
    }

    private State nextState(State state, char c) {
        return switch (state) {
            case S_0 -> (c == 'T') ? State.S_1 : State.S_0;
            case S_1 -> (c == 'E') ? State.S_2 : (c == 'T' ? State.S_1 : State.S_0);
            case S_2 -> (c == 'S') ? State.S_3 : (c == 'T' ? State.S_1 : State.S_0);
            case S_3 -> (c == 'T') ? State.F : State.S_0;
            default -> state;
        };
    }
}
