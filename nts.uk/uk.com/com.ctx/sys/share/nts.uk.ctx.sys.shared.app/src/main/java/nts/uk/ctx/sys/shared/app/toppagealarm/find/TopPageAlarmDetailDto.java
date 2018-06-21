package nts.uk.ctx.sys.shared.app.toppagealarm.find;

import nts.arc.time.GeneralDateTime;

public class TopPageAlarmDetailDto {
	/** 実行ログID */
	private String executionLogId;
	/** 管理社員ID */
	private String managerId;
	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;
	/** 連番 */
	private int serialNo;
	/** エラーメッセージ */
	private String errorMessage ;
	/** 対象社員ID */
	private String targerEmployee ;
}
