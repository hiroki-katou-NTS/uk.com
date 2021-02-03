package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * enum : 一日の時間年休時間参照場所
 * @author hieuLt
 *
 */
public enum DayTimeAnnualLeave {
	
	/** 全社一律**/
	Company_wide_Uniform(0, "全社一律",  "全社一律"),
	/** 社員の契約時間により算定 **/
	Calculated_Based_On_Employee(1, "社員の契約時間により算定" , "社員の契約時間により算定");   
	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static DayTimeAnnualLeave[] values = DayTimeAnnualLeave.values();
	
	
	private DayTimeAnnualLeave(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	
	public static DayTimeAnnualLeave valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DayTimeAnnualLeave val : DayTimeAnnualLeave.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
