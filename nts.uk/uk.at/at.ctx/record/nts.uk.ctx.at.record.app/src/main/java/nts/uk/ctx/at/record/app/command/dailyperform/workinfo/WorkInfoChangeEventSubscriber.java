package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.dom.worktime.TimeLeaveUpdateEvent;
import nts.uk.ctx.at.shared.dom.workinformation.WorkInfoChangeEvent;

/** <<Event>> 実績の就業時間帯が変更された */
/** <<Event>> 実績の勤務種類が変更された */
@Stateless
public class WorkInfoChangeEventSubscriber implements DomainEventSubscriber<WorkInfoChangeEvent> {

//	@Inject
//	private UpdateBreakTimeByTimeLeaveChangeHandler handler;

	@Override
	public Class<WorkInfoChangeEvent> subscribedToEventType() {
		return WorkInfoChangeEvent.class;
	}

	@Override
	public void handle(WorkInfoChangeEvent domainEvent) {
//		handler.handle(UpdateBreakTimeByTimeLeaveChangeCommand.builder().employeeId(domainEvent.getEmployeeId())
//				.workingDate(domainEvent.getTargetDate()).newWorkTimeCode(domainEvent.getNewWorkTimeCode()).build());
		
		TimeLeaveUpdateEvent.builder().employeeId(domainEvent.getEmployeeId()).targetDate(domainEvent.getTargetDate())
				.newWorkTimeCode(Optional.ofNullable(domainEvent.getNewWorkTimeCode()))
				.newWorkTypeCode(domainEvent.getNewWorkTypeCode()).build().toBePublished();
	}

}
