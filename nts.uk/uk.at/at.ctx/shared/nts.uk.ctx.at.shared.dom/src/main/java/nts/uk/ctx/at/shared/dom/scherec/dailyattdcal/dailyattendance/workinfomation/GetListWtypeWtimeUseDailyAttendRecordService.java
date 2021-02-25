package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 日別勤怠の実績で利用する勤務種類と就業時間帯のリストを取得する
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.勤務情報
 * @author HieuLt
 *
 */
public class GetListWtypeWtimeUseDailyAttendRecordService {

	public static WorkTypeWorkTimeUseDailyAttendanceRecord getdata(List<WorkInfoOfDailyAttendance> lstWorkInfoOfDailyAttendance){ 
		/*$勤務種類リスト = 日別勤怠の勤務情報リスト:																						
		map $.勤務実績の勤務情報種類コード																							
		distinct*/
		List<WorkTypeCode> lstWorkTypeCode = lstWorkInfoOfDailyAttendance.stream().map(c -> c.getRecordInfo().getWorkTypeCode()).distinct().collect(Collectors.toList());
		/*$就業時間帯リスト = 日別勤怠の勤務情報リスト:																					
		filter $.勤務実績の就業時間帯コード.isPresent()																				
		map $.勤務実績の就業時間帯コード																							
		distinct*/
		List<WorkTimeCode> lstWorkTimeCode = lstWorkInfoOfDailyAttendance.stream().filter(i->i.getRecordInfo().getWorkTimeCode()!=null).map(c -> c.getRecordInfo().getWorkTimeCode()).distinct().collect(Collectors.toList());
		WorkTypeWorkTimeUseDailyAttendanceRecord result = new WorkTypeWorkTimeUseDailyAttendanceRecord(lstWorkTypeCode, lstWorkTimeCode);
		//	return 日別勤怠の実績で利用する勤務種類と就業時間帯( $勤務種類リスト, $就業時間帯リスト )
		return result;
	}
}
