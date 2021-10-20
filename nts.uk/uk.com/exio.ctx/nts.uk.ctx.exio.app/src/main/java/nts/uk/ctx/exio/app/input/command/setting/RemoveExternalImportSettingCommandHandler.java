package nts.uk.ctx.exio.app.input.command.setting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RemoveExternalImportSettingCommandHandler extends CommandHandler<RemoveExternalImportSettingCommand> {

	@Inject
	private ExternalImportSettingRepository externalImportSettingRepo;
	
	@Inject
	private ReviseItemRepository reviseItemRepo;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveExternalImportSettingCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		val settingCode = new ExternalImportCode(context.getCommand().getCode());
		
		externalImportSettingRepo.delete(companyId, settingCode);
		reviseItemRepo.delete(companyId, settingCode);
	}

}
