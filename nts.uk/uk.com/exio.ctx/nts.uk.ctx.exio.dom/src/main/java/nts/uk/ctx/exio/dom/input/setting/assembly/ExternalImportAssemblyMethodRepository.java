package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

public interface ExternalImportAssemblyMethodRepository {
	Optional<ExternalImportAssemblyMethod> get(String companyId, ExternalImportCode settingCode);
}
