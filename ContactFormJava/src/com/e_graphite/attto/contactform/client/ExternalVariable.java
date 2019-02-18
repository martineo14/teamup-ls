package com.e_graphite.attto.contactform.client;

/**
 * Generic class for external variable 
 * @author attobus@gmail.com
 * @param <T> - Type of external variable
 */
public abstract class ExternalVariable<T> {
    protected T value;

    public T getValue() {
	return value;
    }

    /**
     * Overrides the default behavior of toString().
     * Returns string representation of value
     * @return String representation of value 
     */
    @Override
    public String toString() {
	return value.toString();
    }

    /**
     * Gets value of jsVar String variable in host page
     * @param jsVar - String variable in host page
     * @return String value of given variable
     * <pre>
     * For example:
     * In host page:
     * ...
     * 	&lt;script&gt; 
     *		host_thanks = "Thank you!";
     *	&lt;/script&gt;
     * ...
     * 
     * In the code:
     * String thanks=getJString("host_thanks");
     * // thanks contains "Thank you!"
     * </pre>
     */
    protected static native String getJString(String jsVar) /*-{
		return eval('$wnd.' + jsVar);
    }-*/;

}
