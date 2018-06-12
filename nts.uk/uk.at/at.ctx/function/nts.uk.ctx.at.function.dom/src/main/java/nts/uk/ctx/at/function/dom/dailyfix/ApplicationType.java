package nts.uk.ctx.at.function.dom.dailyfix;

import lombok.AllArgsConstructor;
/**
 * 実績で使用する申請種類
 * @author yennth
 *
 */
@AllArgsConstructor
public enum ApplicationType {
	/** 残業申請（早出） */
	OVERTIME_APPLICATION(0),
	/** 残業申請（通常） */
	OVERTIME_APPLICATION_NORMAL(1),
	/** 残業申請（早出・通常） */
	OVERTIME_APPLICATION_EARLY_REGULAR(2),
	/** 休暇申請 */
	ABSENCE_APPLICATION(3),
	/** 勤務変更申請 */
	WORK_CHANGE_APPLICATION(4),
	/** 出張申請 */
	BUSINESS_TRIP_APPLICATION(5),
	/** 直行直帰申請 */
	GO_RETURN_DIRECTLY_APPLICATION(6),
	/** 休出時間申請 */
	LEAVE_TIME_APPLICATION(7),
	/** 打刻申請（外出許可） */
	STAMP_APPLICATION(8),
	/** 打刻申請（出退勤漏れ） */
	STAMP_NR_APPLICATION(9),
	/** 打刻申請（打刻取消） */
	REGISTER_TIME_CARD_DELETE(10),
	/** 打刻申請（レコーダイメージ） */
	REGISTER_TIME_CARD_IMAGE(11),
	/** 打刻申請（その他） */
	REGISTER_TIME_CARD_OTHER(12),
	/** 時間年休申請 */
	ANNUAL_HD_APPLICATION(13),
	/** 遅刻早退取消申請 */
	EARLY_LEAVE_CANCEL_APPLICATION(14),
	/** 振休振出申請 */
	COMPLEMENT_LEAVE_APPLICATION(15),
	/** 連続出張申請 */
	LONG_BUSINESS_TRIP_APPLICATION(16),
	/** ３６協定時間申請 */
	APPLICATION36(17);
	public final int value;
}
