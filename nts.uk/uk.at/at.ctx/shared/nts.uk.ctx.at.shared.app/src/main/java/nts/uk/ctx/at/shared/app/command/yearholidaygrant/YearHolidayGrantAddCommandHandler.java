package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class YearHolidayGrantAddCommandHandler extends CommandHandler<YearHolidayGrantCommand> {
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	
	@Override
	protected void handle(CommandHandlerContext<YearHolidayGrantCommand> context) {
		YearHolidayGrantCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantHdTblSet> grantHolidaySet = yearHolidayRepo.findByCode(companyId, command.getYearHolidayCode());
		if (grantHolidaySet.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		
		GrantHdTblSet domain = command.toDomain();
		domain.validate();
		
		// add to db		
		yearHolidayRepo.add(domain);
	}
}
