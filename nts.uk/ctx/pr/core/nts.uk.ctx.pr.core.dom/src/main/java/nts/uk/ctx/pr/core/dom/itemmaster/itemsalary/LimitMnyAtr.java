package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

public enum LimitMnyAtr {
	// 限度金額指定
	Fixed(0, "固定")
	// 固定
	, SeeTaxExemptionLimitMaster(1, "非課税限度額マスタを参照")
	// 非課税限度額マスタを参照
	, PersonalTransportationReferenceLimit(2, "個人の交通機関限度参照")
	// 個人の交通機関限度参照
	, ReferToIndividualTransportationToolLimitAmount(3, "個人の交通用具限度額参照")
	// 個人の交通用具限度額参照
	;
	public final int value;
	public final String text;

	LimitMnyAtr(int value, String text) {
		this.value = value;
		this.text = text;
	}
}
