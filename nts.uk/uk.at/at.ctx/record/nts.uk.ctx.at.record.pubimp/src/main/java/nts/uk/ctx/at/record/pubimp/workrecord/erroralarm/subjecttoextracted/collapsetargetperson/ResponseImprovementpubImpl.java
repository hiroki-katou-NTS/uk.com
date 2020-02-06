package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.AlarmCheckTargetConRc;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.ResponseImprovementService;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.ResponseImprovementpub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class ResponseImprovementpubImpl implements ResponseImprovementpub {

	@Inject
	private ResponseImprovementService responseImprovementService;
	
	@Override
	public List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,
			AlarmCheckTargetConRc alarmCheckTargetConRc) {
		List<String> listEmployeeID = responseImprovementService.reduceTargetResponseImprovement(employeeIds, period, alarmCheckTargetConRc);
		return listEmployeeID;
	}

}
