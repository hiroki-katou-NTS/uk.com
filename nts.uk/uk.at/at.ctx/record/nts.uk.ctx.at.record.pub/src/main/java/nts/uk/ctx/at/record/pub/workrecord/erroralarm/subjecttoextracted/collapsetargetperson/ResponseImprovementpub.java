package nts.uk.ctx.at.record.pub.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.List;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.AlarmCheckTargetConRc;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ResponseImprovementpub {
	List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,AlarmCheckTargetConRc alarmCheckTargetConRc);
}
