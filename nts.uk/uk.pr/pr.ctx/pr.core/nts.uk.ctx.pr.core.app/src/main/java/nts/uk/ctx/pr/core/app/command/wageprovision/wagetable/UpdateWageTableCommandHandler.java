package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSettingRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContentRepository;
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

	@Inject
	private WageTableContentRepository wageContentRepo;

	@Inject
	private ElementRangeSettingRepository elemRangeRepo;

	@Override
	protected String handle(CommandHandlerContext<UpdateWageTableCommand> context) {
		String companyId = AppContexts.user().companyId();
		wageRepo.update(context.getCommand().toWageTableDomain());

		if (context.getCommand().getHistory() != null) {
			// neu them moi lich su
			if (context.getCommand().getHistory().getHistoryID().isEmpty()
					&& context.getCommand().getHistory().getStartMonth() != null
					&& context.getCommand().getHistory().getEndMonth() != null) {
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

			// update element range setting
			if (context.getCommand().getElementRange() != null
					&& context.getCommand().getElementRange().getHistoryID() != null 
					&& context.getCommand().getElementRange().getFirstElementRange() != null) {
				context.getCommand().getElementRange().setHistoryID(context.getCommand().getHistory().getHistoryID());
				Optional<ElementRangeSetting> optElementRange = elemRangeRepo
						.getElementRangeSettingById(context.getCommand().getHistory().getHistoryID());
				if (optElementRange.isPresent())
					elemRangeRepo.update(context.getCommand().getElementRange().fromCommandToDomain());
				else
					elemRangeRepo.add(context.getCommand().getElementRange().fromCommandToDomain());
			}

			// update wage table content
			if (context.getCommand().getWageTableContent() != null
					&& context.getCommand().getWageTableContent().getHistoryID() != null) {
				context.getCommand().getWageTableContent().setHistoryID(context.getCommand().getHistory().getHistoryID());
				wageContentRepo.addOrUpdate(context.getCommand().getWageTableContent().fromCommandToDomain());
			}
			
			return context.getCommand().getHistory().getHistoryID();
		} else {
			return "";
		}
	}

}
