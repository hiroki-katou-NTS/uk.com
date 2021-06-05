package nts.uk.ctx.exio.dom.input.validation;

import java.util.List;

import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;

public interface ImportingUserConditionRepository {
	List<ImportingUserCondition> get(String companyId, String settingCode, List<Integer> itemNoList);
}
