/**
 * 2:18:24 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class CompanyEventCommandHandler extends CommandHandler<CompanyEventCommand> {

	@Inject
	private CompanyEventRepository companyEventRepository;

	@Override
	protected void handle(CommandHandlerContext<CompanyEventCommand> context) {
		CompanyEventCommand command = context.getCommand();
		if (command.getState().equals("ADD")) {
			insertCommand(command);
		} else if (command.getState().equals("DELETE")) {
			deleteCommand(command);
		}
	}

	private void insertCommand(CompanyEventCommand command) {
		if (this.companyEventRepository.findByPK(AppContexts.user().companyId(), command.getDate()).isPresent()) {
			this.companyEventRepository.updateEvent(toDomain(command));
		} else {
			this.companyEventRepository.addEvent(toDomain(command));
		}
	}

	private void deleteCommand(CompanyEventCommand command) {
		this.companyEventRepository.removeEvent(toDomain(command));
	}

	private CompanyEvent toDomain(CompanyEventCommand command) {
		return CompanyEvent.createFromJavaType(AppContexts.user().companyId(), command.date, command.eventName);
	}

}
