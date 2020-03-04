package nts.uk.ctx.pr.core.app.command.laborinsurance.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.laborinsurance.EditMethod;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateEmpInsurHisCommandHandler extends CommandHandler<EmpInsurHisCommand> {
	
	@Inject
	private EmpInsurBusBurRatioService empInsurBusBurRatioService;
	
	@Override
	protected void handle(CommandHandlerContext<EmpInsurHisCommand> context) {
		String cid = AppContexts.user().companyId();
		String hisId = context.getCommand().getHisId();
		YearMonth start = new YearMonth(context.getCommand().getStartMonthYear());
		YearMonth end = new YearMonth(context.getCommand().getEndMonthYear());
		if(EditMethod.DELETE.value == context.getCommand().getMethodEditing()) {
			empInsurBusBurRatioService.historyDeletionProcessing(hisId, cid);
		} else {
			empInsurBusBurRatioService.historyCorrectionProcecessing(cid, hisId, start, end);
		}
		
	}

}
