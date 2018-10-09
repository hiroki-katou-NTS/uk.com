package nts.uk.ctx.pr.core.dom.wageprovision.formula;
/**
 * 
 * @author HungTT - 入れ子利用区分
 *
 */

public enum NestedUseAtr {

	NOT_USABLE(0, "利用不可"),
	
	USABLE(1, "利用可能");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private NestedUseAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
