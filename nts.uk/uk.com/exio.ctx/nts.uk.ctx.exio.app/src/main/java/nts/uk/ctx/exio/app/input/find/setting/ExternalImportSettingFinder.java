package nts.uk.ctx.exio.app.input.find.setting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportName;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

@Stateless
public class ExternalImportSettingFinder {
	
	public List<ExternalImportSettingListItemDto> findAll() {
		return ExternalImportSettingListItemDto.fromDomain(getDummySettings());
	}
	
	public ExternalImportSettingDto find(String settingCode) {
		return ExternalImportSettingDto.fromDomain(getDummySetting());
	}
	
	
	
	//=============以下ダミーデータ構築=================
	//後日本実装する
	private List<ExternalImportSetting> getDummySettings() {
		List<ExternalImportSetting> result = new ArrayList<ExternalImportSetting>();
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("001"), new ExternalImportName("設定1"), 1, ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("002"), new ExternalImportName("設定2"), 1, ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("003"), new ExternalImportName("設定3"), 1, ImportingMode.INSERT_ONLY, null));
		result.add(new ExternalImportSetting("000000000000-0001", new ExternalImportCode("004"), new ExternalImportName("設定4"), 1, ImportingMode.INSERT_ONLY, null));
		return result;
	}
	
	private ExternalImportSetting getDummySetting() {
		return new ExternalImportSetting("000000000000-0001", new ExternalImportCode("001"), new ExternalImportName("設定1"), 1, ImportingMode.INSERT_ONLY, null);
	}
}
