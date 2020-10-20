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
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.A：フローメニューの作成.メニュー別OCD.フローメニュー作成の削除を行う
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteCreateFlowMenuCommandHandler extends CommandHandler<DeleteFlowMenuCommand> {
	
	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteFlowMenuCommand> context) {
		DeleteFlowMenuCommand command = context.getCommand();
		Optional<CreateFlowMenu> optDomain = this.createFlowMenuRepository
				.findByPk(AppContexts.user().companyId(), command.getFlowMenuCode());
		if (optDomain.isPresent()) {
			this.createFlowMenuRepository.delete(optDomain.get());
		} else throw new BusinessException("Msg_1807");
	}
}
