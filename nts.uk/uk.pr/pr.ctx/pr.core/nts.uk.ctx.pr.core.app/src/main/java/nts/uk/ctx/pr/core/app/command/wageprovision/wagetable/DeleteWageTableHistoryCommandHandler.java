package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSettingRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContentRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DeleteWageTableHistoryCommandHandler extends CommandHandlerWithResult<DeleteWageTableHistoryCommand, String> {

	@Inject
	private ElementRangeSettingRepository elemRangeSetRepo;

	@Inject
	private WageTableContentRepository wageTableContentRepo;

	@Inject
	private WageTableHistoryRepository wageTableHistRepo;

	@Inject
	private WageTableRepository wageTableRepo;

	@Override
	protected String handle(CommandHandlerContext<DeleteWageTableHistoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		elemRangeSetRepo.remove(context.getCommand().getHistoryId(), companyId, context.getCommand().getWageTableCode());
		wageTableContentRepo.remove(context.getCommand().getHistoryId(), companyId, context.getCommand().getWageTableCode());
		Optional<WageTableHistory> optWageHist = wageTableHistRepo.getWageTableHistByCode(companyId,
				context.getCommand().getWageTableCode());
		if (optWageHist.isPresent()) {
			WageTableHistory wageHist = optWageHist.get();
			if (wageHist.items().size() > 1) {
				wageHist.items().stream().filter(i -> i.identifier().equals(context.getCommand().getHistoryId()))
						.findFirst().ifPresent(itemToBeRemoved -> {
							wageHist.remove(itemToBeRemoved);
							wageTableHistRepo.addOrUpdate(wageHist);
						});
				return wageHist.items().get(0).identifier();
			} else {
				wageTableHistRepo.remove(companyId, context.getCommand().getWageTableCode());
				wageTableRepo.remove(companyId, context.getCommand().getWageTableCode());
				
			}
		}
		return null;
	}

}
