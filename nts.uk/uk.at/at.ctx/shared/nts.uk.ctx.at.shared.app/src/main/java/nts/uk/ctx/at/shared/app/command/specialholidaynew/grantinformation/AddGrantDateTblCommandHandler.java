package nts.uk.ctx.at.shared.app.command.specialholidaynew.grantinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantDateTblRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddGrantDateTblCommandHandler extends CommandHandler<GrantDateTblCommand> {
	@Inject
	private GrantDateTblRepository repo;

	@Override
	protected void handle(CommandHandlerContext<GrantDateTblCommand> context) {
		GrantDateTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantDateTbl> grantDateTbl = repo.findByCode(companyId, command.getSpecialHolidayCode(), command.getGrantDateCode());
		if (grantDateTbl.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		
		GrantDateTbl domain = command.toDomain();
		domain.validateInput();
		
		// add to db		
		repo.add(domain);
	}
}
