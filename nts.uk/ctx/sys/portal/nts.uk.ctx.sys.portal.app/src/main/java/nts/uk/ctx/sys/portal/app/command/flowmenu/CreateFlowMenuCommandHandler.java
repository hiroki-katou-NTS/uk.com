package nts.uk.ctx.sys.portal.app.command.flowmenu;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;
import nts.uk.shr.com.context.AppContexts;


@Stateless
@Transactional
/**
 * @author hieult
 */
public class CreateFlowMenuCommandHandler extends CommandHandler<CreateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository flowMenuRepository;

	@Inject
	public TopPagePartService service;
	@Override
	protected void handle(CommandHandlerContext<CreateFlowMenuCommand> context) {
		String topPagePartId = IdentifierUtil.randomUniqueId();
		String companyId = AppContexts.user().companyId();
		String flowMenuCD = context.getCommand().getTopPageCode();
		//check code
		if(service.isExist(companyId, flowMenuCD, 2)){
			throw new BusinessException("Msg_3");
		}
		
		flowMenuRepository.add(context.getCommand().toDomain(topPagePartId));
	}	
}
