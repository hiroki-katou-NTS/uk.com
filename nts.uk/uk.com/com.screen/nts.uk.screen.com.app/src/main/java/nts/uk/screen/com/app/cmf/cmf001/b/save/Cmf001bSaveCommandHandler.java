package nts.uk.screen.com.app.cmf.cmf001.b.save;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingDto;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmf001bSaveCommandHandler extends CommandHandler<Cmf001bSaveCommand>{
	
	@Inject
	private Cmf001bSaveCommandRequire require;
	
	@Override
	protected void handle(CommandHandlerContext<Cmf001bSaveCommand> context) {
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
