package nts.uk.ctx.sys.assist.app.command.autosetting.storage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSetting;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.PatternClassification;
import nts.uk.ctx.sys.assist.dom.storage.SelectCategoryScreenMode;
import nts.uk.shr.com.context.AppContexts;

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
		String contractCode = AppContexts.user().contractCode();
		//画面モードをチェックする
		switch(EnumAdaptor.valueOf(command.getScreenMode(), SelectCategoryScreenMode.class)) {
			//ドメインモデル「「パターン設定」を追加する
			case NEW:
				handleNew(command, contractCode);
				break;
			//ドメインモデル「パターン設定」を更新する
			case UPDATE:
				handleUpdate(command, contractCode);
				break;
		}
	}
	
	private void handleNew(AddPatternCommand command, String contractCode) {
		Optional<DataStoragePatternSetting> op = dataStoragePatternSettingRepository.findByContractCdAndPatternCdAndPatternAtr(
				contractCode,
				command.getPatternCode(), 
				PatternClassification.USER_OPTIONAL.value);
		
		if (!op.isPresent()) {
			updateCommand(command, contractCode);
			dataStoragePatternSettingRepository.add(DataStoragePatternSetting.createFromMemento(command));
		} else throw new BusinessException("Msg_3");
	}
	
	private void handleUpdate(AddPatternCommand command, String contractCode) {
		updateCommand(command, contractCode);
		dataStoragePatternSettingRepository.update(DataStoragePatternSetting.createFromMemento(command));
	}
	
	private void updateCommand(AddPatternCommand command, String contractCode) {
		command.getCategoriesMaster().forEach(c -> {
			c.setContractCode(contractCode);
			c.setPatternCode(command.getPatternCode());
		});
		command.setContractCode(contractCode);
	}
}
