package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeHandler;
import nts.uk.ctx.at.record.dom.worktime.TimeLeaveChangeEvent;

/**　<<Event>> 実績の出退勤が変更された　*/
@Stateless
public class TimeLeaveChangeEventSubscriber implements DomainEventSubscriber<TimeLeaveChangeEvent> {

	@Inject
	private UpdateBreakTimeByTimeLeaveChangeHandler handler;

	@Override
	public Class<TimeLeaveChangeEvent> subscribedToEventType() {
		return TimeLeaveChangeEvent.class;
	}

	@Override
	public void handle(TimeLeaveChangeEvent domainEvent) {
		handler.handle(UpdateBreakTimeByTimeLeaveChangeCommand.builder().employeeId(domainEvent.getEmployeeId())
				.workingDate(domainEvent.getTargetDate()).timeLeave(domainEvent.getTimeLeave())
				.build());
	}

}
