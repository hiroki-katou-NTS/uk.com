package nts.uk.ctx.bs.employee.dom.workplace_new;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface WorkplaceConfigurationRepository {

	public Optional<WorkplaceConfiguration> getWkpConfig(String companyId);
	
	public void deleteConfiguration(String departmentHistoryId);

}
