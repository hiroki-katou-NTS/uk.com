package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand> {

	@Inject
	private StdAcceptCondSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
		StdAcceptCondSetCommand updateCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		StdAcceptCondSet domain = StdAcceptCondSet.createFromMemento(companyId, updateCommand);
		this.repository.update(domain);
	}
}
