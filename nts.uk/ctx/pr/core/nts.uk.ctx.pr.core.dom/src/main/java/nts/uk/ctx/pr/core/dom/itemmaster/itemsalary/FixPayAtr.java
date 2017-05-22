package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

public enum FixPayAtr {
	// 固定的賃金対象区分
	SpecifiedEveryone(0, "全員一律で指定する"),
	// 全員一律で指定する
	SpecifyForEach(1, "給与契約形態ごとに指定する");
	// 給与契約形態ごとに指定する
	public final int value;
	public final String text;

	FixPayAtr(int value, String text) {
		this.value = value;
		this.text = text;
	}

}
