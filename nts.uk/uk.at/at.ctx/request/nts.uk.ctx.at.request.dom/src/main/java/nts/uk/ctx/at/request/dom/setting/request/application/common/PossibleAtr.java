package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 受付可能か */
public enum PossibleAtr {
	/* 0:不可能 */
	IMPOSSIBLE(0),
	/* 1:可能 */
	POSSIBLE(1);

	public final int value;

	PossibleAtr(int value) {
		this.value = value;
	}

}
