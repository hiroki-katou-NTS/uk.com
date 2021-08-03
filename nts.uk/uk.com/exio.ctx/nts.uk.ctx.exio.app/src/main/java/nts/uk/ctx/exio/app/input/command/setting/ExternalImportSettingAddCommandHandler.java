package nts.uk.ctx.exio.app.input.command.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingRequire;

@Stateless
public class ExternalImportSettingAddCommandHandler extends CommandHandler<ExternalImportSettingCommand>{
	
	@Inject
	private ExternalImportSettingRequire require;

	@Override
	protected void handle(CommandHandlerContext<ExternalImportSettingCommand> context) {
		val require = this.require.create();
		val setting = context.getCommand().toDomain(require);
		
	}

}
