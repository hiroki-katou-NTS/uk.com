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
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional

public class DeleteFlowMenuCommandHandler extends CommandHandler<DeleteFlowMenuCommand> {

	@Inject
	public FlowMenuRepository repository;
	@Inject
	public TopPagePartRepository repositoryTop;

	@Override
	protected void handle(CommandHandlerContext<DeleteFlowMenuCommand> context) {
		String companyID = AppContexts.user().companyID();
		String topPagePartId = context.getCommand().getToppagePartID();
		repository.remove(companyID, topPagePartId);
		repositoryTop.remove(companyID, topPagePartId);
	}
	
}
