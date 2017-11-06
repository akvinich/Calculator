import logic.Calculator;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;

public class BaseTest {
    Calculator calculator = new Calculator();

    @Test
    public void testAddModule() throws InvocationTargetException, IllegalAccessException {
        Class reflectCalculator = calculator.getClass();
        Class[] paramTypes = new Class[]{String.class};

        try {
            Method method = reflectCalculator.getDeclaredMethod("addModule", paramTypes);
            method.setAccessible(true);
            Object[] args = new Object[]{new String("-2+2")};
            Integer d = Integer.parseInt(method.invoke(calculator, args).toString());
            assertTrue(d.equals(0));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
