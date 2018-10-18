package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface AnnLeaGrantRemDataRepository {
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId);
	
	Optional<AnnualLeaveGrantRemainingData> getLast(String employeeId);
	
	Optional<AnnualLeaveGrantRemainingData> findByID(String id);
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId, GeneralDate grantDate);
	
	List<AnnualLeaveGrantRemainingData> findByCheckState(String employeeId, int checkState);
	
	List<AnnualLeaveGrantRemainingData> findNotExp(String employeeId);
	
	void add(AnnualLeaveGrantRemainingData data);
	
	void update(AnnualLeaveGrantRemainingData data);
	
	void delete(String employeeID, GeneralDate grantDate);
	
	void deleteAfterDate(String employeeId, GeneralDate date);
	
	void delete(String annaLeavID);
	
	List<AnnualLeaveGrantRemainingData> checkConditionUniqueForAdd(String employeeId, GeneralDate grantDate);
	
	List<AnnualLeaveGrantRemainingData> checkConditionUniqueForUpdate(String employeeId, String annLeavID, GeneralDate grantDate);

}
