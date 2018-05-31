package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePer;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveGrantDatePerCommandHandler extends CommandHandler<RemoveGrantDatePerCommand> {

	@Inject
	private GrantRegularRepository grantRegularRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveGrantDatePerCommand> context) {
		RemoveGrantDatePerCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantDatePer> grantDatePer = grantRegularRepository.getPerByCode(companyId, command.getSpecialHolidayCode(), command.getPersonalGrantDateCode());
		if (!grantDatePer.isPresent()) {
			throw new RuntimeException("Grant Date Per Not Found");
		}
				
		if(grantDatePer.get().getProvision() == 1) {
			throw new BusinessException("Msg_1219");
		}
		
		// remove Grant Date Per by code
		grantRegularRepository.removePer(companyId, command.getSpecialHolidayCode(), command.getPersonalGrantDateCode());
		// remove Grant Date Per Set by code
		grantRegularRepository.removePerSet(companyId, command.getSpecialHolidayCode(), command.getPersonalGrantDateCode());
	}
}
