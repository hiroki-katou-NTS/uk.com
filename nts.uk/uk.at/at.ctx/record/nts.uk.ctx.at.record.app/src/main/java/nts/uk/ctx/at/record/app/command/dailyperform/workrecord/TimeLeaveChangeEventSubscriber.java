package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.breaktime.UpdateBreakTimeByTimeLeaveChangeHandler;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeaveChangeEvent;

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
		UpdateBreakTimeByTimeLeaveChangeCommand command = (UpdateBreakTimeByTimeLeaveChangeCommand) UpdateBreakTimeByTimeLeaveChangeCommand
																											.builder().timeLeave(domainEvent.getTimeLeave())
																											.employeeId(domainEvent.getEmployeeId())
																											.targetDate(domainEvent.getTargetDate())
																											.build();
		handler.handle(command);
	}

}
