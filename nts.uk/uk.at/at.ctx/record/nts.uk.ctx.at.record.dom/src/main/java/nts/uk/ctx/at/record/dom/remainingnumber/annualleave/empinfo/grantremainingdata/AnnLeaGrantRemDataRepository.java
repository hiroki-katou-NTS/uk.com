package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

public interface AnnLeaGrantRemDataRepository {
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId);

}
