package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.arc.time.calendar.period.DatePeriod;

public interface ResponseImprovementAdapter {
	List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,AlarmCheckTargetCondition alarmCheckTargetConRc);
}
