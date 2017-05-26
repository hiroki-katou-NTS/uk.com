package nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct;

public enum DeductAtr {
	// 控除項目種類
	OptionalDeduction(0, "任意控除"),
	// 任意控除
	TaxDeduction(1, "社保控除"),
	// 社保控除
	IncomeTaxDeduction(2, "所得税控除"),
	// 所得税控除
	InhabitantTaxDeduction(3, "住民税控除");
	// 住民税控除

	public final int value;
	public final String text;

	DeductAtr(int value, String text) {
		this.value = value;
		this.text = text;
	}

}
