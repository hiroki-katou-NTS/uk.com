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
	
	Optional<WorkRecordExtractingCondition> getWorkRecordExtraConById(String errorAlarmCheckID);
	
	void addWorkRecordExtraCon(WorkRecordExtractingCondition workRecordExtractingCondition);
	
	void updateWorkRecordExtraCon(WorkRecordExtractingCondition workRecordExtractingCondition);
	
	void deleteWorkRecordExtraCon(String errorAlarmCheckID);
	
}
