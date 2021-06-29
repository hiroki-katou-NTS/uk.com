package nts.uk.ctx.exio.dom.input.revise;

import java.util.Optional;

public interface ReviseItemRepository {
	Optional<ReviseItem> get(String companyId, int settingCode, int importItemNumber);
}
