import logic.Calculator;
import logic.ComplicatedCalculator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class ComplicatedCalculatorTest extends BaseTest{
    @Before
    public void setUp() throws Exception {
        calculator = new ComplicatedCalculator();
        reflectCalculator = calculator.getClass();
        paramTypes = new Class[]{String.class};
    }

    @Test
    public void testsqrtModule() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = reflectCalculator.getDeclaredMethod("sqrtModule", paramTypes);
        method.setAccessible(true);
        Object[] args = new Object[]{new String("√4")};
        Integer d = Integer.parseInt(method.invoke(calculator, args).toString());
        assertTrue(d.equals(2));

    }
}
