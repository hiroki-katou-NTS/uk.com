/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class Month.
 */
@IntegerRange(min = 1, max = 12)
public class Month extends IntegerPrimitiveValue<Month> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public static final int JANUARY = 1;
	public static final int FEBRUARY = 2;
	public static final int MARCH = 3;
	public static final int APRIL = 4;
	public static final int MAY = 5;
	public static final int JUNE = 6;
	public static final int JULY = 7;
	public static final int AUGUST = 8;
	public static final int SEPTEMBER = 9;
	public static final int OCTOBER = 10;
	public static final int NOVEMBER = 11;
	public static final int DECEMBER = 12;

	/**
	 * Instantiates a new month.
	 *
	 * @param rawValue the raw value
	 */
	public Month(Integer rawValue) {
		super(rawValue);
	}

}
