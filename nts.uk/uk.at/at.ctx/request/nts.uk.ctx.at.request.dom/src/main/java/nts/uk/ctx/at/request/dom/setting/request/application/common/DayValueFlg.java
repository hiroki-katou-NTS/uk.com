package nts.uk.ctx.at.request.dom.setting.request.application.common;

/** 申請日初期値 */
public enum DayValueFlg {
	/* 0:空白 */
	BLANK(0),
	/* 1:今日 */
	TODAY(1);

	public final int value;

	DayValueFlg(int value) {
		this.value = value;
	}

}
