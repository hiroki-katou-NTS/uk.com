package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RervLeaGrantRemDataRepository {

	List<ReserveLeaveGrantRemainingData> find(String employeeId, String cId);
	
	List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId, String cId);
	
	List<ReserveLeaveGrantRemainingData> find(String employeeId, GeneralDate grantDate);
	
	Optional<ReserveLeaveGrantRemainingData> getById(String id);
	
	void add(ReserveLeaveGrantRemainingData data, String cId);
	
	void update(ReserveLeaveGrantRemainingData data);
	
	void delete(String rsvLeaId);
	
	void deleteAfterDate(String employeeId, GeneralDate date);
	
}
