package nts.uk.ctx.at.record.app.command.calculation.holiday.roundingmonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.roundingset.ItemRoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AddRoundingMonthCommandHandler extends CommandHandler<List<AddRoundingMonthCommand>> {
	
	@Inject
	private RoundingSetOfMonthlyRepository repository;

	@Override
	protected void handle(CommandHandlerContext<List<AddRoundingMonthCommand>> context) {
		List<AddRoundingMonthCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// convert to domain
		List<ItemRoundingSetOfMonthly> months = command.stream().map(x -> ItemRoundingSetOfMonthly.of(companyId, 
				Integer.parseInt(x.getTimeItemId()), new TimeRoundingSetting(x.unit, x.rounding))).collect(Collectors.toList());
		
		repository.persistAndUpdateMonItemRound(months, companyId);
	}
}
