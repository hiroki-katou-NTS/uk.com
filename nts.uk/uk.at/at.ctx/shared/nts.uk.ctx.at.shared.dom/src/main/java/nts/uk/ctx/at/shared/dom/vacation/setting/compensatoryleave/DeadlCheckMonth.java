package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

/**
 * 代休経過月数
 * @author HoangNDH
 */
public enum DeadlCheckMonth {
	
	/** The one month. */
	ONE_MONTH(0,"1ヶ月前", "1ヶ月前"),
	
	/** The two month. */
	TWO_MONTH(1,"2ヶ月前", "2ヶ月前"),
	
	/** The three month. */
	THREE_MONTH(2,"3ヶ月前", "2ヶ月前"),
	
	/** The four month. */
	FOUR_MONTH(3,"4ヶ月前", "3ヶ月前"),
	
	/** The five month. */
	FIVE_MONTH(4,"5ヶ月前", "4ヶ月前"),
	
	/** The six month. */
	SIX_MONTH(5,"6ヶ月前", "5ヶ月前");
	
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
