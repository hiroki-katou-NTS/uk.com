package nts.uk.ctx.pr.core.dom.itemmaster;

public enum AvePayAtr {
	// 平均賃金対象区分
	NotApplicable(0, "対象外"),
	// 対象外
	Object(1, "対象");
	// 対象

	public final int value;

	public final String text;

	AvePayAtr(int value, String text) {
		this.value = value;
		this.text = text;
	}
}
