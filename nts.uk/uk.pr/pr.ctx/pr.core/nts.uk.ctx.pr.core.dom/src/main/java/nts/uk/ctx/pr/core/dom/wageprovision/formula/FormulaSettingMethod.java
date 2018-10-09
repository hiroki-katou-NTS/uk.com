package nts.uk.ctx.pr.core.dom.wageprovision.formula;

/**
 * 
 * @author HungTT - 計算式の設定方法
 *
 */

public enum FormulaSettingMethod {

	SIMPLE_SETTING(0, "かんたん設定"),
	
	DETAIL_SETTING(1, "詳細設定");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private FormulaSettingMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
