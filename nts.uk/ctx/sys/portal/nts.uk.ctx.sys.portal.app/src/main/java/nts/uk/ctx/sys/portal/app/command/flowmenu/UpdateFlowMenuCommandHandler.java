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
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;

@Stateless
@Transactional
public class UpdateFlowMenuCommandHandler extends CommandHandler<UpdateFlowMenuCommand> {
	
	@Inject
	public FlowMenuRepository repository;
	
	@Inject
	public TopPagePartRepository repositoryTop;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlowMenuCommand> context) {
		
		String companyId = IdentifierUtil.randomUniqueId();
		String topPagePartId = context.getCommand().getTopPagePartId();
		//check topPagePartId is Existence	
		Optional<FlowMenu> getFlowMenu = repository.getFlowMenu(companyId, topPagePartId);
		Optional<TopPagePart> getTopPagePart = repositoryTop.find(topPagePartId);
		
		if(!getFlowMenu.isPresent() || !getTopPagePart.isPresent()){
			throw new BusinessException("ER026");
		}
		FlowMenu flowMenuInf = getFlowMenu.get();
		FlowMenu infor = FlowMenu.createFromJavaType(flowMenuInf.getCompanyID(),
				flowMenuInf.getToppagePartID(),
				flowMenuInf.getFileID(),
				context.getCommand().getFileName(),
				context.getCommand().getDefClassAtr());
		repository.update(infor);
		
		TopPagePart topPage = getTopPagePart.get();
		TopPagePart topInf = TopPagePart.createFromJavaType(topPage.getCompanyID(), 
				topPage.getToppagePartID(), 
				topPage.getCode().v(), 
				context.getCommand().getTopPageName(), 
				topPage.getType().value, 
				context.getCommand().getWidthSize(),
				context.getCommand().getHeightSize());
		repositoryTop.update(topInf);
	}
}