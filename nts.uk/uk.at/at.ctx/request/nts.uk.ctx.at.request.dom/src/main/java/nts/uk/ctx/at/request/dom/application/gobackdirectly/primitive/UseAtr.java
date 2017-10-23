package nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive;

/** するしない区分 */
public enum UseAtr {
	/* しない */
	NOTUSE(0),
	/* する */
	USE(1);

	public final int value;

	UseAtr(int value) {
		this.value = value;
	}

}
