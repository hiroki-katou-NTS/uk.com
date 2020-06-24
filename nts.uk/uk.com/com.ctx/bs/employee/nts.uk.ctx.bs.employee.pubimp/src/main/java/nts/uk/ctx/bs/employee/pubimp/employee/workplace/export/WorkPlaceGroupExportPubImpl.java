package nts.uk.ctx.bs.employee.pubimp.employee.workplace.export;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.EmployeeOrganizationExport;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkPlaceGroupExportPub;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkplaceGroupExport;

/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class WorkPlaceGroupExportPubImpl implements WorkPlaceGroupExportPub {

	@Override
	public List<String> getReferenceableEmployees(GeneralDate referenceDate, String employeeId,
			String workplaceGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeOrganizationExport> getAllEmp(GeneralDate referenceDate, String workplaceGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAllWorkplace(GeneralDate referenceDate, String workplaceGroupId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<WorkplaceGroupExport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
		// TODO Auto-generated method stub
		return null;
	}

}
