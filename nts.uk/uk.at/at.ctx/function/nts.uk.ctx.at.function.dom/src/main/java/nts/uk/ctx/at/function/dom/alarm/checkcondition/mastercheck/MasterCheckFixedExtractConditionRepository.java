package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import java.util.List;

public interface MasterCheckFixedExtractConditionRepository {

	List<MasterCheckFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr);
}
