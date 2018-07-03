package nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 対象者をしぼり込む(レスポンス改善)
 * @author tutk
 *
 */
public interface ResponseImprovementService {
	
	List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,AlarmCheckTargetConRc alarmCheckTargetConRc);
	
}
