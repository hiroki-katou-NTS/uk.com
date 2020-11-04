package nts.uk.ctx.sys.portal.app.command.toppage;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * The class UpdateLayoutFlowMenuCommandHandler
 */
@Stateless
public class UpdateLayoutFlowMenuCommandHandler extends CommandHandler<UpdateLayoutFlowMenuCommand> {

	@Inject
	private LayoutNewRepository layoutNewRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateLayoutFlowMenuCommand> context) {
		UpdateLayoutFlowMenuCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<LayoutNew> findLayoutFlowMenu = layoutNewRepository.getByCidAndCode(companyId, command.getTopPageCode(), BigDecimal.valueOf(0));
		
		if (findLayoutFlowMenu.isPresent()) {
			LayoutNew layoutNew = command.toDomain();
			layoutNewRepository.update(layoutNew);
		}
		
	}

}
