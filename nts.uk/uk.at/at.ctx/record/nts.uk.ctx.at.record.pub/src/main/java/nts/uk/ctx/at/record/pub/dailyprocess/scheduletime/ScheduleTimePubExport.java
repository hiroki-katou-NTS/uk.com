package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

import java.util.Collections;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * RequestList No91 
 * Export class
 * 勤務予定の勤怠時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ScheduleTimePubExport {
	//社員ID
	String employeeid;
	
	//年月日
	GeneralDate ymd;
	
	//総労働時間
	AttendanceTime totalWorkTime;
	
	//所定移管
	AttendanceTime preTime;
	
	//実働時間
	AttendanceTime actualWorkTime;
	
	//平日時間
	AttendanceTime weekDayTime;
	
	//休憩時間
	AttendanceTime breakTime;
	
	//育児時間
	AttendanceTime childTime;
	
	//介護時間
	AttendanceTime careTime;
	
	//フレックス時間
	AttendanceTimeOfExistMinus flexTime;
	
	//人件費時間
	List<AttendanceTime> personalExpenceTime;

	private ScheduleTimePubExport(String employeeid, GeneralDate ymd, AttendanceTime totalWorkTime,
			AttendanceTime preTime, AttendanceTime actualWorkTime, AttendanceTime weekDayTime, AttendanceTime breakTime,
			AttendanceTime childTime, AttendanceTime careTime, AttendanceTimeOfExistMinus flexTime,
			List<AttendanceTime> personalExpenceTime) {
		super();
		this.employeeid = employeeid;
		this.ymd = ymd;
		this.totalWorkTime = totalWorkTime;
		this.preTime = preTime;
		this.actualWorkTime = actualWorkTime;
		this.weekDayTime = weekDayTime;
		this.breakTime = breakTime;
		this.childTime = childTime;
		this.careTime = careTime;
		this.flexTime = flexTime;
		this.personalExpenceTime = personalExpenceTime;
	}
	
	public static ScheduleTimePubExport of(String employeeid, GeneralDate ymd, AttendanceTime totalWorkTime,
			AttendanceTime preTime, AttendanceTime actualWorkTime, AttendanceTime weekDayTime, AttendanceTime breakTime,
			AttendanceTime childTime, AttendanceTime careTime ,
			AttendanceTimeOfExistMinus flexTime,List<AttendanceTime> personalExpenceTime) {
		return new ScheduleTimePubExport( employeeid,  ymd,  totalWorkTime,
				 preTime,  actualWorkTime,  weekDayTime,  breakTime,
				 childTime, careTime , flexTime,personalExpenceTime);
	}
	
	public static ScheduleTimePubExport empty() {
		return new ScheduleTimePubExport("", 
				 GeneralDate.today(), 
				 new AttendanceTime(0), 
				 new AttendanceTime(0), 
				 new AttendanceTime(0), 
				 new AttendanceTime(0), 
				 new AttendanceTime(0),
				 new AttendanceTime(0),
				 new AttendanceTime(0), 
				 new AttendanceTimeOfExistMinus(0),
				 Collections.emptyList());
	}
}