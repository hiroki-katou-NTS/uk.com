package nts.uk.screen.com.app.cmf.cmf001.c.delete;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class Cmf001cDeleteCommandHandler extends CommandHandler<Cmf001cDeleteCommand> {

	@Inject
	private ExternalImportSettingRepository settingRepo;

	@Inject
	private ReviseItemRepository reviseItemRepo;
	
	@Override
	protected void handle(CommandHandlerContext<Cmf001cDeleteCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		ImportingDomainId domainId = ImportingDomainId.valueOf(command.getDomainId());
		
		val setting = settingRepo.get(companyId, command.getExternalImportCode()).get();
		setting.getAssembly(domainId).getMapping().setNoSetting(command.getItemNo());
		
		settingRepo.update(setting);
		reviseItemRepo.delete(companyId, command.getExternalImportCode(), domainId, command.getItemNo());
	}

}
