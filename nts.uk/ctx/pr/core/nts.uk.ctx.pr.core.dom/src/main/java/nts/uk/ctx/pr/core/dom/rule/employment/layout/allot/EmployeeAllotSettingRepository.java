package nts.uk.ctx.pr.core.dom.rule.employment.layout.allot;

import java.util.List;
import java.util.Optional;

public interface EmployeeAllotSettingRepository {
	Optional<EmployeeAllotSetting> find(String companyCode, String historyID, String employeeCd);
	
	// Return List
	List<EmployeeAllotSetting> findAll(String companyCode,String historyID);
	List<getEmployeeList> findAllEm(String companyCode,String historyID, String employeeCode);
}
