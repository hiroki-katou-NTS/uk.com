package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * AR: 会社の連続出勤できる上限日数
 * @author lan_lt
 * 	
 *
 */
@AllArgsConstructor
public class MaxNumberDaysOfContinuousAttendanceCompany implements DomainAggregate{
	//会社ID
	@Getter
	private final String companyId;
	
	//日数
	@Getter
	private MaxNumberDaysOfContinuousAttendance numberOfDays;
}
