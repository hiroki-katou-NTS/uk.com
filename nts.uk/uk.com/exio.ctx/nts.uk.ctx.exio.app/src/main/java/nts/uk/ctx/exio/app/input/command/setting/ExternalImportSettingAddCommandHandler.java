package nts.uk.ctx.exio.app.input.command.setting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportSettingAddCommandHandler extends CommandHandler<ExternalImportSettingCommand>{

	@Override
	protected void handle(CommandHandlerContext<ExternalImportSettingCommand> context) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
