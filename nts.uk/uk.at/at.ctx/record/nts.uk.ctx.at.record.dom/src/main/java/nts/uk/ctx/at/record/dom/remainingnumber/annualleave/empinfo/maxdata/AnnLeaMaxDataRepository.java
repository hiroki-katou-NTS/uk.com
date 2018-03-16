package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

public interface AnnLeaMaxDataRepository {
	
	Optional<AnnualLeaveMaxData> get(String employeId);

}
