package nts.uk.screen.com.app.cmf.cmf001.f.save;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmf001fSaveCommandHandler extends CommandHandler<Cmf001fSaveCommand> {

	@Inject
	private TransactionService transaction;
	
	@Inject
	private ExternalImportSettingRepository settingRepo;
	
	@Override
	protected void handle(CommandHandlerContext<Cmf001fSaveCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		transaction.execute(() -> {
			val old = settingRepo.get(companyId, command.getExternalImportCode())
					.get();
			settingRepo.update(context.getCommand().update(old));
		});
		
	}

}
