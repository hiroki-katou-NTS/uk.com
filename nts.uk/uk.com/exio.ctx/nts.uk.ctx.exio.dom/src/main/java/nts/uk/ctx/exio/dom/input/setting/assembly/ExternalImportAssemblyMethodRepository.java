package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.Optional;

public interface ExternalImportAssemblyMethodRepository {
	Optional<ExternalImportAssemblyMethod> get(String companyId, String settingCode);
}
