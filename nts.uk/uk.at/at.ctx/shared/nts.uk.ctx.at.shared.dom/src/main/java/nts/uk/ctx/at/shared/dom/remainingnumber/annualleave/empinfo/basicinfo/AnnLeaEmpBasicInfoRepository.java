package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

public interface AnnLeaEmpBasicInfoRepository {
	
	Optional<AnnualLeaveEmpBasicInfo> get(String employeeId);
	
	List<AnnualLeaveEmpBasicInfo> getList(List<String> employeeIds);
	
	void add(AnnualLeaveEmpBasicInfo baicInfo);
	
	void addAll(List<AnnualLeaveEmpBasicInfo> domains);
	
	void update(AnnualLeaveEmpBasicInfo basicInfo);
	
	void updateAll(List<AnnualLeaveEmpBasicInfo> domains);
	
	void delete(String employeeId);
	List<AnnualLeaveEmpBasicInfo> getAll(String cid, List<String> listEmployeeId);

}
