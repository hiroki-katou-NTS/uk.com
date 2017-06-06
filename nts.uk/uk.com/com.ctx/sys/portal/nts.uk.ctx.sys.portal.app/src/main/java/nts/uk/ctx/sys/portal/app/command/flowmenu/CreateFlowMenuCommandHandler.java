package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.service.FlowMenuService;
import nts.arc.i18n.custom.IInternationalization;

/**
 * @author hieult
 */
@Stateless
@Transactional
public class CreateFlowMenuCommandHandler extends CommandHandler<CreateFlowMenuCommand> {
	
	@Inject
	private FlowMenuService flowMenuService;
	
	@Inject
	IInternationalization internationalization;
	
	@Override
	protected void handle(CommandHandlerContext<CreateFlowMenuCommand> context) {
		String topPagePartId = IdentifierUtil.randomUniqueId();
		CreateFlowMenuCommand command = context.getCommand();
		
		flowMenuService.createFlowMenu(command.toDomain(topPagePartId));
	}	
}
