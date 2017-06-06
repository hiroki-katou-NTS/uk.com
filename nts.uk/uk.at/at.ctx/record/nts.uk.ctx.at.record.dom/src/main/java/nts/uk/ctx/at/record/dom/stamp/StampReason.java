package nts.uk.ctx.at.record.dom.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StampReason {
	// 応援
	SUPPORT(0, "応援"),
	// 組合
	UNION(1, "組合"),
	// 有償
	CHARGE(2, "有償"),
	// 公用
	OFFICAL(3, "公用"),
	// 私用
	PRIVATE(4, "私用"),
	// 臨時
	TEMPORARY(5, "臨時");

	public final int value;
	public final String name;

}
