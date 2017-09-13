package nts.uk.ctx.at.record.dom.daily;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet.OverTimeWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 日別実績の残業時間
 * @author keisuke_hoshina
 *
 */
@Value
public class OverTimeWorkOfDaily {
	
	private List<OverTimeWorkFrameTimeSheet> overTimeWorkFrameTimeSheet;
	
	private OverTimeWorkOfDaily(List<OverTimeWorkFrameTimeSheet> frameTimeList) {
		this.overTimeWorkFrameTimeSheet = frameTimeList;
	}
	
	
	/**
	 * 
	 * @param overTimeHourSet
	 * @param attendanceLeave
	 * @param secondStartTime 2回目勤務の開始時間
	 * @return
	 */
	public static Optional<OverTimeWorkOfDaily> of(List<OverTimeHourSet> overTimeHourSet,AttendanceLeavingWork attendanceLeave,TimeWithDayAttr secondStartTime,int workNo) {
		
		List<OverTimeWorkFrameTimeSheet> frameTimeSheet = new ArrayList<OverTimeWorkFrameTimeSheet>();
		TimeSpanForCalc timeSheet = new TimeSpanForCalc(attendanceLeave.getAttendance().getActualEngrave().getTimesOfDay(),attendanceLeave.getLeaveWork().getActualEngrave().getTimesOfDay());
		TimeSpanForCalc timeSpan;
		
		for(OverTimeHourSet overTimeHour : overTimeHourSet) {
			if(startDicision(secondStartTime,workNo,attendanceLeave.getAttendance().getActualEngrave().getTimesOfDay())) {
				timeSpan = timeSheet.getDuplicatedWith(overTimeHour.getTimeSpan()).get();
				frameTimeSheet.add(new OverTimeWorkFrameTimeSheet(null, overTimeHour.getFrameNo()
										,new TimeSpanWithRounding(timeSpan.getStart(),timeSpan.getEnd(),overTimeHour.getTimeSpan().getRounding()), null));
			}

		}
		
		return Optional.of(new OverTimeWorkOfDaily(frameTimeSheet));
	}
	
	/**
	 * 勤務回数を見て開始時刻が正しいか判定する
	 * @param startTime
	 * @param workNo
	 * @param attendanceTime
	 * @return
	 */
	public static boolean startDicision(TimeWithDayAttr startTime, int workNo, TimeWithDayAttr attendanceTime) {
		if(workNo == 0) {
			return (startTime.v() < attendanceTime.v());
		}
		else{
			return (startTime.v() >= attendanceTime.v());
		}
	}

		
}
