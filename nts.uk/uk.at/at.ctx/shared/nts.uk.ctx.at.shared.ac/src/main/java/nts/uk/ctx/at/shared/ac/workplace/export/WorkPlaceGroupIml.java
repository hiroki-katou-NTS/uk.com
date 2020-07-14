package nts.uk.ctx.at.shared.ac.workplace.export;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmployeeOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
//	職場グループIM
/**
 * 
 * @author kingo
 *
 */
@Stateless
public class WorkPlaceGroupIml implements WorkplaceGroupAdapter{

	@Override
	public List<String> getReferenceableEmployees(GeneralDate referenceDate, String employeeId,
			String workplaceGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeOrganizationImport> getAllEmp(GeneralDate referenceDate, String workplaceGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAllWorkplace(GeneralDate referenceDate, String workplaceGroupId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeOrganizationImport> get(GeneralDate referenceDate, List<String> listEmpId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
