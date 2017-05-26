/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * The Enum ExpirationTime.
 */
public enum ExpirationTime {
	
	/** The This month. */
	ThisMonth(1),
	
	/** The Unlimited. */
	Unlimited(2),
	
	/** The Year end clear. */
	YearEndClear(3),
	
	/** The One month. */
	OneMonth(4),
	
	/** The Two month. */
	TwoMonth(5),
	
	/** The Three month. */
	ThreeMonth(6),
	
	/** The Four month. */
	FourMonth(7),
	
	/** The Five month. */
	FiveMonth(8),
	
	/** The Six month. */
	SixMonth(9),
	
	/** The Seven month. */
	SevenMonth(10),
	
	/** The Eight month. */
	EightMonth(11),
	
	/** The Nine month. */
	NineMonth(12),
	
	/** The Ten month. */
	TenMonth(13),
	
	/** The Eleven month. */
	ElevenMonth(14),
	
	/** The One year. */
	OneYear(14),
	
	/** The Two year. */
	TwoYear(15),
	
	/** The Three year. */
	ThreeYear(16),
	
	/** The Four year. */
	FourYear(17),
	
	/** The Five year. */
	FiveYear(18);
	
	/** The value. */
	public int value;
	
	/**
	 * Instantiates a new expiration time.
	 *
	 * @param value the value
	 */
	private ExpirationTime(Integer value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the expiration time
	 */
	public static ExpirationTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExpirationTime val : ExpirationTime.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
