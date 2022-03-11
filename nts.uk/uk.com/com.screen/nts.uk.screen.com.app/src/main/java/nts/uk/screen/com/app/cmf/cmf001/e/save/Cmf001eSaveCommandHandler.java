package nts.uk.screen.com.app.cmf.cmf001.e.save;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.exio.app.input.setting.FromCsvBaseSettingToDomainRequireImpl;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.FromCsvBaseSettingToDomainRequire;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmf001eSaveCommandHandler extends CommandHandler<Cmf001eSaveCommand>{

	@Inject
	private ExternalImportSettingRepository externalImportSettingRepo;
	
	@Inject
	private FileStorage fileStorage;
	
	@Inject
	private TransactionService transaction;
	
	@Override
	protected void handle(CommandHandlerContext<Cmf001eSaveCommand> context) {

		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		//新規モード
		if(command.isNew()) {
			//コードの重複チェック
			if(externalImportSettingRepo.exist(companyId, command.getCode())) {
				throw new BusinessException("Msg_3");
			}
			val domain = command.toDomain(Optional.empty());
			
			transaction.execute(() -> {
				externalImportSettingRepo.insert(domain);
			});
			
		} else {
			val csvRequire = new FromCsvBaseSettingToDomainRequireImpl(fileStorage);
			
			transaction.execute(() -> {
				val old = externalImportSettingRepo.get(Optional.of(csvRequire), companyId, command.getCode());
				val domain = command.toDomain(old);
				externalImportSettingRepo.update(domain);
			});
		}
	}

}
