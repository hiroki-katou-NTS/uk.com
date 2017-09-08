package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePer;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class GrantDatePerCommandHandler extends CommandHandler<GrantDatePerCommand> {
	@Inject
	private GrantRegularRepository grantRegularRepository;

	@Override
	protected void handle(CommandHandlerContext<GrantDatePerCommand> context) {
		GrantDatePerCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		GrantDatePer domain = command.toDomain();
		domain.validate();
		
		// check exists code
		Optional<GrantDatePer> grantDatePer = grantRegularRepository.getPerByCode(companyId, command.getSpecialHolidayCode(), command.getPersonalGrantDateCode());
		if (grantDatePer.isPresent()) {
			// Update data
			grantRegularRepository.updatePer(domain);
			return;
		} 
		
		// Add new data
		grantRegularRepository.addPer(domain);
	}
}
