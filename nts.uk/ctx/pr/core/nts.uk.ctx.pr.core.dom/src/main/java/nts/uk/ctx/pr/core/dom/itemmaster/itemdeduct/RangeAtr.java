package nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct;

public enum RangeAtr {

	// エラー範囲下限利用区分
	NotUsed(0),
	// 利用しない
	ToUse(1);
	// 利用する
	
	public final int value;

	RangeAtr(int value) {
		this.value = value;
	}
}
