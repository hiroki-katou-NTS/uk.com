package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface RervLeaGrantRemDataRepository {

	List<ReserveLeaveGrantRemainingData> find(String employeeId);
	
	List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId);
	
	void add(ReserveLeaveGrantRemainingData data);
	
	void update(ReserveLeaveGrantRemainingData data);
	
	void delete(String employeeId, GeneralDate grantDate);
}
