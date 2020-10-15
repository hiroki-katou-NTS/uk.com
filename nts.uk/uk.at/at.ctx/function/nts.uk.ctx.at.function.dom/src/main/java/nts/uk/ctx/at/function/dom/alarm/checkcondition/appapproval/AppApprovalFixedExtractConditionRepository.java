package nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval;

import java.util.List;
import java.util.Optional;

public interface AppApprovalFixedExtractConditionRepository {

	public List<AppApprovalFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr);
	
	public List<AppApprovalFixedExtractCondition> findAll();
	
	public List<AppApprovalFixedExtractCondition> findById(String id);
	
	public Optional<AppApprovalAlarmCheckCondition> findByCodeAndCategory(String companyId, String code, int category);
	
	public void add(AppApprovalFixedExtractCondition domain);
	
	public void update(AppApprovalFixedExtractCondition domain);
	
	public void delete(String id);

}
