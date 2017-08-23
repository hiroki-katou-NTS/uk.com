package nts.uk.ctx.at.schedule.dom.shift.total.times.setting;

/**
 * The Enum DayCountCategory.
 */
//半日勤務カウント区分

public enum CountAtr {
	
	/** The halfday. */
	//0.5回
	HALFDAY(0, "0.5回"),
	
	/** The oneday. */
	//1回
	ONEDAY(1, "1回");
		
	/** The value. */
	public final int value;
	
	/** The name. */
	public final String name;
	
	/**
	 * To name.
	 *
	 * @return the string
	 */
	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "0.5回";
			break;
		default:
			name = "1回";
			break;
		}
		return name;
	}

	/**
	 * Instantiates a new day count category.
	 *
	 * @param value the value
	 * @param name the name
	 */
	private CountAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	
}
