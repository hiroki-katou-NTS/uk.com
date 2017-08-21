package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GrantHolidayTblDeleteCommandHandler extends CommandHandler<List<GrantHolidayTblDeleteCommand>> {
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	@Override
	protected void handle(CommandHandlerContext<List<GrantHolidayTblDeleteCommand>> context) {
		List<GrantHolidayTblDeleteCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		for (GrantHolidayTblDeleteCommand item : command) {
			grantYearHolidayRepo.remove(companyId, item.getGrantYearHolidayNo(), item.getConditionNo(), item.getYearHolidayCode());
		}
		
	}

}
