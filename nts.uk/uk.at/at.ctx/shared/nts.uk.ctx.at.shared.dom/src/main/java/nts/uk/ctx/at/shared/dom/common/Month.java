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

	/** The Constant JANUARY. */
	public static final int JANUARY = 1;

	/** The Constant FEBRUARY. */
	public static final int FEBRUARY = 2;

	/** The Constant MARCH. */
	public static final int MARCH = 3;

	/** The Constant APRIL. */
	public static final int APRIL = 4;

	/** The Constant MAY. */
	public static final int MAY = 5;

	/** The Constant JUNE. */
	public static final int JUNE = 6;

	/** The Constant JULY. */
	public static final int JULY = 7;

	/** The Constant AUGUST. */
	public static final int AUGUST = 8;

	/** The Constant SEPTEMBER. */
	public static final int SEPTEMBER = 9;

	/** The Constant OCTOBER. */
	public static final int OCTOBER = 10;

	/** The Constant NOVEMBER. */
	public static final int NOVEMBER = 11;

	/** The Constant DECEMBER. */
	public static final int DECEMBER = 12;

	/**
	 * Instantiates a new month.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public Month(Integer rawValue) {
		super(rawValue);
	}

}
