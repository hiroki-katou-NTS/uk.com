package nts.uk.ctx.exio.dom.input.setting;

import java.util.List;

public interface ExternalImportSettingRepository {
	List<ExternalImportSetting> get(String companyId, String settingCode);
}
