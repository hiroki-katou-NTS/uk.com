package nts.uk.ctx.pr.core.dom.itemperiod;

public enum UsageClassification {
	// 0:利用しない
	NotUsed(0),
	// 1:利用する
	Use(1);

	public final int value;

	UsageClassification(int value) {
			this.value = value;
		}
}
