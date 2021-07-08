package nts.uk.ctx.exio.dom.input.validation.user;

import java.util.List;

public interface ImportingUserConditionRepository {
	List<ImportingUserCondition> get(String companyId, String settingCode, List<Integer> itemNoList);
}
