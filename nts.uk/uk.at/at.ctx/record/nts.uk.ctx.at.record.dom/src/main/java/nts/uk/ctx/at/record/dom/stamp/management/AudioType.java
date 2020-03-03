package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 音声種類
 * @author phongtq
 *
 */
public enum AudioType {
	
	/** なし */
	NONE(0),

	/** おはようございます */
	GOOD_MORNIN(1),

	/** お疲れ様でした */
	GOOD_JOB(2);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static AudioType[] values = AudioType.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private AudioType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static AudioType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AudioType val : AudioType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
