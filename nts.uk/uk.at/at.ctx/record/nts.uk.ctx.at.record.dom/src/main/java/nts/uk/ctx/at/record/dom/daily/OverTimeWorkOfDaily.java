package nts.uk.ctx.at.record.dom.daily;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet.OverTimeWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.OverTimeHourSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;

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
	
	
	public static OverTimeWorkOfDaily of(List<OverTimeHourSet> overTimeHourSet,AttendanceLeavingWork attendanceLeave) {
		
		List<OverTimeWorkFrameTimeSheet> frameTimeSheet = new ArrayList<OverTimeWorkFrameTimeSheet>();
		TimeSpanForCalc timeSheet = new TimeSpanForCalc(attendanceLeave.getAttendance().getActualEngrave().getTimesOfDay(),attendanceLeave.getLeaveWork().getActualEngrave().getTimesOfDay());
		TimeSpanForCalc timeSpan;
		
		for(OverTimeHourSet overTimeHour : overTimeHourSet) {
			timeSpan = timeSheet.getDuplicatedWith(overTimeHour.getTimeSpan()).get();
			frameTimeSheet.add(new OverTimeWorkFrameTimeSheet(null, overTimeHour.getFrameNo()
									,new TimeSpanWithRounding(timeSpan.getStart(),timeSpan.getEnd(),overTimeHour.getTimeSpan().getRounding()), null));
		}
		
		return new OverTimeWorkOfDaily(frameTimeSheet);
	}

		
}
