package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * 応援カード編集設定の更新を行う
 * @author NWS_namnv
 *
 */
@Stateless
public class UpdateSupportCardSettingCommandHandler extends CommandHandler<SupportCardSettingCommand> {

	@Override
	protected void handle(CommandHandlerContext<SupportCardSettingCommand> context) {
		SupportCardSettingCommand command = context.getCommand();
		// TODO
		// set 応援カード編集設定
		// call repo
	}

}
