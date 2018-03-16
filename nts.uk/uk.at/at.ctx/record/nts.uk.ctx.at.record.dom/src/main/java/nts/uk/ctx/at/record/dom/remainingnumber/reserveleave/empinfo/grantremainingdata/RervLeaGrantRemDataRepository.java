package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata;

import java.util.Optional;

public interface RervLeaGrantRemDataRepository {
	
	Optional<ReserveLeaveGrantRemainingData> get(String employeeId);

}
