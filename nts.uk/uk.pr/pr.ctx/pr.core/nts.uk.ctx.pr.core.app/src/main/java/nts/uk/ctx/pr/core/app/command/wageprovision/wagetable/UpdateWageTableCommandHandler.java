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
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class UpdateWageTableCommandHandler extends CommandHandlerWithResult<UpdateWageTableCommand, String> {

	private static final String NEW_HIST_ID = "zzzzzz10";
	
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
			if (context.getCommand().getHistory().getHistoryID().equals(NEW_HIST_ID)) {
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
			if (context.getCommand().getElementRange() != null) {
				context.getCommand().getElementRange().setHistoryID(context.getCommand().getHistory().getHistoryID());
				Optional<ElementRangeSetting> optElementRange = elemRangeRepo.getElementRangeSettingById(
						context.getCommand().getHistory().getHistoryID(), companyId,
						context.getCommand().getWageTableCode());
				if (optElementRange.isPresent())
					elemRangeRepo.update(context.getCommand().getElementRange().fromCommandToDomain(), companyId,
							context.getCommand().getWageTableCode());
				else
					elemRangeRepo.add(context.getCommand().getElementRange().fromCommandToDomain(), companyId,
							context.getCommand().getWageTableCode());
			}

			// update wage table content
			if (context.getCommand().getWageTableContent() != null) {
				context.getCommand().getWageTableContent()
						.setHistoryID(context.getCommand().getHistory().getHistoryID());
				if (context.getCommand().getWageTableContent().isBrandNew()) {
					wageContentRepo.addOrUpdate(context.getCommand().getWageTableContent().fromCommandToDomain(),
							companyId, context.getCommand().getWageTableCode());
				} else {
					wageContentRepo.updateListPayment(context.getCommand().getHistory().getHistoryID(), companyId,
							context.getCommand().getWageTableCode(),
							context.getCommand().getWageTableContent().fromCommandToDomain().getPayments());
				}
			}

			return context.getCommand().getHistory().getHistoryID();
		} else {
			return "";
		}
	}

}
