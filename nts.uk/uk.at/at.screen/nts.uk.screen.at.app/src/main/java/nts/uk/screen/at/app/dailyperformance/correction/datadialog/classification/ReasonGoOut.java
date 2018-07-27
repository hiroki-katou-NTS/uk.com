package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("unused")
/** 外出理由 */
public enum ReasonGoOut   {
	// 私用
	SUPPORT(0, "私用"),
	// 公用
	UNION(1, "公用"),
	// 有償
	CHARGE(2, "有償"),
	// 組合
	OFFICAL(3, "組合");

	public final int code;
	
	public final String name;
	
	private final static ReasonGoOut[] values = ReasonGoOut.values();
}
