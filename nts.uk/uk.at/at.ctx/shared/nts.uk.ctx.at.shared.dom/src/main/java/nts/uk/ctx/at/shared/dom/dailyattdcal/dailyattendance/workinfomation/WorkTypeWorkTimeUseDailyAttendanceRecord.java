package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * Temporary			
 * 日別勤怠の実績で利用する勤務種類と就業時間帯	
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class WorkTypeWorkTimeUseDailyAttendanceRecord {
	/** 勤務種類リスト **/
	private final List<WorkTypeCode> lstWorkTypeCode;
	/** 就業時間帯リスト **/
	private final List<WorkTimeCode> lstWorkTimeCode;
	
	//[C-1] 日別勤怠の実績で利用する勤務種類と就業時間帯( List<勤務種類コード>, List<就業時間帯コード> )
}
