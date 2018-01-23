package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class AddRoundingMonthCommandHandler extends CommandHandler<List<AddRoundingMonthCommand>> {
	@Inject
	private RoundingMonthRepository repository;

	@Override
	protected void handle(CommandHandlerContext<List<AddRoundingMonthCommand>> context) {
		List<AddRoundingMonthCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// convert to domain
		List<RoundingMonth> months = command.stream().map(x -> x.toDomain(companyId)).collect(Collectors.toList());
		months.forEach(month -> {
			month.validate();
			Optional<RoundingMonth> optionalRoundingMonth = this.repository.findByCId(companyId,
					month.getTimeItemId().v());
			if (optionalRoundingMonth.isPresent()) {
				// update Holiday Addtime
				this.repository.update(month);
			} else {
				// add Holiday Addtime
				this.repository.add(month);
			}
			;
		});
	}
}
