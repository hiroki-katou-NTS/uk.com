package nts.uk.ctx.at.schedule.dom.adapter;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

/**
 * RequestList No91 
 * Import Class
 * @author chinhbv
 *
 */
@Value
@Builder
public class ScTimeImport {

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
}
