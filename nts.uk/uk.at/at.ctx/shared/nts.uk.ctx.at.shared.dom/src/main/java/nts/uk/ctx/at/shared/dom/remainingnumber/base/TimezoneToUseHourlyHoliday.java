package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

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
	
	/**
	 * 外出理由を指定
	 * @param goOutReason 外出理由
	 * @return
	 */
	public static TimezoneToUseHourlyHoliday getDuringWorking (GoingOutReason goOutReason) {
		switch(goOutReason) {
		case PRIVATE:
			return TimezoneToUseHourlyHoliday.GOINGOUT_PRIVATE;
		case UNION:
			return TimezoneToUseHourlyHoliday.GOINGOUT_UNION;
		default:
			throw new RuntimeException("時間休暇は私用、組合しかない。");
		}
	}
}
