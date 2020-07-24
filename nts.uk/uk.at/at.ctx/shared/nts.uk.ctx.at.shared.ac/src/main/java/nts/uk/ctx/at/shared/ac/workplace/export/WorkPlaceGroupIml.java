package nts.uk.ctx.at.shared.ac.workplace.export;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;

/**
 * 職場グループImpl
 * @author HieuLt
 *
 */
@Stateless
public class WorkPlaceGroupIml implements WorkplaceGroupAdapter{

	@Override
	public List<WorkplaceGroupImport> getbySpecWorkplaceGroupID(List<String> lstWorkplaceGroupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkplaceGroupImport> getAllWorkplaces(GeneralDate date, List<String> lstWorkplaceGroupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpOrganizationImport> getGetAllEmployees(GeneralDate date, List<String> lstWorkplaceGroupID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getReferableEmp(GeneralDate date, String empId, List<String> lstWorkplaceGroupID) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
