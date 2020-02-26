package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

/**
 * 
 * @author thanh.tq サイクル設定区分
 *
 */
public enum CycleSettingAtr {

	NOT_USE(0, "しない"), 
	USE(1, "する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private CycleSettingAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
