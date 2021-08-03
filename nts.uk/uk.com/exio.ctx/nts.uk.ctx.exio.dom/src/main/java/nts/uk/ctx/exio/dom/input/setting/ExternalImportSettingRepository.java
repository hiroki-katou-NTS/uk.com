package nts.uk.ctx.exio.dom.input.setting;

import java.util.Optional;

public interface ExternalImportSettingRepository {
	
	void insert(ExternalImportSetting domain);
	
	void update(ExternalImportSetting domain);
	
	void delete(String companyId, ExternalImportCode settingCode);
	
	Optional<ExternalImportSetting> get(String companyId, ExternalImportCode settingCode);
}
