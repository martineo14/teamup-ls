package com.e_graphite.attto.contactform.client;

import java.math.BigDecimal;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;

/**
 * The implementation of animation of the height property of element
 * @author attobus@gmail.com
 *
 */
public class HeightAnimation extends Animation {
    private Element element;
    private double heightIncrement;
    private double targetHeight;
    private double baseHeight;

    // The handler of completion of animation
    private CompleteHandler handler;

    /**
     * Constructs animation for given element 
     * @param element - animated element
     */
    public HeightAnimation(Element element) {
	this.element = element;
    }

    @Override
    protected void onUpdate(double progress) {
	// calculate new height according to the progress
	double height = baseHeight + progress * heightIncrement;
	// set new height to element 
	element.getStyle().setHeight(height, Unit.PX);
    }

    @Override
    protected void onComplete() {
	super.onComplete();
	// ensure that the height of the element == target height
	element.getStyle().setHeight(targetHeight, Unit.PX);
	// call onComplete method on handler if the handler is set
	if (handler != null)
	    handler.onCompelte();
    }

    public void setHandler(CompleteHandler handler) {
	this.handler = handler;
    }

    /**
     * Starts animation of element with given parameters
     * @param duration - duration of animation in milliseconds
     * @param targetHeight - the height of element after animation
     */
    public void animate(int duration, double targetHeight) {
	// set targetHeight to zero if it is less then zero   
	if (targetHeight < 0.0) {
	    targetHeight = 0.0;
	}
	this.targetHeight = targetHeight;
	//get current height of element
	String heightStr = element.getStyle().getHeight();
	//remove px from the end of string
	if (heightStr.endsWith("px"))
	    heightStr = heightStr.substring(0, heightStr.length() - 2);
	try {
	    // translate string to double and set baseHeight
	    baseHeight = new BigDecimal(heightStr).doubleValue();
	    // calculate increment
	    heightIncrement = targetHeight - baseHeight;
	    // run animation
	    run(duration);
	} catch (NumberFormatException e) {
	    // complete the process if something went wrong with conversion of string to number 
	    onComplete();
	}
    }

    /**
     *	Interface of completion handler  
     * @author attobus@gmail.com
     */
    public static interface CompleteHandler {
	/**
	 * HeightAnimation calls this method when the process completed.
	 */
	public void onCompelte();
    }
}
