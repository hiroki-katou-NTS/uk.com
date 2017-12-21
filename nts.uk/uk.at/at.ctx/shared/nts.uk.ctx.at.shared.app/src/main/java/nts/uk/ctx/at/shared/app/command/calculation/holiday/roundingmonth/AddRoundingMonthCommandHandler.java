package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

/**
 * @author phongtq
 * The class Add Rounding Month Command Handler
 */
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddRoundingMonthCommandHandler extends CommandHandlerWithResult<AddRoundingMonthCommand, List<String>> {
	@Inject
	private RoundingMonthRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddRoundingMonthCommand> context) {
		AddRoundingMonthCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// convert to domain
		RoundingMonth month = command.toDomain(companyId);
		month.validate();
		Optional<RoundingMonth> optionalRoundingMonth = this.repository.findByCId(companyId, month.getTimeItemId().v());
		
		if (optionalRoundingMonth.isPresent()) {
			// update Holiday Addtime
			this.repository.update(month);
		}
		
		// add Holiday Addtime
		this.repository.add(month);
		return null;
	}
}
