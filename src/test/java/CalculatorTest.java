import org.example.Calculator;
import org.example.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    public void testSum() {
        double result = Calculator.sum(2, 3);
        Assertions.assertEquals(5, result, "Розрахунок неправильний");
    }

    @Test
    public void testDifference() {
        double result = Calculator.difference(2, 3);
        Assertions.assertEquals(-1, result, "Розрахунок неправильний");
    }

    @Test
    public void testMultiplication() {
        double result = Calculator.multiplication(2, 3);
        Assertions.assertEquals(6, result, "Розрахунок неправильний");
    }

    @Test
    public void testDivision() {
        double result = Calculator.division(6, 3);
        Assertions.assertEquals(2, result, "Розрахунок неправильний");
    }

    @Test
    public void testDivisionByZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Calculator.division(6, 0);
        }, "Має бути виключення при діленні на нуль");
    }

    @Test
    public void testInvalidNumberInDivision() {
        Assertions.assertThrows(ArithmeticException.class, () -> {
            
            Calculator.division(6, 0);
        }, "Має бути виключення при діленні на нуль");
    }

    @Test
    public void testSquareRoot() throws InvalidInputException {
        double result = Calculator.squareRoot(4);
        Assertions.assertEquals(2, result, "Розрахунок квадратного кореня неправильний");
    }

    @Test
    public void testSquareRootNegative() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            Calculator.squareRoot(-1);
        }, "Має бути виключення при обчисленні квадратного кореня з від'ємного числа");
    }
}
