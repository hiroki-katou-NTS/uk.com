package nts.uk.ctx.at.request.dom.setting.request.application.common;

/**
 * 承認ルートの基準日
 * 
 * @author ducpm
 *
 */
public enum BaseDateFlg {
	/** 0:システム日付時点 */
	SYSTEM_DATE(0),
	/** 1:申請対象日時点 */
	APP_DATE(1);

	public final int value;

	BaseDateFlg(int value) {
		this.value = value;
	}

}
