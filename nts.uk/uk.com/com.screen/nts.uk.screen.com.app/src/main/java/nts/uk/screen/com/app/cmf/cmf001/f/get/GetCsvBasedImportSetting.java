package nts.uk.screen.com.app.cmf.cmf001.f.get;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.uk.ctx.exio.app.input.setting.FromCsvBaseSettingToDomainRequireImpl;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.setting.BaseCsvInfoDto;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.FromCsvBaseSettingToDomainRequire;
import nts.uk.shr.com.context.AppContexts;

/**
 * ScreenQuery CSVベースの受入設定を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetCsvBasedImportSetting {
	
	public CsvBasedImportSettingDto get(ExternalImportCode settingCode) {
		
		String companyId = AppContexts.user().companyId();
		Require require = EmbedStopwatch.embed(new RequireImpl(companyId, settingCode));
		
		val setting = require.getExternalImportSetting();
		
		return CsvBasedImportSettingDto.create(require, setting, require.getSampleCsvItems());
	}
	
	public interface Require extends CsvBasedImportSettingDto.RequireCreate {
		
		ExternalImportSetting getExternalImportSetting();
		
		List<BaseCsvInfoDto> getSampleCsvItems();
	}
	
	@Inject
	private FileStorage fileStorage;
	
	@Inject
	private ExternalImportSettingRepository settingRepo;
	
	@Inject
	private ImportableItemsRepository importableItemRepo;

	
	public class RequireImpl implements Require {
		
		private final ExternalImportSetting setting;
		private final FromCsvBaseSettingToDomainRequire csvRequire;
		
		RequireImpl(String companyId, ExternalImportCode settingCode) {
			this.csvRequire = new FromCsvBaseSettingToDomainRequireImpl(fileStorage);
			this.setting = settingRepo.get(Optional.of(csvRequire), companyId, settingCode).get();
		}

		@Override
		public ExternalImportSetting getExternalImportSetting() {
			return setting;
		}

		@Override
		public List<BaseCsvInfoDto> getSampleCsvItems() {
			return csvRequire.createBaseCsvInfo(setting.getCsvFileInfo()).get();
		}
		
		private final NestedMapCache<ImportingDomainId, Integer, ImportableItem> importableItemCache = NestedMapCache.incremental(
				domainId -> importableItemRepo.get(domainId).stream(),
				item -> item.getItemNo());

		@Override
		public ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo) {
			return importableItemCache.get(domainId, itemNo).get();
		}
		
	}
}
