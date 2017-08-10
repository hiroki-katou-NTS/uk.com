package nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive;

/** 申請コメント設定/太字 */
public enum FontWeight {
	/* 0:非太字 */
	NOTBOLD(0),
	/* 1:太字 */
	BOLD(1);

	public final int value;

	FontWeight(int value) {
		this.value = value;
	}
}
