package nts.uk.ctx.at.record.dom.service;

//import java.sql.Date;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeOffRemainErrorInputParam {
	/**
	 * 会社ID
	 */
	private String cid;
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 集計期間
	 */
	private DatePeriod aggDate;
	/**
	 * 対象期間
	 */
	private DatePeriod objDate;
	/**
	 * 時間代休利用: True:利用, False:　未使用 
	 */
	private boolean useDayoff;
	/**
	 * 日別実績の勤怠時間
	 */
	private List<AttendanceTimeOfDailyPerformance> lstAttendanceTimeData;
	/**
	 * 日別実績の勤務情報
	 */
	private List<WorkInfoOfDailyPerformance> lstWorkInfor;
	/**
	 * 月別実績（Work）
	 */
	private Optional<AttendanceTimeOfMonthly> optMonthlyData;
	
}
