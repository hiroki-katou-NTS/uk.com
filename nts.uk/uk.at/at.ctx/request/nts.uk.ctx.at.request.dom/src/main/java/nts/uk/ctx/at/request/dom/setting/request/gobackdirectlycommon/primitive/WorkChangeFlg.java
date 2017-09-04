package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;

public enum WorkChangeFlg {
	/**
	 * 0:申請時に決める（初期選択：勤務を変更しない）
	 */
	DECIDENOTCHANGE(0),
	/**
	 * 1:申請時に決める（初期選択：勤務を変更する）
	 */
	DECIDECHANGE(1),
	/**
	 * 2:変更しない
	 */
	NOTCHANGE(2),
	/**
	 * 3:変更する
	 */
	CHANGE(3);

	public final int value;

	WorkChangeFlg(int value) {
		this.value = value;
	}
}
