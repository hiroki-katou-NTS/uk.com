package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq 社会保険対象変更区分
 * 
 */
public enum SocialInsuaEditableAtr {

	NONE_EDITABLE(0, "変更できない"), 
	EDITABLE(1, "変更できる");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SocialInsuaEditableAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
