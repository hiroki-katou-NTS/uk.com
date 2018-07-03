package nts.uk.ctx.exio.dom.exo.execlog;

/*
 * 定型区分
 */
public enum StandardClassification {
	USER(0, "ユーザ"), 
	STANDARD(1, "定型");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private StandardClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
