package nts.uk.ctx.at.record.app.command.dailyperform.workrecord;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.record.dom.worktime.TimeLeaveUpdateEvent;

/** Event：出退勤時刻を補正する */
@Stateless
public class TimeLeaveUpdateRequestSubscriber implements DomainEventSubscriber<TimeLeaveUpdateEvent> {

	@Inject
	private TimeLeaveUpdateByWorkInfoChangeHandler commandHandler;

	@Override
	public Class<TimeLeaveUpdateEvent> subscribedToEventType() {
		return TimeLeaveUpdateEvent.class;
	}

	@Override
	public void handle(TimeLeaveUpdateEvent domainEvent) {
		TimeLeaveUpdateByWorkInfoChangeCommand command = (TimeLeaveUpdateByWorkInfoChangeCommand) TimeLeaveUpdateByWorkInfoChangeCommand
																									.builder()
																									.newWorkTimeCode(domainEvent
																									.getNewWorkTimeCode().orElse(null))
																									.newWorkTypeCode(domainEvent.getNewWorkTypeCode())
																									.employeeId(domainEvent.getEmployeeId())
																									.targetDate(domainEvent.getTargetDate())
																									.build();
		commandHandler.handle(command);
	}

}
