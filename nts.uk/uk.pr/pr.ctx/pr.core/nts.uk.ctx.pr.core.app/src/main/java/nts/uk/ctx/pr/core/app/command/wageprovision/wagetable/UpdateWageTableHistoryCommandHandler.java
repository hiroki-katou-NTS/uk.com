package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class UpdateWageTableHistoryCommandHandler extends CommandHandler<UpdateWageTableHistoryCommand> {

	@Inject
	private WageTableHistoryRepository wageTableHistRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateWageTableHistoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<WageTableHistory> optWageHist = wageTableHistRepo.getWageTableHistByCode(companyId,
				context.getCommand().getWageTableCode());
		if (optWageHist.isPresent()) {
			WageTableHistory wageHist = optWageHist.get();
			wageHist.items().stream().filter(i -> i.identifier().equals(context.getCommand().getHistoryId()))
					.findFirst().ifPresent(itemToBeChanged -> {
						wageHist.changeSpan(itemToBeChanged,
								new YearMonthPeriod(new YearMonth(context.getCommand().getStartYm()),
										new YearMonth(context.getCommand().getEndYm())));
						wageTableHistRepo.addOrUpdate(wageHist);
					});
		}
	}

}
