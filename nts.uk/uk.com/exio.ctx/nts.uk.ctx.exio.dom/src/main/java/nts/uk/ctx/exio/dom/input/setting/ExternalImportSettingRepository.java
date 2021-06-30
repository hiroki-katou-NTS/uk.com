package nts.uk.ctx.exio.dom.input.setting;

import java.util.Optional;

public interface ExternalImportSettingRepository {
	Optional<ExternalImportSetting> get(String companyId, ExternalImportCode settingCode);
}
