package nts.uk.screen.at.app.dailyperformance.correction.dto;

public enum ApplicationType {
	/** 残業申請（早出） */
	OVER_TIME_APPLICATION(0, "残業申請（早出）"),
	
	/** 残業申請（通常） */
	OVERTIME_APPLICATION_NORMAL(1, "残業申請（通常）"),
	
	/** 残業申請（早出・通常） */
	OVERTIME_APPLICATION_EARLY_REGULAR(2, "残業申請（早出・通常）"),	
	
	/** 休暇申請 */
	ABSENCE_APPLICATION(3, "休暇申請"),
	
	/** 勤務変更申請 */
	WORK_CHANGE_APPLICATION(4, "勤務変更申請"),	
	
	/** 出張申請 */
	BUSINESS_TRIP_APPLICATION(5, "出張申請"),
	
	/** 直行直帰申請 */
	GO_RETURN_DIRECTLY_APPLICATION(6, "直行直帰申請"),
	
	/** 休出時間申請 */
	BREAK_TIME_APPLICATION(7, "休出時間申請"),
	
	/** 打刻申請（外出許可） */
	STAMP_APPLICATION(8, "打刻申請（外出許可）"),
	
	/** 打刻申請（出退勤漏れ） */
	STAMP_NR_APPLICATION(9, "打刻申請（出退勤漏れ）"),
	
	/** 打刻申請（打刻取消） */
	REGISTER_TIME_CARD_DELETE(10, "打刻申請（打刻取消）"),
	
	/** 打刻申請（レコーダイメージ） */
	REGISTER_TIME_CARD_IMAGE(11, "打刻申請（レコーダイメージ）"),
	
	/** 打刻申請（その他） */
	REGISTER_TIME_CARD_OTHER(12, "打刻申請（その他）"),
	
	/** 時間年休申請 */
	ANNUAL_HD_APPLICATION(13, "時間年休申請"),
	
	/** 遅刻早退取消申請 */
	EARLY_LEAVE_CANCEL_APPLICATION(14, "遅刻早退取消申請"),
	
	/** 振休振出申請 */
	COMPLEMENT_LEAVE_APPLICATION(15, "振休振出申請"),
	
	/** 連続出張申請 */
	LONG_BUSINESS_TRIP_APPLICATION(16, "連続出張申請"),
	
	/** ３６協定時間申請 */
	APPLICATION36(17, "３６協定時間申請");

	public int value;

	public String nameId;

	ApplicationType(int type, String nameId) {
		this.value = type;
		this.nameId = nameId;
	}
}
