package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq 値の属性
 * 
 */
public enum ValueAtr {

	MONEY_WITHOUT_DECIMAL(0, "明細金額型"), 
	MONEY_WITH_DECIMAL(1, "明細金額（小数あり）型"), 
	TIME(2,"時間値"), 
	COUNT(3, "回数値");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ValueAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
