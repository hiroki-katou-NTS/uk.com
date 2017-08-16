package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

public enum Change {

	/** 変更しない */
	NOTCHANGE(0),
	/** 変化する */
	CHANGE(1);

	public int value;

	Change(int type) {
		this.value = type;
	}
}
