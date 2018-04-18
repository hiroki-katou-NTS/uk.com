package nts.uk.ctx.at.record.app.command.dailyperform.workinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeHandler;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoChangeEvent;

/**　<<Event>> 実績の就業時間帯が変更された　*/
@Stateless
public class WorkInfoChangeEventSubscriber implements DomainEventSubscriber<WorkInfoChangeEvent> {

	@Inject
	private UpdateBreakTimeByTimeLeaveChangeHandler handler;

	@Override
	public Class<WorkInfoChangeEvent> subscribedToEventType() {
		return WorkInfoChangeEvent.class;
	}

	@Override
	public void handle(WorkInfoChangeEvent domainEvent) {
		handler.handle(UpdateBreakTimeByTimeLeaveChangeCommand.builder().employeeId(domainEvent.getEmployeeId())
				.workingDate(domainEvent.getTargetDate()).newWorkTimeCode(domainEvent.getNewWorkTimeCode())
				.build());
	}

}
