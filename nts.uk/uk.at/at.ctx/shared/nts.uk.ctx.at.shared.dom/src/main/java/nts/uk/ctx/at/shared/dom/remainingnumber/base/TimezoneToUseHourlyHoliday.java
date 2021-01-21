package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;

/**
 * 時間休暇種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.時間休暇種類
 * @author kumiko_otake
 *
 */
@AllArgsConstructor
public enum TimezoneToUseHourlyHoliday {

	/** 勤務NO(1) 出勤前 **/
	WORK_NO1_BEFORE( 0 ),
	/** 勤務NO(1) 退勤後 **/
	WORK_NO1_AFTER( 1 ),

	/** 勤務NO(2) 出勤前 **/
	WORK_NO2_BEFORE( 2 ),
	/** 勤務NO(2) 退勤後 **/
	WORK_NO2_AFTER( 3 ),

	/** 外出 私用外出 **/
	GOINGOUT_PRIVATE( 4 ),
	/** 外出 組合外出 **/
	GOINGOUT_UNION( 5 ),
	;


	public final Integer value;


	/**
	 * 出勤の勤務NOを指定
	 * @param workNo 勤務NO
	 * @return
	 */
	public static TimezoneToUseHourlyHoliday getBeforeWorking(WorkNo workNo) {
		return (workNo.v() == 2) ? WORK_NO2_BEFORE : WORK_NO1_BEFORE;
	}

	/**
	 * 退勤の勤務NOを指定
	 * @param workNo 勤務NO
	 * @return
	 */
	public static TimezoneToUseHourlyHoliday getAfterWorking(WorkNo workNo) {
		return (workNo.v() == 2) ? WORK_NO2_AFTER : WORK_NO1_AFTER;
	}
}
