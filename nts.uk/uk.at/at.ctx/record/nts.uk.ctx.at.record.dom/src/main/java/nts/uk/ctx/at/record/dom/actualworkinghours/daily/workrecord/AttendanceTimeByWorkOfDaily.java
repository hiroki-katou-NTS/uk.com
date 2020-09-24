package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/** 日別実績の作業別勤怠時間 */
@Getter
@NoArgsConstructor
public class AttendanceTimeByWorkOfDaily {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日*/
	private GeneralDate ymd;
	
	/** 作業一覧: 日別実績の作業時間*/
	private List<WorkTimeOfDaily> workTimes;

	public AttendanceTimeByWorkOfDaily(String employeeId, GeneralDate ymd, List<WorkTimeOfDaily> workTimes) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workTimes = workTimes;
	}
	
	
}
