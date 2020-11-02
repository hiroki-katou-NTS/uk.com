package nts.uk.ctx.sys.assist.app.command.autosetting.storage;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.storage.DataStoragePatternSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeletePatternCommandHandler extends CommandHandler<DeletePatternCommand> {

	@Inject
	private DataStoragePatternSettingRepository dataStoragePatternSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<DeletePatternCommand> context) {
		DeletePatternCommand command = context.getCommand();
		//ドメインモデル「パターン設定」を削除する
		dataStoragePatternSettingRepository.delete(
				AppContexts.user().contractCode(), 
				command.getPatternCode(),
				command.getPatternClassification());
	}
}
