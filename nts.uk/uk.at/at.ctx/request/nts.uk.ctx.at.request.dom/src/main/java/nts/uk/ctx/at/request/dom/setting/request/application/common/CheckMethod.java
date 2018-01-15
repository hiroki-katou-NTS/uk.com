package nts.uk.ctx.at.request.dom.setting.request.application.common;

public enum CheckMethod {
	/**0:時刻でチェック*/
	TIMECHECK(0),
	/**1:日数でチェック*/
	DAYCHECK(1);

	public final int value;

	CheckMethod(int value) {
		this.value = value;
	}

}
