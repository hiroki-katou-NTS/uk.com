package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AnnLeaGrantRemDataRepository {
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId);
	
	List<AnnualLeaveGrantRemainingData> findByCheckState(String employeeId, Boolean checkState);
	
	void add(AnnualLeaveGrantRemainingData data);
	
	void update(AnnualLeaveGrantRemainingData data);
	
	void delete(String employeeID, GeneralDate grantDate);

}
