package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GoOutReason {
	// 私用
	SUPPORT(0, "私用"),
	// 公用
	UNION(1, "公用"),
	// 有償
	CHARGE(2, "有償"),
	// 組合
	OFFICAL(3, "組合");

	public final int value;
	public final String name;

}
