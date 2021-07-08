package nts.uk.ctx.exio.dom.input.setting.assembly.revise;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;

public interface ReviseItemRepository {
	Optional<ReviseItem> get(String companyId, ExternalImportCode settingCode, int importItemNumber);
}
