package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 
 * @author HieuLT
 *
 */
/** 個人勤務日区分別勤務種類 **/

@Getter
public class WorkTypeByIndividualWorkDay {


	//休日出勤時: 勤務種類コード
	private WorkTypeCode goToWorkOnHolidays;
	//休日時: 勤務種類コード
	private WorkTypeCode onHolidays;
	//出勤時: 勤務種類コード
	private WorkTypeCode whenCommuting;
	//Optional 法内休出時: 勤務種類コード
	private Optional<WorkTypeCode> duringLegalHolidays;
	//Optional 法外休出時: 勤務種類コード
	private Optional<WorkTypeCode> duringExorbitantHolidays;
	//Optinal 祝日休出時: 勤務種類コード
	private Optional<WorkTypeCode> holidays;
	
	public WorkTypeByIndividualWorkDay(WorkTypeCode goToWorkOnHolidays, WorkTypeCode onHolidays,
			WorkTypeCode whenCommuting, Optional<WorkTypeCode> duringLegalHolidays,
			Optional<WorkTypeCode> duringExorbitantHolidays, Optional<WorkTypeCode> holidays) {
		super();
		this.goToWorkOnHolidays = goToWorkOnHolidays;
		this.onHolidays = onHolidays;
		this.whenCommuting = whenCommuting;
		this.duringLegalHolidays = duringLegalHolidays;
		this.duringExorbitantHolidays = duringExorbitantHolidays;
		this.holidays = holidays;
	}
}
