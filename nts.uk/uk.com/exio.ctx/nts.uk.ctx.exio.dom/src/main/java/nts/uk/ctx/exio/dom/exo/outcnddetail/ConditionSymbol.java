package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.i18n.I18NText;

/**
 * 
 * 
 * 条件記号
 *
 */

public enum ConditionSymbol {

	// 含む
	CONTAIN(0, I18NText.getText("CMF002_372"), " like "),

	// 範囲内
	BETWEEN(1, I18NText.getText("CMF002_373"), ""),

	// 同じ
	IS(2, I18NText.getText("CMF002_374"), " = "),
	
	// 同じでない
	IS_NOT(3, I18NText.getText("CMF002_375"), " <> "),
	
	// より大きい
	GREATER(4, I18NText.getText("CMF002_376"), " > "),
	
	//より小さい
	LESS(5, I18NText.getText("CMF002_377"), " < "),
	
	//以上
	GREATER_OR_EQUAL(6, I18NText.getText("CMF002_378"), " >= "),
	
	//以下
	LESS_OR_EQUAL(7, I18NText.getText("CMF002_379"), " <= "),
	
	//同じ(複数)
	IN(8, I18NText.getText("CMF002_380"), " in "),

	//同じでない(複数)
	NOT_IN(9, I18NText.getText("CMF002_381"), " not in ");
	

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	public final String operator;

	private ConditionSymbol(int value, String nameId, String operator) {
		this.value = value;
		this.nameId = nameId;
		this.operator = operator;
	}
}
