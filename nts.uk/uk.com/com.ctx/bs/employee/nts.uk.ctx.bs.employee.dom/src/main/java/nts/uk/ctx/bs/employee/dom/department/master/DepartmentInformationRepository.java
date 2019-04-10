package nts.uk.ctx.bs.employee.dom.department.master;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface DepartmentInformationRepository {

	public List<DepartmentInformation> getAllDepartmentByCompany(String companyId, String depHistId);
	
	public List<DepartmentInformation> getAllActiveDepartmentByCompany(String companyId, String depHistId);
	
	public List<DepartmentInformation> getActiveDepartmentByDepIds(String companyId, String depHistId, List<String> listDepartmentId);
	
	public void addDepartment(DepartmentInformation department);
	
	public void addDepartments(List<DepartmentInformation> listDepartment);

	public void deleteDepartmentInforOfHistory(String companyId, String depHistId);

}
