package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import java.util.List;

public interface AppApprovalFixedExtractConditionRepository {

	List<AppApprovalFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr);
}
