package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * 代休経過月数
 * @author HoangNDH
 */
public enum DeadlCheckMonth {
	
	/** 1ヶ月前 */
	ONE_MONTH(0,"Enum_DeadlCheckMonth_ONE_MONTH", "1ヶ月前"),
	
	/** 2ヶ月前 */
	TWO_MONTH(1,"Enum_DeadlCheckMonth_TWO_MONTH", "2ヶ月前"),
	
	/** 3ヶ月前 */
	THREE_MONTH(2,"Enum_DeadlCheckMonth_THREE_MONTH", "3ヶ月前"),
	
	/** 4ヶ月前 */
	FOUR_MONTH(3,"Enum_DeadlCheckMonth_FOUR_MONTH", "4ヶ月前"),
	
	/** 5ヶ月前 */
	FIVE_MONTH(4,"Enum_DeadlCheckMonth_FIVE_MONTH", "5ヶ月前"),
	
	/** 6ヶ月前 */
	SIX_MONTH(5,"Enum_DeadlCheckMonth_SIX_MONTH", "6ヶ月前");
	
	/** The value. */
	public int value;
	
	/** The name id. */
	private String nameId;
	
	/** The description. */
	private String description;
	
	 /** The Constant values. */
    private final static DeadlCheckMonth[] values = DeadlCheckMonth.values();

	/**
	 * Instantiates a new deadl check month.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private DeadlCheckMonth(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	
	/**
     * Value of.
     *
     * @param value the value
     * @return the nursing category
     */
    public static DeadlCheckMonth valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (DeadlCheckMonth val : DeadlCheckMonth.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
