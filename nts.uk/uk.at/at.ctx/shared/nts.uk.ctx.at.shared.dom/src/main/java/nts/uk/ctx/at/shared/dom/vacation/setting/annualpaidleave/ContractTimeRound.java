package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/** 契約時間丸め **/
public enum ContractTimeRound {

	Round_up_to_1_hour(0, "１時間に切り上げる", "１時間に切り上げる"), 
	
	Do_not_round(1, "丸めない", "丸めない");

	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static ContractTimeRound[] values = ContractTimeRound.values();

	private ContractTimeRound(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

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
