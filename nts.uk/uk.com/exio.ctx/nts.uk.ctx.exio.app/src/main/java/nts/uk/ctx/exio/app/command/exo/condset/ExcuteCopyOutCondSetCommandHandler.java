package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExcuteCopyOutCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand> {

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;

	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		String userId = AppContexts.user().userId();
		String exterOutCndSetCd = command.getCopyDestinationCode();
		String conditionSetCd = command.getConditionSetCd();
		int overwrite = command.getOverWrite();
		// In the case of "copy to standard form" or "fixed form copy" => userId
		// = none
		List<StdOutputCondSet> lstStdOutputCondSet = stdOutputCondSetRepository
				.getOutputCondSetByCidAndconditionSetCd(cid, conditionSetCd);
		if (lstStdOutputCondSet.size() > 0) {
			if (overwrite == 1) {
				stdOutputCondSetService.getResultAndOverwrite("OK", "DONOT");
			} else {
			  throw new BusinessException("Msg_3");
			}
		} else {
			stdOutputCondSetService.getResultAndOverwrite("OK", "TO");
		}
		
	}

}
