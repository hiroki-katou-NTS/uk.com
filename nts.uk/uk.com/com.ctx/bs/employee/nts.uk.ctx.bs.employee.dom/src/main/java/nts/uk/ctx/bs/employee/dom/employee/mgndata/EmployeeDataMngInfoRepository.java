package nts.uk.ctx.bs.employee.dom.employee.mgndata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface EmployeeDataMngInfoRepository {
	void add(EmployeeDataMngInfo domain);

	void update(EmployeeDataMngInfo domain);

	void remove(EmployeeDataMngInfo domain);

	void remove(String sid, String pId);

	EmployeeDataMngInfo findById(String sid, String pId);

	// Lanlt code start
	
	Optional<EmployeeInfo> findById(String sid);
	
	Optional<EmployeeInfo> getDepartment(String departmentId, GeneralDate date);

	// Lanlt code end
	List<EmployeeDataMngInfo> findByEmployeeId(String sid);

	List<EmployeeDataMngInfo> findByPersonId(String pid);

	List<EmployeeDataMngInfo> findByCompanyId(String cid);

	// sonnlb code start

	List<EmployeeDataMngInfo> getEmployeeNotDeleteInCompany(String cId, String sCd);

	// sonnlb code end

	void updateRemoveReason(EmployeeDataMngInfo domain);
}
