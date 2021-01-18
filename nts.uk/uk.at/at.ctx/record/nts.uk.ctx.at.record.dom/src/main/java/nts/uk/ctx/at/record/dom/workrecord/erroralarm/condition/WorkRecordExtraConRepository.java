package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;
/**
 * 
 * @author tutk
 *
 */

import java.util.List;
import java.util.Optional;

public interface WorkRecordExtraConRepository {
	
	List<WorkRecordExtractingCondition> getAllWorkRecordExtraCon();
	
	List<WorkRecordExtractingCondition> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID );
	
	// get 勤務実績の抽出条件 WorkRecordExtractingCondition by ID and useAtr
	List<WorkRecordExtractingCondition> getAllWorkRecordExtraConByIdAndUse(List<String> listErrorAlarmID, int use);
	
	Optional<WorkRecordExtractingCondition> getWorkRecordExtraConById(String errorAlarmCheckID);
	
	void addWorkRecordExtraCon(WorkRecordExtractingCondition workRecordExtractingCondition);
	
	void updateWorkRecordExtraCon(WorkRecordExtractingCondition workRecordExtractingCondition);
	
	void deleteWorkRecordExtraCon(String errorAlarmCheckID);
	
}
