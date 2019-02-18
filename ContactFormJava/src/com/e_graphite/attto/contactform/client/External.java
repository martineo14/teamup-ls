package com.e_graphite.attto.contactform.client;

/**
 * Convenience class with external variables and their default values.
 * Default values equivalent the next block in host page: 
 * <pre>
 * 	&lt;script&gt; 
 *		contactform_ok = "Thank you! The message has been sent.";
 *		contactform_error = "Ooops! Something has gone wrong. Try again later.";
 *		contactform_message_appearance_interval = "1000";
 *		contactform_message_wait_interval = "1000";
 *		contactform_message_disappearance_interval = "1000";
 *	&lt;/script&gt;
 * </pre>
 * @author attobus@gmail.com
 * @see {@link ExternalVariable}, {@link ExternalIntegerVariable}, {@link ExternalStringVariable}
 */
public class External {
    public static final ExternalVariable<String> OK = new ExternalStringVariable(
	    "contactform_ok", "Thank you! The message has been sent.");
    public static final ExternalVariable<String> ERROR = new ExternalStringVariable(
	    "contactform_error",
	    "Ooops! Something has gone wrong. Try again later.");
    public static final ExternalVariable<Integer> APPEARANCE_INTERVAL = new ExternalIntegerVariable(
	    "contactform_message_appearance_interval", 1000);
    public static final ExternalVariable<Integer> WAIT_INTERVAL = new ExternalIntegerVariable(
	    "contactform_message_wait_interval", 1000);
    public static final ExternalVariable<Integer> DISAPPEARANCE_INTERVAL = new ExternalIntegerVariable(
	    "contactform_message_appearance_interval", 1000);
}
