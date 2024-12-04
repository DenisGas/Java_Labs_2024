// StateMachineTest.java
import org.example.StateMachine;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;

public class TestDetectorFSMTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void testStateMachine(String input, StateMachine.State expectedState) {
        StateMachine machine = new StateMachine();
        StateMachine.State result = machine.processString(input);
        assertEquals(expectedState, result);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of("F", StateMachine.State.S_0),
                Arguments.of("TF", StateMachine.State.S_0),
                Arguments.of("TEF", StateMachine.State.S_0),
                Arguments.of("TESF", StateMachine.State.S_0),

                Arguments.of("T", StateMachine.State.S_1),
                Arguments.of("TE", StateMachine.State.S_2),
                Arguments.of("TES", StateMachine.State.S_3),

                Arguments.of("TEST", StateMachine.State.F),
                Arguments.of("TES T", StateMachine.State.S_1),
                Arguments.of("TESTOP", StateMachine.State.F),

                Arguments.of("", StateMachine.State.S_0),

                Arguments.of("TESTEST", StateMachine.State.F),
                Arguments.of("TTEST", StateMachine.State.F)
        );
    }
}