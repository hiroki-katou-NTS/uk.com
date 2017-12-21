package nts.uk.ctx.at.shared.app.command.calculation.holiday.time;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalc;
import nts.uk.ctx.at.shared.dom.calculation.holiday.time.OverdayCalcHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

public class AddOverdayCalcCommandHandler extends CommandHandlerWithResult<AddOverdayCalcCommand, List<String>>{
	@Inject
	private OverdayCalcHolidayRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddOverdayCalcCommand> context) {
		AddOverdayCalcCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// convert to domain
				OverdayCalc calcHoliday = command.toDomain(companyId);
				Optional<OverdayCalc> optionalHoliday = this.repository.findByCId(companyId);
				if (optionalHoliday.isPresent()) {
					// update Holiday Addtime
					this.repository.update(calcHoliday);
				}
				// add Holiday Addtime
				this.repository.add(calcHoliday);
				return null;
	}

}
