package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

/**
 * マスタ数値区分
 */
public enum MasterNumericAtr {

	MASTER_ITEM(0, "マスタ項目"), 
	NUMERIC_ITEM(1, "数値項目");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private MasterNumericAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
