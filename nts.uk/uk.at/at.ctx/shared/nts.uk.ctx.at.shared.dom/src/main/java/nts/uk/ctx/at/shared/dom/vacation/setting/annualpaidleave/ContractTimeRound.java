package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * 契約時間丸め
 * @author masaaki_jinno
 *
 */
public enum ContractTimeRound {

    /** Round up to 1 hour. */
	RoundUpTo1Hour(1, "１時間に切り上げる", "１時間に切り上げる"),
    
    /** Not round. */
    NotRound(2, "丸めない", "丸めない");

	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static ContractTimeRound[] values = ContractTimeRound.values();

	/**
	 * Instantiates a new display division.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ContractTimeRound(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the display division
	 */
	public static ContractTimeRound valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ContractTimeRound val : ContractTimeRound.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
