package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.Optional;

public interface AnnLeaEmpBasicInfoRepository {
	
	Optional<AnnualLeaveEmpBasicInfo> get(String employeeId);
	
	void add(AnnualLeaveEmpBasicInfo baicInfo);
	
	void update(AnnualLeaveEmpBasicInfo basicInfo);
	
	void delete(String employeeId);

}
