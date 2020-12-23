package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告用退勤系範囲
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareLeaveRange {

	/** 退勤 */
	private Optional<TimeWithDayAttr> leave;
	/** 残業開始 */
	private Optional<TimeWithDayAttr> overtimeStart;
	/** 残業終了 */
	private Optional<TimeWithDayAttr> overtimeEnd;
	/** 残業深夜開始 */
	private Optional<TimeWithDayAttr> overtimeMnStart;
	/** 残業時間 */
	private AttendanceTime overtimeTime;
	/** 残業深夜時間 */
	private AttendanceTime overtimeMnTime;
	/** 休出開始 */
	private Optional<TimeWithDayAttr> holidayWorkStart;
	/** 休出終了 */
	private Optional<TimeWithDayAttr> holidayWorkEnd;
	/** 休出深夜開始 */
	private Optional<TimeWithDayAttr> holidayWorkMnStart;
	/** 休出時間 */
	private AttendanceTime holidayWorkTime;
	/** 休出深夜時間 */
	private AttendanceTime holidayWorkMnTime;
	
	public DeclareLeaveRange(){
		this.leave = Optional.empty();
		this.overtimeStart = Optional.empty();
		this.overtimeEnd = Optional.empty();
		this.overtimeMnStart = Optional.empty();
		this.overtimeTime = new AttendanceTime(0);
		this.overtimeMnTime = new AttendanceTime(0);
		this.holidayWorkStart = Optional.empty();
		this.holidayWorkEnd = Optional.empty();
		this.holidayWorkMnStart = Optional.empty();
		this.holidayWorkTime = new AttendanceTime(0);
		this.holidayWorkMnTime = new AttendanceTime(0);
	}
}
