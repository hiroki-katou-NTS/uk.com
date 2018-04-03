package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

public interface RervLeaGrantRemDataRepository {

	List<ReserveLeaveGrantRemainingData> find(String employeeId, String cId);
	
	List<ReserveLeaveGrantRemainingData> findNotExp(String employeeId, String cId);
	
	Optional<ReserveLeaveGrantRemainingData> getById(String id);
	
	void add(ReserveLeaveGrantRemainingData data, String cId);
	
	void update(ReserveLeaveGrantRemainingData data);
	
	void delete(String rsvLeaId);
}
