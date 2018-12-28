package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
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
public class AddWageTableCommandHandler extends CommandHandlerWithResult<AddWageTableCommand, String> {

	@Inject
	private WageTableRepository wageRepo;

	@Inject
	private WageTableHistoryRepository wageHistRepo;

	@Override
	protected String handle(CommandHandlerContext<AddWageTableCommand> context) {
		String companyId = AppContexts.user().companyId();
		if (wageRepo.getWageTableById(companyId, context.getCommand().getWageTableCode()).isPresent()) {
			throw new BusinessException("Msg_3");
		}
		WageTable wageTable = context.getCommand().toWageTableDomain();
		wageRepo.add(wageTable);
		WageTableHistory wageHist = context.getCommand().toWageTableHistoryDomain();
		wageHistRepo.addOrUpdate(wageHist);
		return wageHist.getValidityPeriods().get(0).identifier();
	}

}
