package nts.uk.ctx.at.record.dom.daily;

import java.util.List;

import lombok.Value;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 日別実績の出退勤
 * @author keisuke_hoshina
 *
 */
@Value
public class DailyOfAttendanceLeavingWork {

	private String syainID;
	private WorkTimes workTimes;
	private List<AttendanceLeavingWorkTime> attendanceLeavingWorkTime;
}
