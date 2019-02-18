package com.e_graphite.attto.contactform.client;

/**
 * External variable with integer type
 * @author attobus@gmail.com
 * @see {@link ExternalVariable}
 */
public class ExternalIntegerVariable extends ExternalVariable<Integer> {
    public ExternalIntegerVariable(String jsVar, Integer defaultValue) {
	super();

	value = defaultValue;
	
	String str = getJString(jsVar);
	if (str != null) {
	    try {
		value = Integer.decode(str);
	    } catch (NumberFormatException nfe) {}
	}
    }
}
