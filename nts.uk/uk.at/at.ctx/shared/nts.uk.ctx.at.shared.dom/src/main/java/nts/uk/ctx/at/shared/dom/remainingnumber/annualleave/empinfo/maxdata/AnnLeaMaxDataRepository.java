package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

public interface AnnLeaMaxDataRepository {
	
	Optional<AnnualLeaveMaxData> get(String employeeId);
	
	void add(AnnualLeaveMaxData maxData);
	
	void update(AnnualLeaveMaxData maxData);
	
	void delete(String employeeId);

}
