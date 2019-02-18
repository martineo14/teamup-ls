package com.e_graphite.attto.contactform.client;

/**
 * External variable with String type
 * @author attobus@gmail.com
 * @see {@link ExternalVariable}
 */
public class ExternalStringVariable extends ExternalVariable<String> {
    public ExternalStringVariable(String jsVar, String defaultValue) {
	super();

	value = getJString(jsVar);
	if (value == null)
	    value = defaultValue;
    }
}
