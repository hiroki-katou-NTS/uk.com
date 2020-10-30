package nts.uk.ctx.sys.portal.app.command.toppagepart.createflowmenu;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.D：フローメニューレイアウト設定.メニュー別OCD.フローメニュー作成のレイアウトを登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateFlowMenuLayoutCommandHandler extends CommandHandler<UpdateFlowMenuLayoutCommand> {
	
	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateFlowMenuLayoutCommand> context) {
		UpdateFlowMenuLayoutCommand command = context.getCommand();
		//1. get(ログイン会社ID、フローメニューコード)
		Optional<CreateFlowMenu> optCreateFlowMenu = this.createFlowMenuRepository
				.findByPk(AppContexts.user().companyId(), command.getFlowMenuCode());
		
		optCreateFlowMenu.ifPresent(domain -> {
			//4. create(inputフローメニューレイアウト)
			//5. set(ファイルID)
			domain.setFlowMenuLayout(command.getFlowMenuLayout() != null 
									? Optional.of(FlowMenuLayout.createFromMemento(command.getFlowMenuLayout()))
									: Optional.empty());
			
			//2. not　フローメニューレイアウト　empty: delete()
			//6. set(フローメニューレイアウト)
			//7. persist()
			this.createFlowMenuRepository.update(domain);
		}); 
	}
}
