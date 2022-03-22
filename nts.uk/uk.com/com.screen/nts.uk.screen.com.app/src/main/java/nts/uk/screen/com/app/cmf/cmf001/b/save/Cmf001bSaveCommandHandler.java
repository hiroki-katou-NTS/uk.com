package nts.uk.screen.com.app.cmf.cmf001.b.save;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.TransactionService;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.screen.com.app.cmf.cmf001.b.get.ExternalImportSettingDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmf001bSaveCommandHandler extends CommandHandler<Cmf001bSaveCommand>{
	
	@Override
	protected void handle(CommandHandlerContext<Cmf001bSaveCommand> context) {

		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		val settingDto = command.getSetting();
		
		Require require = EmbedStopwatch.embed(new RequireImpl(companyId));

		//新規モード
		if(command.isNew()) {
			//コードの重複チェック
			if(externalImportSettingRepo.exist(companyId, command.getCode())) {
				throw new BusinessException("Msg_3");
			}
			val setting = settingDto.toDomainAsDomainBase(require, Optional.empty());
			
			transaction.execute(() -> {
				externalImportSettingRepo.insert(setting);
			});
		} else {
			val oldSetting = externalImportSettingRepo.get(companyId, command.getCode());
			val setting = settingDto.toDomainAsDomainBase(require, oldSetting);
			
			transaction.execute(() -> {
				externalImportSettingRepo.update(setting);
			});
		}
	}
	
	@Inject
	private TransactionService transaction;

	@Inject
	private ExternalImportSettingRepository externalImportSettingRepo;
	
	@Inject
	private ReviseItemRepository reviseItemRepo;
	
	public interface Require extends ExternalImportSettingDto.RequireToDomain {
		
	}
	
	@RequiredArgsConstructor
	public class RequireImpl implements Require {
		
		private final String companyId;

		@Override
		public void deleteReviseItems(
				ExternalImportCode settingCode,
				ImportingDomainId domainId,
				List<Integer> itemNos) {
			
			reviseItemRepo.delete(companyId, settingCode, domainId, itemNos);
		}
		
	}

}
