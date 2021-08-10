package nts.uk.ctx.cloud.operate.app.command;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class GeneratePasswordCommandHandler extends CommandHandler<GeneratePasswordCommand> {

	@Override
	protected void handle(CommandHandlerContext<GeneratePasswordCommand> context) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
