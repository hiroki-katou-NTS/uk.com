package nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck;

import java.util.List;

public interface MasterCheckFixedExtractConditionRepository {

	List<MasterCheckFixedExtractCondition> findAll(List<String> extractConditionIds, boolean useAtr);
	
	List<MasterCheckFixedExtractCondition> getAllFixedMasterCheckConById(String errorAlarmCheckId);
	
	void addMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition);
	
	void updateMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition);
	
	void deleteMasterCheckFixedCondition(String errorAlarmCheckId);

}
