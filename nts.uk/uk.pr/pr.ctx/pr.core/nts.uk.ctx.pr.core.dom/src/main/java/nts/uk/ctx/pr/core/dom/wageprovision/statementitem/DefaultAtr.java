package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq 既定区分 
 * 
 */
public enum DefaultAtr {

	USER_CREATE(0, "ユーザ作成"), 
	SYSTEM_DEFAULT(1, "システム既定");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DefaultAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
