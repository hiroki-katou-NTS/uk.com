package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author HieuLT
 * 個人勤務日区分別勤務種類
 */

@Getter
public class WorkTypeByIndividualWorkDay extends DomainObject {

	//出勤時: 勤務種類コード
	private WorkTypeCode weekdayTimeWTypeCode; // WhenCommuting
	//休日出勤時: 勤務種類コード
	private WorkTypeCode holidayWorkWTypeCode; // goToWorkOnHolidays
	//休日時: 勤務種類コード
	private WorkTypeCode holidayTimeWTypeCode; // onHolidays
	//Optional 法内休出時: 勤務種類コード - InLawBreakTimeWorkTypeCode
	private Optional<WorkTypeCode> inLawBreakTimeWTypeCode;
	//Optional 法外休出時: 勤務種類コード- OutsideLawBreakTimeWorkTypeCode
	private Optional<WorkTypeCode> outsideLawBreakTimeWTypeCode;
	//Optinal 祝日休出時: 勤務種類コード - HolidayAttendanceTimeWorkTypeCode
	private Optional<WorkTypeCode> holidayAttendanceTimeWTypeCode;
	
	public WorkTypeByIndividualWorkDay(
			WorkTypeCode weekdayTimeWTypeCode, 
			WorkTypeCode holidayWorkWTypeCode,
			WorkTypeCode holidayTimeWTypeCode, 
			Optional<WorkTypeCode> inLawBreakTimeWTypeCode,
			Optional<WorkTypeCode> outsideLawBreakTimeWTypeCode, 
			Optional<WorkTypeCode> holidayAttendanceTimeWTypeCode) {
		super();
		this.weekdayTimeWTypeCode = weekdayTimeWTypeCode;
		this.holidayWorkWTypeCode = holidayWorkWTypeCode;
		this.holidayTimeWTypeCode = holidayTimeWTypeCode;
		this.inLawBreakTimeWTypeCode = inLawBreakTimeWTypeCode;
		this.outsideLawBreakTimeWTypeCode = outsideLawBreakTimeWTypeCode;
		this.holidayAttendanceTimeWTypeCode = holidayAttendanceTimeWTypeCode;
	}
}
