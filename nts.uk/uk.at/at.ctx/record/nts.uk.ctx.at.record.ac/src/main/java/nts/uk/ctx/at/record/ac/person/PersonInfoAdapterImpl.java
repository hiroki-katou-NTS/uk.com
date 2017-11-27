/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedImport;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;

/**
 * The Class PersonInfoAdapterImpl.
 */
@Stateless
public class PersonInfoAdapterImpl implements PersonInfoAdapter {


	/** The I person info pub. */
	@Inject
	private IPersonInfoPub IPersonInfoPub;

	@Inject
	private EmployeeInfoPub employeeInfoPub;
	//@Inject
	//private IPersonInfoPub IPersonInfoPub;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfoImportedImport getPersonInfo(String employeeId) {
		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(employeeId);
		PersonInfoImportedImport personInfoImported = PersonInfoImportedImport.builder().employeeId(personInfoExport.getEmployeeId())
				.employeeName(personInfoExport.getEmployeeName()).build();
		
		return personInfoImported;
		
	}

	@Override
	public List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid) {
		List<EmpBasicInfoImport> data = employeeInfoPub
				.getListEmpBasicInfo(listSid).stream().map(c ->  this.coverEmpBasicInfoImport(c))
				.collect(Collectors.toList());
		return data;
	}
	
	private EmpBasicInfoImport coverEmpBasicInfoImport(EmpBasicInfoExport empBasicInfoExport) {
		EmpBasicInfoImport empBasicInfoImport = new EmpBasicInfoImport(
				empBasicInfoExport.getEmployeeId(),
				empBasicInfoExport.getPId(),
				empBasicInfoExport.getEmployeeCode(),
				empBasicInfoExport.getPersonName(),
				empBasicInfoExport.getPersonMailAddress(),
				empBasicInfoExport.getCompanyMailAddress(),
				empBasicInfoExport.getEntryDate(),
				empBasicInfoExport.getBirthDay(),
				empBasicInfoExport.getRetiredDate(),
				empBasicInfoExport.getGender()
				);
		return empBasicInfoImport;
	}

}
