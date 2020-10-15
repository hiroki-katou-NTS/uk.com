package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMntRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Class handling adding insufficient flex holiday time management command data
 * @author HoangNDH
 *
 */
@Stateless
public class AddInsufficientFlexHolidayMntCommandHandler extends CommandHandler<AddInsufficientFlexHolidayMntCommand> {
	@Inject
	private InsufficientFlexHolidayMntRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddInsufficientFlexHolidayMntCommand> context) {
		AddInsufficientFlexHolidayMntCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// Convert to domain
		InsufficientFlexHolidayMnt insufficientFlexHolidayMnt = command.toDomain(companyId);
		
		Optional<InsufficientFlexHolidayMnt> optInsufficientFlexHolidayMnt = repository.findByCId(companyId);
		if (optInsufficientFlexHolidayMnt.isPresent()) {
			repository.update(insufficientFlexHolidayMnt);
		}
		else {
			repository.add(insufficientFlexHolidayMnt);
		}
	}
	
}
