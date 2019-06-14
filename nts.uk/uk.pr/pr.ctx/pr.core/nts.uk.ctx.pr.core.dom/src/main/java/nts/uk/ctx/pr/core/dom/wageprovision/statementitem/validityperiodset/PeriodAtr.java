package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

/**
 * 
 * @author thanh.tq 有効期間設定区分
 *
 */
public enum PeriodAtr {

	NOT_SETUP(0, "設定しない"), 
	SETUP(1, "設定する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PeriodAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
