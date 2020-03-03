/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.ac.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.department.master.AffDpmHistItemExport;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentPub;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.WkpCdNameExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;

/**
 * The Class WorkplaceApproverAdaptorImpl.
 */
@Stateless
public class WorkplaceApproverAdaptorImpl implements WorkplaceApproverAdapter {

	/** The wp pub. */
//	@Inject
//	private SyWorkplacePub wpPub;
	@Inject
	private DepartmentPub depPub;
	
	@Inject
	private WorkplacePub wkpPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.workplace.
	 * WorkplaceApproverAdaptor#findByWkpId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkplaceImport> findByWkpId(String workplaceId, GeneralDate baseDate) {
		Optional<WkpCdNameExport> optWkpCdNameExport = this.wkpPub.findByWkpId(workplaceId);

		// Check exist
		if (!optWkpCdNameExport.isPresent()) {
			return Optional.empty();
		}

		// Return
		WkpCdNameExport x = optWkpCdNameExport.get();
		return Optional.of(new WorkplaceImport(workplaceId, x.getWkpCode(), x.getWkpName()));
	}

	/**
	 * Find by sid.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the optional
	 */
	// RequestList #30
	@Override
	public WorkplaceImport findBySid(String employeeId, GeneralDate baseDate) {

		Optional<SWkpHistExport> dataOptional = wkpPub.findBySid(employeeId, baseDate);

		if(!dataOptional.isPresent()) {
			return new WorkplaceImport("", "", "コード削除済");
		}
		SWkpHistExport data = dataOptional.get();
		WorkplaceImport result = new WorkplaceImport(data.getWorkplaceId(), data.getWorkplaceCode(), data.getWorkplaceName());
		return result;
	}

	@Override
	public Optional<WkpDepInfo> findByWkpIdNEW(String companyId, String wkpId, GeneralDate baseDate) {
		return wkpPub.findByWkpIdNew(companyId, wkpId, baseDate)
				.map(c -> new WkpDepInfo(c.getWorkplaceId(), c.getWorkplaceCode(), c.getWorkplaceName()));
	}

	@Override
	public Optional<WkpDepInfo> findByDepIdNEW(String companyId, String depId, GeneralDate baseDate) {
		return depPub.getInfoDep(companyId, depId, baseDate)
				.map(c -> new WkpDepInfo(c.getDepartmentId(), c.getDepartmentCode(), c.getDepartmentName()));
	}
	
	@Override
	public String getDepartmentIDByEmpDate(String employeeID, GeneralDate date) {
		AffDpmHistItemExport ob = depPub.getDepartmentHistItemByEmpDate(employeeID, date);
		if(ob == null) {
			throw new BusinessException(new RawErrorMessage(employeeID + "の部門はまだ設定しない。"));
		}		
		return ob.getDepartmentId();
	}

	@Override
	public List<String> getUpperDepartment(String companyID, String departmentID, GeneralDate date) {
		return depPub.getUpperDepartment(companyID, departmentID, date);
	}
	
	@Override
	public List<String> getDepartmentIDAndUpper(String companyID, String departmentID, GeneralDate date) {
		return depPub.getDepartmentIDAndUpper(companyID, departmentID, date);
	}

	@Override
	public String getWorkplaceIDByEmpDate(String employeeID, GeneralDate date) {
		return wkpPub.getAffWkpHistItemByEmpDate(employeeID, date).getWorkplaceId();
	}

	@Override
	public List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date) {
		return wkpPub.getUpperWorkplace(companyID, workplaceID, date);
	}

	@Override
	public List<String> getWorkplaceIdAndUpper(String companyId, String workplaceID, GeneralDate baseDate) {
		return wkpPub.getWorkplaceIdAndUpper(companyId, baseDate, workplaceID);
	}
}
