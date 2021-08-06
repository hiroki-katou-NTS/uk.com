package nts.uk.ctx.exio.app.input.command.setting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SaveExternalImportSettingCommandHandler extends CommandHandler<SaveExternalImportSettingCommand>{
	
	@Inject
	private SaveExternalImportSettingCommandRequire require;
	
	@Override
	protected void handle(CommandHandlerContext<SaveExternalImportSettingCommand> context) {
		val require = this.require.create();
		if(context.getCommand().getIsCreateMode()) {
			require.insert(context.getCommand().getSetting().toDomain(require));
		}
		else {
			require.update(context.getCommand().getSetting().toDomain(require));
		}
	}
	
	public static interface Require extends ExternalImportSettingDto.Require{
		void insert(ExternalImportSetting setting);
		
		void update(ExternalImportSetting setting);
	}
}
