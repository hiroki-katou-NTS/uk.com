package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonth;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingMonthRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AddRoundingMonthCommandHandler extends CommandHandler<AddRoundingMonthCommand> {
	@Inject
	private RoundingMonthRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddRoundingMonthCommand> context) {
		AddRoundingMonthCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// convert to domain
		RoundingMonth month = command.toDomain(companyId);
		month.validate();
		Optional<RoundingMonth> optionalRoundingMonth = this.repository.findByCId(companyId, month.getTimeItemId().v());
		
		if (optionalRoundingMonth.isPresent()) {
			// update Holiday Addtime
			this.repository.update(month);
		}else {		
			// add Holiday Addtime
			this.repository.add(month);
			};
	}
}
