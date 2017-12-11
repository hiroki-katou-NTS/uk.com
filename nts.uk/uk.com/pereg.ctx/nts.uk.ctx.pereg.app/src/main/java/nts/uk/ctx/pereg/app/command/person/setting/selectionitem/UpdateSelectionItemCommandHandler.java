package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateSelectionItemCommandHandler extends CommandHandler<UpdateSelectionItemCommand> {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateSelectionItemCommand> context) {
		UpdateSelectionItemCommand command = context.getCommand();
		
		// ドメインモデル「個人情報の選択項目」のエラーチェック
		Optional<PerInfoSelectionItem> optCheckExistByName = this.perInfoSelectionItemRepo
				.getSelectionItemByName(command.getSelectionItemName());
		
		Optional<PerInfoSelectionItem> updateObject = this.perInfoSelectionItemRepo
				.getSelectionItemBySelectionItemId(command.getSelectionItemId());
		
		// 「選択項目名称」は重複してはならない
		if (optCheckExistByName.isPresent() && !command.getSelectionItemName().equals(updateObject.get().getSelectionItemName().v())) {
			throw new BusinessException(new RawErrorMessage("Msg_513"));
		}

		// ドメインモデル「個人情報の選択項目」を登録する
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
