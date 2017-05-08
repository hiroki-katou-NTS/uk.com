/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.flowmenu.DefClassAtr;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
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
		//check topPagePartId is exit
		//check topPagePartId is Existence	
		Optional<FlowMenu> getFlowMenu = repository.getFlowMenu(companyID, topPagePartId);
		Optional<TopPagePart> getTopPagePart = repositoryTop.find(topPagePartId);
		
		if(!getFlowMenu.isPresent() || !getTopPagePart.isPresent()){
			throw new BusinessException("ER026");
		}
		if(getFlowMenu.get().getDefClassAtr() == DefClassAtr.Default){
			throw new BusinessException("Msg_76");
		}
		
		repository.remove(companyID, topPagePartId);
		repositoryTop.remove(companyID, topPagePartId);
	}
	
}
