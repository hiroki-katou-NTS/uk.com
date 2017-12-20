/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.MailAddress;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

/**
 * The Class EmpEmployeeAdapterImpl.
 */
@Stateless
public class EmpEmployeeAdapterImpl implements EmpEmployeeAdapter {

	/** The employee pub. */
	@Inject
	private SyEmployeePub employeePub;

	// @Inject
	// private PersonPub personPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter#findByEmpId(java.lang.String)
	 */
	@Override
	public EmployeeImport findByEmpId(String empId) {
		// Get Employee Basic Info
		EmployeeBasicInfoExport empExport = employeePub.findBySId(empId);
		// Check Null
		if (empExport != null) {
			// Map to EmployeeImport
			EmployeeImport empDto = EmployeeImport.builder()
					.employeeId(empExport.getEmployeeId())
					.employeeCode(empExport.getEmployeeCode())
					.employeeName(empExport.getPName())
					.employeeMailAddress(
							empExport.getPMailAddr() == null ? null : (new MailAddress(empExport.getPMailAddr().v())))
					.entryDate(empExport.getEntryDate())
					.retiredDate(empExport.getRetiredDate())
					.build();
			return empDto;
		}
		return null;
	}

}
