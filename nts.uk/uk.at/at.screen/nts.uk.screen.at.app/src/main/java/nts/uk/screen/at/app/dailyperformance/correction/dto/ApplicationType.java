package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameQueryImport;

public enum ApplicationType {
	/** 残業申請（早出） */
	OVER_TIME_APPLICATION(0, "残業申請（早出）","KAF005","A","overworkatr=0"),
	
	/** 残業申請（通常） */
	OVERTIME_APPLICATION_NORMAL(1, "残業申請（通常）","KAF005","A","overworkatr=1"),
	
	/** 残業申請（早出・通常） */
	OVERTIME_APPLICATION_EARLY_REGULAR(2, "残業申請（早出・通常）","KAF005","A","overworkatr=2"),
	
	/** 休暇申請 */
	ABSENCE_APPLICATION(3, "休暇申請","KAF006","A",null),
	
	/** 勤務変更申請 */
	WORK_CHANGE_APPLICATION(4, "勤務変更申請","KAF007","A",null),
	
	/** 出張申請 */
	BUSINESS_TRIP_APPLICATION(5, "出張申請","KAF008","A",null),
	
	/** 直行直帰申請 */
	GO_RETURN_DIRECTLY_APPLICATION(6, "直行直帰申請","KAF009","A",null),
	
	/** 休出時間申請 */
	BREAK_TIME_APPLICATION(7, "休出時間申請","KAF010","A",null),
	
	/** 打刻申請（外出許可） */
	STAMP_APPLICATION(8, "打刻申請（外出許可）","KAF002","A",null),
	
	/** 打刻申請（出退勤漏れ） */
	STAMP_NR_APPLICATION(9, "打刻申請（出退勤漏れ）","KAF002","A",null),
	
	/** 打刻申請（打刻取消） */
	REGISTER_TIME_CARD_DELETE(10, "打刻申請（打刻取消）","KAF002","A",null),
	
	/** 打刻申請（レコーダイメージ） */
	REGISTER_TIME_CARD_IMAGE(11, "打刻申請（レコーダイメージ）","KAF002","B",null),
	
	/** 打刻申請（その他） */
	REGISTER_TIME_CARD_OTHER(12, "打刻申請（その他）","KAF002","A",null),
	
	/** 時間年休申請 */
	//ANNUAL_HD_APPLICATION(13, "時間年休申請",null,null,null),
	
	/** 遅刻早退取消申請 */
	EARLY_LEAVE_CANCEL_APPLICATION(13, "遅刻早退取消申請","KAF004","A",null),
	
	/** 振休振出申請 */
	COMPLEMENT_LEAVE_APPLICATION(14, "振休振出申請","KAF011","A",null),
	
	/** 連続出張申請 */
	LONG_BUSINESS_TRIP_APPLICATION(16, "連続出張申請",null,null,null),
	
	/** ３６協定時間申請 */
	APPLICATION36(17, "３６協定時間申請",null,null,null);

	public int value;

	public String nameId;
	
	public String programId;
	
	public String screenId;
	
	public String queryString;

	ApplicationType(int type, String nameId, String programId, String screenId, String queryString) {
		this.value = type;
		this.nameId = nameId;
		this.programId = programId;
		this.screenId = screenId;
		this.queryString = queryString;
	}
	
	public StandardMenuNameQueryImport toStandardMenuNameQuery() {
		return new StandardMenuNameQueryImport(this.programId, this.screenId, Optional.ofNullable(this.queryString));
	}
}
