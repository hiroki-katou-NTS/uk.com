package nts.uk.ctx.at.record.dom.stamp.management;
/**
 * 予約区分
 * @author phongtq
 *
 */
public enum ReservationArt {
	
	/** なし */
	NONE(0),

	/** 予約 */
	RESERVATION(1),

	/** 予約取消 */
	CANCEL_RESERVATION(2);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ReservationArt[] values = ReservationArt.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ReservationArt(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ReservationArt valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReservationArt val : ReservationArt.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}

