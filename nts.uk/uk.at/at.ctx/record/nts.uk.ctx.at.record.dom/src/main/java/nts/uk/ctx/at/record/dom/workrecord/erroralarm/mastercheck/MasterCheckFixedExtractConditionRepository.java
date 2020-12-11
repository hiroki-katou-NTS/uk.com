package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

import java.util.List;

public interface MasterCheckFixedExtractConditionRepository {

	List<MasterCheckFixedExtractCondition> findAll(String extractConditionIds, boolean useAtr);
	
	List<MasterCheckFixedExtractCondition> getAllFixedMasterCheckConById(String errorAlarmCheckId);
	
	void addMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition);
	
	void updateMasterCheckFixedCondition(MasterCheckFixedExtractCondition masterCheckFixedCondition);
	
	void deleteMasterCheckFixedCondition(String errorAlarmCheckId);
	
	void persist(List<MasterCheckFixedExtractCondition> condition);

}
