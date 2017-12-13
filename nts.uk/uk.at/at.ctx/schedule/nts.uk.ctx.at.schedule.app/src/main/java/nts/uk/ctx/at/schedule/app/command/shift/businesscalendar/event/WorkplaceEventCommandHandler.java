/**
 * 2:18:40 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class WorkplaceEventCommandHandler extends CommandHandler<WorkplaceEventCommand> {
	@Inject
	private WorkplaceEventRepository workplaceEventRepository;

	@Override
	protected void handle(CommandHandlerContext<WorkplaceEventCommand> context) {
		WorkplaceEventCommand command = context.getCommand();
		if (command.getState().equals("ADD")) {
			insertCommand(command);
		} else if (command.getState().equals("DELETE")) {
			deleteCommand(command);
		}
	}

	private void insertCommand(WorkplaceEventCommand command) {
		if (this.workplaceEventRepository.findByPK(command.getWorkplaceId(), command.getDate()).isPresent()) {
			this.workplaceEventRepository.updateEvent(toDomain(command));
		} else {
			this.workplaceEventRepository.addEvent(toDomain(command));
		}
	}

	private void deleteCommand(WorkplaceEventCommand command) {
		this.workplaceEventRepository.removeEvent(toDomain(command));
	}

	private WorkplaceEvent toDomain(WorkplaceEventCommand command) {
		return WorkplaceEvent.createFromJavaType(command.getWorkplaceId(), command.date, command.eventName);
	}

}
