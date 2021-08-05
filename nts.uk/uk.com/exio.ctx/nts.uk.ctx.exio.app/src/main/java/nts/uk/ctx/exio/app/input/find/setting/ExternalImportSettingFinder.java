package nts.uk.ctx.exio.app.input.find.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.StringifiedValue;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportCsvFileInfo;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.assembly.ExternalImportAssemblyMethod;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingItemMapping;
import nts.uk.ctx.exio.dom.input.setting.assembly.mapping.ImportingMapping;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalImportSettingFinder {
	
	@Inject
	private ExternalImportSettingRequire require;
	
	public List<ExternalImportSettingListItemDto> findAll() {
		val require = this.require.create();
		val settings = require.getSettings(AppContexts.user().companyId());
		return ExternalImportSettingListItemDto.fromDomain(settings);
	}
	
	public ExternalImportSettingDto find(String settingCode) {
		val require = this.require.create();
		val settingOpt = require.getSetting(AppContexts.user().companyId(), new ExternalImportCode(settingCode));
		return ExternalImportSettingDto.fromDomain(require, settingOpt.get());
	}
	
	
	
	//=============以下ダミーデータ構築=================
	//後日本実装する
	private List<ExternalImportSetting> getDummySettings() {
		List<ExternalImportSetting> result = new ArrayList<ExternalImportSetting>();
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("001"), new ExternalImportName("設定1"), ImportingGroupId.valueOf(100), ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("002"), new ExternalImportName("設定2"), ImportingGroupId.valueOf(200), ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("003"), new ExternalImportName("設定3"), ImportingGroupId.valueOf(300), ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("004"), new ExternalImportName("設定4"), ImportingGroupId.valueOf(400), ImportingMode.INSERT_ONLY, null));
		return result;
	}
	
	private ExternalImportSetting getDummySetting() {
		return new ExternalImportSetting("000000000000-0001", new ExternalImportCode("001"), new ExternalImportName("設定1"), ImportingGroupId.valueOf(100), ImportingMode.INSERT_ONLY, getDummyAssemblyMethod());
	}
	
	private ExternalImportAssemblyMethod getDummyAssemblyMethod() {
		val dummyMapping = new ArrayList<ImportingItemMapping>();
		dummyMapping.add(new ImportingItemMapping(1, Optional.empty(), Optional.empty()));
		dummyMapping.add(new ImportingItemMapping(2, Optional.of(1), Optional.empty()));
		dummyMapping.add(new ImportingItemMapping(3, Optional.of(2), Optional.empty()));
		dummyMapping.add(new ImportingItemMapping(4, Optional.empty(), Optional.of(StringifiedValue.of("文字列"))));
		dummyMapping.add(new ImportingItemMapping(5, Optional.empty(), Optional.of(StringifiedValue.of(999))));
		
		return new ExternalImportAssemblyMethod(
				new ExternalImportCsvFileInfo(
						new ExternalImportRowNumber(1), 
						new ExternalImportRowNumber(2)), 
				new ImportingMapping(dummyMapping));
	}
	
	public static interface Require extends ExternalImportSettingDto.Require {
		List<ExternalImportSetting> getSettings(String companyId);
		Optional<ExternalImportSetting> getSetting(String companyId, ExternalImportCode settingCode);
	}
}
