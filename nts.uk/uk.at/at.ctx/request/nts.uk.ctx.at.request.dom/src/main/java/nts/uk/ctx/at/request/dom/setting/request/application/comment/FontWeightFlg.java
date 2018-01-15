package nts.uk.ctx.at.request.dom.setting.request.application.comment;

/** 申請コメント設定/太字 */
public enum FontWeightFlg {
	/* 0:非太字 */
	NOTBOLD(0),
	/* 1:太字 */
	BOLD(1);

	public final int value;

	FontWeightFlg(int value) {
		this.value = value;
	}
}
