package nts.uk.ctx.exio.app.input.command.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.uk.ctx.exio.app.input.command.setting.SaveExternalImportSettingCommandHandler.Require;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SaveExternalImportSettingCommandRequire {
	
	@Inject
	private ExternalImportSettingRepository externalImportSettingRepo;
	
	@Inject
	private ImportableItemsRepository importableItemsRepo;
	
	public Require create() {
		
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	@RequiredArgsConstructor
	public class RequireImpl implements Require {
		
		@Override
		public List<ImportableItem> getImportableItems(ImportingGroupId groupId) {
			return importableItemsRepo.get(groupId);
		}
		
		@Override
		public Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode) {
			return externalImportSettingRepo.get(companyId, settingCode);
		}
		
		@Override
		public void insert(ExternalImportSetting setting) {
			externalImportSettingRepo.insert(setting);
		}
		
		@Override
		public void update(ExternalImportSetting setting) {
			externalImportSettingRepo.update(setting);
		}
	}
}
