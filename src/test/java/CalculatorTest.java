import logic.Calculator;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class CalculatorTest extends BaseTest{
    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
        reflectCalculator = calculator.getClass();
        paramTypes = new Class[]{String.class};
    }

    @Test
    public void testAddModule() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = reflectCalculator.getDeclaredMethod("addModule", paramTypes);
        method.setAccessible(true);
        Object[] args = new Object[]{new String("-2+2")};
        Integer d = Integer.parseInt(method.invoke(calculator, args).toString());
        assertTrue(d.equals(0));
    }

    @Test
    public void testDivModule() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException  {
        Method method = reflectCalculator.getDeclaredMethod("divModule", paramTypes);
        method.setAccessible(true);
        Object[] args = new Object[]{new String("-2/-2")};
        Integer d = Integer.parseInt(method.invoke(calculator, args).toString());
        assertTrue(d.equals(1));
    }

    @Test
    public void testOperationModule() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException  {
        Method method = reflectCalculator.getDeclaredMethod("operation", paramTypes);
        method.setAccessible(true);
        Object[] args = new Object[]{new String("4+4*(15-5)")};
        Integer d = Integer.parseInt(method.invoke(calculator, args).toString());
        assertTrue(d.equals(44));
    }
}
