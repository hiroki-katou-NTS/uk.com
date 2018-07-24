package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;

import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand> {

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	

	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		String cid = AppContexts.user().companyId();
		String condSetCd = context.getCommand().getConditionSetCd();
		stdOutputCondSetService.remove(cid, condSetCd);
	}
}
