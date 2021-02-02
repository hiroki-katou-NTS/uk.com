package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

/**
 * 一日の時間年休時間参照場所
 * @author masaaki_jinno
 *
 */
public enum AnnualTimePerDayRefer {
	
	/** 全社一律 */
	CompanyUniform(1, "全社一律", "全社一律"),

	/** 社員の契約時間により算定 */
	EmploeeContractTime(2, "社員の契約時間により算定", "社員の契約時間により算定");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static AnnualTimePerDayRefer[] values = AnnualTimePerDayRefer.values();

	/**
	 * Instantiates a new setting  distinct.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private AnnualTimePerDayRefer(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the setting distinct
	 */
	public static AnnualTimePerDayRefer valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AnnualTimePerDayRefer val : AnnualTimePerDayRefer.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}