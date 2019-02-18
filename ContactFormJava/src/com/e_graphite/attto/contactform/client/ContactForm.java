package com.e_graphite.attto.contactform.client;

import static com.google.gwt.http.client.RequestBuilder.POST;

import com.e_graphite.attto.contactform.client.HeightAnimation.CompleteHandler;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;

/**
 * The main class with entry point method
 * @author attobus@gmail.com
 */
public class ContactForm implements EntryPoint {

	// Url to submit script
    private static final String URL = "submit.php";
    
    // OK response string of submit script
    private static final String OK_RESPONSE = "Sent";

    // request builder 
    private RequestBuilder builder;
    
    // elements of submit and reset buttons
    private InputElement submitElement, resetElement;
    // elements of ok and error messages
    private Element okMessageElement, errorMessageElement;
    // height of message string in pixels
    private int height;

    /**
     * The entry point method.
     */
    public void onModuleLoad() {

	// Get list of <input> elements in the host page
	NodeList<Element> elements = Document.get().getElementsByTagName("input");
	
	// Check if there is <input> element in the host page.
	if (elements.getLength() == 0) {
	    GWT.log("no input element");
	    return;
	}

	for (int i = 0; i < elements.getLength(); i++) {

	    // find "submit" button
	    Element element = elements.getItem(i);
	    if ("submit".equalsIgnoreCase(element.getPropertyString("type"))
		    && InputElement.is(element)) {

		// change "submit" to ordinary button 
		element.setPropertyString("type", "button");
		submitElement = InputElement.as(element);
		GWT.log("found submit element=" + element);

		// add click listener to button
		Event.sinkEvents(element, Event.ONCLICK);
		Event.setEventListener(element, new EventListener() {
		    @Override
		    public void onBrowserEvent(Event event) {
			clicked();
		    }
		});
		continue;
	    }

	    // find "reset" element
	    if ("reset".equalsIgnoreCase(element.getPropertyString("type"))
		    && InputElement.is(element)) {

		resetElement = InputElement.as(element);
		GWT.log("found reset element=" + element);
	    }

	}

	// add success and error messages under input fields
	addMessages();

	// initialize request builder
	builder = new RequestBuilder(POST, URL);
	builder.setHeader("Content-type", "application/x-www-form-urlencoded");

	GWT.log("OK=" + External.OK);
	GWT.log("ERROR=" + External.ERROR);
	GWT.log("APPEARANCE_INTERVAL=" + External.APPEARANCE_INTERVAL);
	GWT.log("WAIT_INTERVAL=" + External.WAIT_INTERVAL);
	GWT.log("DISAPPEARANCE_INTERVAL=" + External.DISAPPEARANCE_INTERVAL);
    }

    /**
     * Process click. Send message.
     */
    private void clicked() {

	// Disable buttons
	submitElement.setDisabled(true);
	resetElement.setDisabled(true);

	// create request payload
	StringBuilder sb = new StringBuilder().append("name=")
		.append(getInputValueById("name")).append("&email=")
		.append(getInputValueById("email")).append("&subject=")
		.append(getInputValueById("subject")).append("&message=")
		.append(getTextAreaValueById("message"));

	try {
	    // Send request
	    builder.sendRequest(sb.toString(), new RequestCallback() {

		@Override
		public void onResponseReceived(Request request,
			Response response) {
		    // Check if response is ok
		    if (response.getStatusCode() == 200
			    && OK_RESPONSE.equals(response.getText())) {
			//display success message
			displayOk();
		    } else {
			//display error message
			displayError();
		    }
		}

		@Override
		public void onError(Request request, Throwable e) {
		    GWT.log("Response exception", e);
		    //display error message
		    displayError();
		}

	    });
	} catch (RequestException e) {
	    GWT.log("Response exception", e);
	    //display error message
	    displayError();
	}

	GWT.log("data:" + sb);
	GWT.log("Button clicked");

    }

    /**
     * Shows animated error message
     */
    private void displayError() {
	// animate error message's element
	animateShow(errorMessageElement);
	GWT.log("Error");
    }

    /**
     * Shows animated success message
     */
    private void displayOk() {
	// animate success message's element
	animateShow(okMessageElement);
	clearForm();
	GWT.log("Ok");
    }

