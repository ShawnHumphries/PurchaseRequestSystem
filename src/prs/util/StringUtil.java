package prs.util;

import java.text.NumberFormat;

/*
 * The StringUtil class contains utility methods to help with strings.
 */

public class StringUtil {

	/*
	 * Returns the passed in double as a formatted currency value.
	 */
	public static String getFormattedDouble(double input)
	{
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(input);
	}

}
