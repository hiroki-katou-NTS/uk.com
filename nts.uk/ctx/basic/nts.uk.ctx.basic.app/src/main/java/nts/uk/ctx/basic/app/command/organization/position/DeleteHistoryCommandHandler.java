package nts.uk.ctx.basic.app.command.organization.position;


import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteHistoryCommandHandler extends CommandHandler<DeleteHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteHistoryCommand> context) {

		String companyCode = AppContexts.user().companyCode();
		String hitoryId = IdentifierUtil.randomUniqueId();

		
		positionRepository.deleteHist(companyCode, hitoryId );

	}

}
