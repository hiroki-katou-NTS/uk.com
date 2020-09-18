package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;
/**
 * 連続出勤できる上限日数
 * @author lan_lt
 *
 */
@Value
public class MaxNumberDaysOfContinuousAttendance implements DomainValue{
	//日数
	private NumberOfConsecutiveDays numberOfDays;

}
