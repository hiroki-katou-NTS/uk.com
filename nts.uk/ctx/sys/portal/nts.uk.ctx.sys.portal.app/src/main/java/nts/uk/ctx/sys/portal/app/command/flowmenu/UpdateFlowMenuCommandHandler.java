/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;

@Stateless
@Transactional
public class UpdateFlowMenuCommandHandler extends CommandHandler<UpdateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlowMenuCommand> context) {
		//repository.update(context.getCommand().toDomain());
	}
}