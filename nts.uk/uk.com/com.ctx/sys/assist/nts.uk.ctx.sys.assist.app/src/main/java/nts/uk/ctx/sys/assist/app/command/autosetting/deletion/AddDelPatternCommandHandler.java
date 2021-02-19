package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSetting;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.ctx.sys.assist.dom.storage.PatternClassification;
import nts.uk.ctx.sys.assist.dom.storage.SelectCategoryScreenMode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AddDelPatternCommandHandler extends CommandHandler<AddDelPatternCommand> {

	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<AddDelPatternCommand> context) {
		AddDelPatternCommand command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		// 画面モードをチェックする
		SelectCategoryScreenMode screenMode = EnumAdaptor.valueOf(command.getScreenMode(),
				SelectCategoryScreenMode.class);
		// ドメインモデル「「パターン設定」を追加する
		if (screenMode.equals(SelectCategoryScreenMode.NEW)) {
			handleNew(command, contractCode);
		} else {
			// ドメインモデル「パターン設定」を更新する
			handleUpdate(command, contractCode);
		}
	}

	private void handleNew(AddDelPatternCommand command, String contractCode) {
		Optional<DataDeletionPatternSetting> op = dataDeletionPatternSettingRepository.findByPk(contractCode,
				command.getPatternCode(), PatternClassification.USER_OPTIONAL.value);

		if (!op.isPresent()) {
			updateCommand(command, contractCode);
			dataDeletionPatternSettingRepository.add(DataDeletionPatternSetting.createFromMemento(command));
		} else
			throw new BusinessException("Msg_3");
	}

	private void handleUpdate(AddDelPatternCommand command, String contractCode) {
		updateCommand(command, contractCode);
		dataDeletionPatternSettingRepository.update(DataDeletionPatternSetting.createFromMemento(command));
	}

	private void updateCommand(AddDelPatternCommand command, String contractCode) {
		command.getCategoriesMaster().forEach(c -> {
			c.setContractCode(contractCode);
			c.setPatternCode(command.getPatternCode());
		});
		command.setContractCode(contractCode);
	}
}
