package nts.uk.ctx.sys.assist.app.command.autosetting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.SelectCategoryScreenMode;

/**
 *登録を実行する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AddPatternCommandHandler extends CommandHandler<AddPatternCommand> {
	
	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddPatternCommand> context) {
		AddPatternCommand command = context.getCommand();
		//画面モードをチェックする
		switch(EnumAdaptor.valueOf(command.getScreenMode(), SelectCategoryScreenMode.class)) {
			//ドメインモデル「「パターン設定」を追加する
			case NEW:
				handleNew(command);
				break;
			//ドメインモデル「パターン設定」を更新する
			case UPDATE:
				handleUpdate(command);
				break;
		}
	}
	
	private void handleNew(AddPatternCommand command) {
		dataStoragePatternSettingRepository.add(DataStoragePatternSetting.createFromMemento(command));
	}
	
	private void handleUpdate(AddPatternCommand command) {
		dataStoragePatternSettingRepository.update(DataStoragePatternSetting.createFromMemento(command));
	}
}
