package nts.uk.ctx.at.record.dom.monthly.erroralarm;

/**
 * @author dungdt フレックスエラー
 */
public enum Flex {

	FLEX_EXCESS_CARRYOVER_TIME(0, "フレックス繰越可能時間超過"),

	FLEX_SHORTAGE_TIME_EXCESS_DEDUCTION(1, "フレックス不足時間過剰控除"),

	FLEX_YEAR_HOLIDAY_DEDUCTIBLE_DAYS(2, "フレックス年休控除可能日数超過"),

	FLEX_INCOMPLETE_OF_CARRYFORWARD(3, "前月繰越の補填未完了");

	public int value;

	public String nameId;

	private Flex(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	private final static Flex[] values = Flex.values();

	public static Flex valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Flex val : Flex.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
