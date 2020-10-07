package nts.uk.ctx.sys.assist.app.command.autosetting.deletion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionPatternSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteDelPatternCommandHandler extends CommandHandler<DeleteDelPatternCommand> {

	@Inject
	private DataDeletionPatternSettingRepository dataDeletionPatternSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteDelPatternCommand> context) {
		DeleteDelPatternCommand command = context.getCommand();
		//ドメインモデル「パターン設定」を削除する
		dataDeletionPatternSettingRepository.delete(
				AppContexts.user().contractCode(), 
				command.getPatternCode(),
				command.getPatternClassification());
	}
}
