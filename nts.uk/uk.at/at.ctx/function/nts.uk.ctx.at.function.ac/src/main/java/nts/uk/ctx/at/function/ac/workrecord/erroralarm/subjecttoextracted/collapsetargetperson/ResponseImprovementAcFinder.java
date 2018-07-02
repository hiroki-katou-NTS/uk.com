package nts.uk.ctx.at.function.ac.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.ResponseImprovementAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.AlarmCheckTargetConRc;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.subjecttoextracted.collapsetargetperson.ResponseImprovementpub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class ResponseImprovementAcFinder implements ResponseImprovementAdapter {

	@Inject
	private ResponseImprovementpub responseImprovementpub;
	
	@Override
	public List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period,
			AlarmCheckTargetCondition alarmCheckTargetConRc) {
		return responseImprovementpub.reduceTargetResponseImprovement(employeeIds, period, convertToExport(alarmCheckTargetConRc)); 
	}
	
	private AlarmCheckTargetConRc convertToExport(AlarmCheckTargetCondition dto) {
		return new AlarmCheckTargetConRc(
				dto.getId(),
				dto.isFilterByBusinessType(),
				dto.isFilterByJobTitle(),
				dto.isFilterByEmployment(),
				dto.isFilterByClassification(),
				dto.getLstBusinessTypeCode(),
				dto.getLstJobTitleId(),
				dto.getLstEmploymentCode(),
				dto.getLstClassificationCode()
				);
	}

}
