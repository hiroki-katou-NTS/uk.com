package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.Optional;

public interface AnnLeaGrantRemDataRepository {
	
	Optional<AnnualLeaveGrantRemainingData> get(String employeeId);

}