    /**
     * Clears all fields of input form
     */
    private void clearForm() {
	setInputValueById("name", "");
	setInputValueById("email", "");
	setInputValueById("subject", "");
	setTextAreaValueById("message", "");
    }

    /**
     * Animates the appearance of element
     * @param element - animated element
     */
    private void animateShow(final Element element) {
	HeightAnimation animation = new HeightAnimation(element);
	animation.setHandler(new CompleteHandler() {
	    @Override
	    public void onCompelte() {
		animateWait(element);
	    }
	});
	animation.animate(External.APPEARANCE_INTERVAL.getValue(), 2 * height);
    }

    /**
     * Wait for next animation
     * @param element - animated element
     */
    private void animateWait(final Element element) {
	new Timer() {
	    @Override
	    public void run() {
		animateHide(element);
	    }
	}.schedule(External.WAIT_INTERVAL.getValue());
    }

    /**
     * Animates the disappearance of element
     * @param element - animated element
     */
    private void animateHide(final Element element) {
	HeightAnimation animation = new HeightAnimation(element);
	animation.setHandler(new CompleteHandler() {
	    @Override
	    public void onCompelte() {
		// enable buttons
		submitElement.setDisabled(false);
		resetElement.setDisabled(false);
	    }
	});
	animation.animate(External.DISAPPEARANCE_INTERVAL.getValue(), 0);
    }

    /**
     * Adds success and error messages under input fields and makes them invisible
     */
    private void addMessages() {
	
	// create <div> elements for messages
	okMessageElement = DOM.createElement("div");
	okMessageElement.setInnerText(External.OK.toString());
	errorMessageElement = DOM.createElement("div");
	errorMessageElement.setInnerText(External.ERROR.toString());

	// find parent <div> for "Send message" button 
	Element e;
	do {
	    e = submitElement.getParentElement();
	} while ("div".equals(e.getTagName()));
	e = e.getParentElement();
	Element pe = e.getParentElement();
	// remove <div> with buttons from host page
	e.removeFromParent();
	// add <div> elements for messages to host page after input fields
	pe.appendChild(okMessageElement);
	pe.appendChild(errorMessageElement);
	// add <div> with buttons to host page after messages
	pe.appendChild(e);

	// get height of message in pixels
	height = okMessageElement.getOffsetHeight() > errorMessageElement
		.getOffsetHeight() ? okMessageElement.getOffsetHeight()
		: errorMessageElement.getOffsetHeight();
	GWT.log("height=" + height);
	
	// hide messages by setting their height to 0 ... 
	errorMessageElement.getStyle().setHeight(0, Unit.PX);
	okMessageElement.getStyle().setHeight(0, Unit.PX);

	// ... and hide overflow of elements
	errorMessageElement.getStyle().setOverflow(Overflow.HIDDEN);
	okMessageElement.getStyle().setOverflow(Overflow.HIDDEN);
    }

    /**
     * Gets content of input field with given id
     * @param id - id of input field
     * @return String - content of input field
     */
    private String getInputValueById(String id) {

	String value = "";
	Element element = Document.get().getElementById(id);
	if (element != null && InputElement.is(element)) {
	    value = InputElement.as(element).getValue();
	}
	return com.google.gwt.http.client.URL.encodeQueryString(value);
    }

    /**
     * Gets content of text area with given id
     * @param id - id of text area
     * @return String - content of text area
     */
    private String getTextAreaValueById(String id) {

	String value = "";
	Element nameElement = Document.get().getElementById(id);
	if (nameElement != null && TextAreaElement.is(nameElement)) {
	    value = TextAreaElement.as(nameElement).getValue();
	}
	return com.google.gwt.http.client.URL.encodeQueryString(value);
    }
    
    /**
     * Fills the input field by given string
     * @param id - id of input field
     * @param value - new content of input field 
     */
    private void setInputValueById(String id, String value) {

	Element element = Document.get().getElementById(id);
	if (element != null && InputElement.is(element)) {
	    InputElement.as(element).setValue(value);
	}
    }

    /**
     * Fills the text area by given string
     * @param id - id of text area
     * @param value - new content of text area 
     */    
    private void setTextAreaValueById(String id, String value) {

	Element nameElement = Document.get().getElementById(id);
	if (nameElement != null && TextAreaElement.is(nameElement)) {
	    TextAreaElement.as(nameElement).setValue(value);
	}
    }

}
