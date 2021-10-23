package nts.uk.ctx.exio.dom.input.validation.user;

import java.util.Optional;

public interface ImportingUserConditionRepository {
	Optional<ImportingUserCondition> get(String companyId, String settingCode, int itemNo);
}
