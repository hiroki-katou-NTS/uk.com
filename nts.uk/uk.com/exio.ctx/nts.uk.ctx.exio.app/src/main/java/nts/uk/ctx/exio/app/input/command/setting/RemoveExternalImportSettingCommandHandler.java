package nts.uk.ctx.exio.app.input.command.setting;

import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.shr.com.context.AppContexts;

public class RemoveExternalImportSettingCommandHandler extends CommandHandler<RemoveExternalImportSettingCommand> {
	
	@Inject
	private RemoveExternalImportSettingCommandRequire require;
	
	
	@Override
	protected void handle(CommandHandlerContext<RemoveExternalImportSettingCommand> context) {
		val require = this.require.create();
		val targetCode = context.getCommand().getCode();
		require.delete(AppContexts.user().companyId(), new ExternalImportCode(targetCode));
	}
	
	public static interface Require {
		void delete(String companyId, ExternalImportCode code);
	}

}
