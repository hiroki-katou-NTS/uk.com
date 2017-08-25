package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class GrantHolidayTblUpdateCommandHandler extends CommandHandler<GrantHolidayTblCommand> {
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	@Override
	protected void handle(CommandHandlerContext<GrantHolidayTblCommand> context) {
		GrantHolidayTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		List<GrantHdTbl> grantHolidayTbl = grantYearHolidayRepo.findByCode(companyId, command.getConditionNo(), command.getYearHolidayCode());
		if (grantHolidayTbl.isEmpty()) {
			throw new BusinessException("");
		}
		
		GrantHdTbl domain = command.toDomain();
		domain.validate();
		
		// add to db		
		grantYearHolidayRepo.update(domain);
	}

}
