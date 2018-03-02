package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

@Stateless
@Transactional
public class RemoveStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand> {

	@Inject
	private StdAcceptCondSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
		String cid = AppContexts.user().companyId();
		int sysType = context.getCommand().getSystemType();
		String conditionSetCd = context.getCommand().getConditionSetCd();
		repository.remove(cid, sysType, conditionSetCd);
	}
}
