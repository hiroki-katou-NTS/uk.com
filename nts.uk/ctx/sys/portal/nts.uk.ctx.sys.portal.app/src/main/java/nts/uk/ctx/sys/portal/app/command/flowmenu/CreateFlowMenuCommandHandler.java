/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
public class CreateFlowMenuCommandHandler extends CommandHandler<CreateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository repository;
	
	@Inject
	public TopPagePartRepository repositoryTop;

	@Inject
	public TopPagePartService service;
	@Override
	protected void handle(CommandHandlerContext<CreateFlowMenuCommand> context) {
		String topPagePartId = IdentifierUtil.randomUniqueId();
		String companyId = AppContexts.user().companyId();
		String code = context.getCommand().getTopPageCode();
		//check code
		if(service.isExit(companyId, code, 2)){
			throw new BusinessException("Msg_3");
		}
		repository.add(context.getCommand().toDomain(topPagePartId));
		repositoryTop.add(context.getCommand().toTopPagePart(topPagePartId));
	}	
}
