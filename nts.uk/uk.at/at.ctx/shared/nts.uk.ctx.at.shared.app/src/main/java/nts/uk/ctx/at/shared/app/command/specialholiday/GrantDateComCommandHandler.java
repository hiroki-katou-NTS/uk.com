package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class GrantDateComCommandHandler extends CommandHandler<GrantDateComCommand> {
	
	@Inject
	private GrantRegularRepository grantRegularRepository;

	@Override
	protected void handle(CommandHandlerContext<GrantDateComCommand> context) {
		GrantDateComCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantDateCom> grantDateCom = grantRegularRepository.getComByCode(companyId, command.getSpecialHolidayCode());
		if (grantDateCom.isPresent()) {
			//throw new BusinessException("Msg_3");
		}
		
		GrantDateCom domain = command.toDomain();
		grantRegularRepository.add(domain);
	}
}
