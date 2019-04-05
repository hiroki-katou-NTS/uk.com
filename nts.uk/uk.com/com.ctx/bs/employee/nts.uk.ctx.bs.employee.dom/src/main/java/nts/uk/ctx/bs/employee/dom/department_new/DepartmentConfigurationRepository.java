package nts.uk.ctx.bs.employee.dom.department_new;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface DepartmentConfigurationRepository {

	public Optional<DepartmentConfiguration> getDepConfig(String companyId);
	
	public void deleteConfiguration(String departmentHistoryId);

}
