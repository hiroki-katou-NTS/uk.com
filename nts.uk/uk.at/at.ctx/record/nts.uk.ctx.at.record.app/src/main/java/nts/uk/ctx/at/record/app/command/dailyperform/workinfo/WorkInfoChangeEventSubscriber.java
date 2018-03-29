package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoChangeEvent;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class WorkInfoChangeEventSubscriber implements DomainEventSubscriber<WorkInfoChangeEvent> {
	
	@Inject
	private WorkInformationRepository repo;

	@Override
	public Class<WorkInfoChangeEvent> subscribedToEventType() {
		return WorkInfoChangeEvent.class;
	}

	@Override
	public void handle(WorkInfoChangeEvent domainEvent) {
		Optional<WorkInfoOfDailyPerformance> oldWorkInfo = repo.find(domainEvent.getEmployeeId(), domainEvent.getTargetDate());
		oldWorkInfo.ifPresent(wi -> {
			WorkTimeCode workTime = domainEvent.getNewWorkTimeCode() == null ?
					wi.getRecordInfo().getWorkTimeCode() : domainEvent.getNewWorkTimeCode();
			WorkTypeCode workType = domainEvent.getNewWorkTypeCode() == null ? 
					wi.getRecordInfo().getWorkTypeCode() : domainEvent.getNewWorkTypeCode();
			wi.setRecordInfo(new WorkInformation(workTime, workType));
			repo.updateByKey(wi);
		});
	}

}

