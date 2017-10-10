package command.person.setting.selectionitem;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSelectionItemCommandHandler extends CommandHandler<UpdateSelectionItemCommand> {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectionItemCommand> context) {

		// ドメインモデル「個人情報の選択項目」を登録する
		UpdateSelectionItemCommand command = context.getCommand();
		PerInfoSelectionItem domain = PerInfoSelectionItem.createFromJavaType(command.getSelectionItemId(),
				command.getSelectionItemName(), command.getMemo(),
				command.isSelectionItemClassification() == true ? 1 : 0, AppContexts.user().contractCode(),
				command.getIntegrationCode(), command.getFormatSelection().getSelectionCode(),
				command.getFormatSelection().isSelectionCodeCharacter() == true ? 1 : 0,
				command.getFormatSelection().getSelectionName(),
				command.getFormatSelection().getSelectionExternalCode());

		// 選択項目ID:「個人情報の選択項目」を登録する
		this.perInfoSelectionItemRepo.update(domain);
	}

}
