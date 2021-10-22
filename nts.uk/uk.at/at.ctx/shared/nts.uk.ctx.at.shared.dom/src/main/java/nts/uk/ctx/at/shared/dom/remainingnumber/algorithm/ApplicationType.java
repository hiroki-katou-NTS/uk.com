package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import lombok.AllArgsConstructor;
/**
 * 申請種類
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ApplicationType {
	/** 残業申請*/
	OVER_TIME_APPLICATION(0),
	/** 休暇申請*/
	ABSENCE_APPLICATION(1),
	/** 勤務変更申請*/
	WORK_CHANGE_APPLICATION(2),
	/** 出張申請*/
	BUSINESS_TRIP_APPLICATION(3),
	/** 直行直帰申請*/
	GO_RETURN_DIRECTLY_APPLICATION(4),
	/** 休出時間申請*/
	BREAK_TIME_APPLICATION(6),
	/** 打刻申請*/
	STAMP_APPLICATION(7),
	/** 時間年休申請*/
	ANNUAL_HOLIDAY_APPLICATION(8),
	/** 遅刻早退取消申請*/
	EARLY_LEAVE_CANCEL_APPLICATION(9),
	/** 振休振出申請*/
	COMPLEMENT_LEAVE_APPLICATION(10),
	/** 打刻申請（NR形式）*/
	STAMP_NR_APPLICATION(11),
	/** 連続出張申請*/
	LONG_BUSINESS_TRIP_APPLICATION(12),
	/** 出張申請オフィスヘルパー*/
	BUSINESS_TRIP_APPLICATION_OFFICE_HELPER(13),
	/** ３６協定時間申請*/
	APPLICATION_36(14),
    /** 15: 任意項目申請*/
    OPTIONAL_ITEM_APPLICATION(15);
	 
	public final int value;	
	
}
