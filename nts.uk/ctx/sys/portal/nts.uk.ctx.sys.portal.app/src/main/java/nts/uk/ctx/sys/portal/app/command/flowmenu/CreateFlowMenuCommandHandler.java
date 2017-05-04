/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;


@Stateless
@Transactional
public class CreateFlowMenuCommandHandler extends CommandHandler<CreateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository repository;
	
	@Inject
	public TopPagePartRepository repositoryTop;

	@Override
	protected void handle(CommandHandlerContext<CreateFlowMenuCommand> context) {
		String topPagePartId = IdentifierUtil.randomUniqueId();
		repository.add(context.getCommand().toDomain(topPagePartId));
		repositoryTop.add(context.getCommand().toTopPagePart(topPagePartId));
	}	
}
