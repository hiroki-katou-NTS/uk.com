package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.A：フローメニューの作成.メニュー別OCD.フローメニュー作成の更新を行う
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateCreateFlowMenuCommandHandler extends CommandHandler<UpdateFlowMenuCommand> {

	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlowMenuCommand> context) {
		UpdateFlowMenuCommand command = context.getCommand();
		Optional<CreateFlowMenu> optCreateFlowMenu = createFlowMenuRepository
				.findByPk(AppContexts.user().companyCode(), command.getFlowMenuCode());
		if (optCreateFlowMenu.isPresent()) {
			CreateFlowMenu domain = optCreateFlowMenu.get();
			domain.setFlowMenuName(new TopPagePartName(command.getFlowMenuName()));
			createFlowMenuRepository.update(domain);
		} else throw new BusinessException("Msg_1806");
	}
}
