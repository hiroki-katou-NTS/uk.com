package nts.uk.ctx.exio.app.command.exi.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSet;

@Stateless
@Transactional
public class AddStdAcceptCondSetCommandHandler extends CommandHandler<StdAcceptCondSetCommand> {

	@Inject
	private StdAcceptCondSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdAcceptCondSetCommand> context) {
		StdAcceptCondSetCommand addCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		StdAcceptCondSet domain = new StdAcceptCondSet(companyId, addCommand.getSystemType(),
				addCommand.getConditionSettingCode(), addCommand.getConditionSettingName(),
				addCommand.getDeleteExistData(), addCommand.getAcceptMode(), addCommand.getCheckCompleted(),
				addCommand.getCategoryId(), addCommand.getCsvDataItemLineNumber(), addCommand.getCsvDataStartLine(),
				addCommand.getDeleteExistDataMethod());
		this.repository.add(domain);

	}
}
