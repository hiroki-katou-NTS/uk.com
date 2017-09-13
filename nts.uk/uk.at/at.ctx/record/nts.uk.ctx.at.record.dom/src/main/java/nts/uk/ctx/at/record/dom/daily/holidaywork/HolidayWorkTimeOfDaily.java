package nts.uk.ctx.at.record.dom.daily.holidaywork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.AttendanceLeavingWork;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.FixRestSetting;

/**
 * 日別実績の休出時間
 * @author keisuke_hoshina
 *
 */
@Value
public class HolidayWorkTimeOfDaily {
	private List<HolidayWorkFrameTimeSheet> holidyWorkFrameTimeSheet;
	private List<HolidayWorkFrameTime> holidayWorkFrameTime;
	
	private HolidayWorkTimeOfDaily(List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheet,List<HolidayWorkFrameTime> holidayWorkFrameTime) {
		this.holidyWorkFrameTimeSheet = holidayWorkFrameTimeSheet;
		this.holidayWorkFrameTime = holidayWorkFrameTime;
	}
	
	/**
	 * 休出時間帯の作成と時間計算
	 * @param attendanceTime
	 * @param oneDayRange　１日の計算範囲.１日の範囲
	 * @return
	 */
	public static HolidayWorkTimeOfDaily getHolidayWorkTimeOfDaily(List<FixRestSetting> fixTimeSet ,AttendanceLeavingWork attendanceLeave) {
		
		List<HolidayWorkFrameTimeSheet> holidayWorkTimeSheetList = new ArrayList<HolidayWorkFrameTimeSheet>();
		List<HolidayWorkFrameTime> holidayWorkFrameTimeList = new ArrayList<HolidayWorkFrameTime>();
		Optional<TimeSpanForCalc> timeSpan;
		TimeSpanForCalc attendanceLeaveSheet = new TimeSpanForCalc(attendanceLeave.getAttendance().getActualEngrave().getTimesOfDay(),attendanceLeave.getLeaveWork().getActualEngrave().getTimesOfDay());
		for(FixRestSetting holidayTimeSheet : fixTimeSet) {
			/*計算範囲の判断*/
			timeSpan = attendanceLeaveSheet.getDuplicatedWith(holidayTimeSheet.getHours().getSpan());
			/*控除時間帯を分割*/
			/*遅刻早退処理*/
			if(/*遅刻早退どっちの設定を見てもOKなので、どちらかの休出の場合でも計算するを見る*/) {
				
			}
			/*休出時間帯の作成*/
			
			holidayWorkTimeSheetList.add(new HolidayWorkFrameTimeSheet(timeSpan.get()));
		}
		
		return new HolidayWorkTimeOfDaily(holidayWorkTimeSheetList,holidayWorkFrameTimeList);
	}
}
