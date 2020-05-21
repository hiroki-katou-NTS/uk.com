package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

/**
 * 音声種類
 * @author phongtq
 *
 */
public enum AudioType {
	
	/** なし */
	NONE(0, "なし", "AudioType_NONE"),

	/** おはようございます */
	GOOD_MORNING(1, "おはようございます", "AudioType_GOOD_MORNING"),

	/** お疲れ様でした */
	GOOD_JOB(2, "お疲れ様でした", "AudioType_GOOD_JOB");

	/** The value. */
	public int value;
	
	/** The name id. */
	public  String nameId;

	/** The description. */
	public  String description;

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
	private AudioType (int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
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
