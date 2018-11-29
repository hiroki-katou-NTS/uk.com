package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class UpdateWageTableCommandHandler extends CommandHandlerWithResult<UpdateWageTableCommand, String> {

	@Inject
	private WageTableRepository wageRepo;

	@Inject
	private WageTableHistoryRepository wageHistRepo;

	@Override
	protected String handle(CommandHandlerContext<UpdateWageTableCommand> context) {
		String companyId = AppContexts.user().companyId();
		wageRepo.update(context.getCommand().toWageTableDomain());
		// neu them moi lich su
		if (context.getCommand().getHistory().getHistoryID().isEmpty()) {
			context.getCommand().getHistory().setHistoryID(IdentifierUtil.randomUniqueId());
			Optional<WageTableHistory> optWageHist = wageHistRepo.getWageTableHistByCode(companyId,
					context.getCommand().getWageTableCode());
			if (optWageHist.isPresent()) {
				WageTableHistory wageHist = optWageHist.get();
				wageHist.add(new YearMonthHistoryItem(context.getCommand().getHistory().getHistoryID(),
						new YearMonthPeriod(new YearMonth(context.getCommand().getHistory().getStartMonth()),
								new YearMonth(context.getCommand().getHistory().getEndMonth()))));
				wageHistRepo.addOrUpdate(wageHist);
			} else {
				wageHistRepo.addOrUpdate(context.getCommand().toWageTableHistoryDomain());
			}
		}
		// update wage table content

		return context.getCommand().getHistory().getHistoryID();
	}

}
