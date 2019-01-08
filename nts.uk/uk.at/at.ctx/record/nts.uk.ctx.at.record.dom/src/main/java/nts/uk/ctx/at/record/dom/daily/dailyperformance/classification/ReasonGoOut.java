package nts.uk.ctx.at.record.dom.daily.dailyperformance.classification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** 外出理由 */
public enum ReasonGoOut {
	// 私用
	SUPPORT(0, "私用"),
	// 公用
	UNION(1, "公用"),
	// 有償
	CHARGE(2, "有償"),
	// 組合
	OFFICAL(3, "組合");

	public final int value;

	public final String description;

	private final static ReasonGoOut[] values = ReasonGoOut.values();
	
	public static ReasonGoOut valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReasonGoOut val : ReasonGoOut.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
}
