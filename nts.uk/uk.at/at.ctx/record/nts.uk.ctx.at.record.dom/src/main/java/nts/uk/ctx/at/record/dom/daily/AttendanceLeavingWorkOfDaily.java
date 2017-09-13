package nts.uk.ctx.at.record.dom.daily;

import java.util.List;

import lombok.Value;

/**
 * 日別実績の出退勤
 * @author keisuke_hoshina
 *
 */
@Value
public class AttendanceLeavingWorkOfDaily {

	private String syainID;
	private WorkTimes workTimes;
	private List<AttendanceLeavingWork> attendanceLeavingWorkTime;
}
