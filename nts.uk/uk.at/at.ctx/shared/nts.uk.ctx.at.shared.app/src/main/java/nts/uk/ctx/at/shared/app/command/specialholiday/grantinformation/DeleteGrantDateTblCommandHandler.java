package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class DeleteGrantDateTblCommandHandler extends CommandHandler<DeleteGrantDateTblCommand> {
	@Inject
	private GrantDateTblRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteGrantDateTblCommand> context) {
		DeleteGrantDateTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		repo.delete(companyId, command.getSpecialHolidayCode(), command.getGrantDateCode());
	}
}
