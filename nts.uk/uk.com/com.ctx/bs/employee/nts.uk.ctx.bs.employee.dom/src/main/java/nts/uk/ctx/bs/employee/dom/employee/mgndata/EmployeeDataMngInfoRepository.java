package nts.uk.ctx.bs.employee.dom.employee.mgndata;

import java.util.List;

public interface EmployeeDataMngInfoRepository {
	void add(EmployeeDataMngInfo domain);

	void update(EmployeeDataMngInfo domain);

	void remove(EmployeeDataMngInfo domain);

	void remove(String sid, String pId);

	EmployeeDataMngInfo findById(String sid, String pId);
	
	List<EmployeeDataMngInfo> findByEmployeeId(String sid);

	List<EmployeeDataMngInfo> findByPersonId(String pid);

	List<EmployeeDataMngInfo> findByCompanyId(String cid);

	// sonnlb code start

	List<EmployeeDataMngInfo> getEmployeeNotDeleteInCompany(String cId, String sCd);

	// sonnlb code end
	
	void updateRemoveReason(EmployeeDataMngInfo domain);
	
	List<EmployeeDataMngInfo> getListEmpToDelete(String cid);
}
