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
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedImport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

/**
 * The Class PersonInfoAdapterImpl.
 */
@Stateless
public class PersonInfoAdapterImpl implements PersonInfoAdapter {


	/** The I person info pub. */
	@Inject
	private IPersonInfoPub IPersonInfoPub;

	/** The employee info pub. */
	@Inject
	private EmployeeInfoPub employeeInfoPub;
	
//	@Inject
//	private PersonPub personPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfoImportedImport getPersonInfo(String employeeId) {
		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(employeeId);
		if (personInfoExport == null) {
			return null;
		}
		PersonInfoImportedImport personInfoImported = PersonInfoImportedImport.builder()
				.employeeId(personInfoExport.getEmployeeId())
				.employeeName(personInfoExport.getEmployeeName())
				.build();
		
		return personInfoImported;
		
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getAllPersonInfo()
	 */
	@Override
	public List<PersonInfoImportedImport> getListPerson(List<String> listPersonId) {		
		return this.employeeInfoPub.getListEmpBasicInfo(listPersonId).stream()
				.map(item -> {					
					return PersonInfoImportedImport.builder()
							.employeeId(item.getEmployeeId())
							.employeeName(item.getPersonName())
							.build();
				})
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getListPersonInfo(java.util.List)
	 */
	@Override
	public List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid) {
		List<EmpBasicInfoImport> data = employeeInfoPub
				.getListEmpBasicInfo(listSid).stream().map(c ->  this.coverEmpBasicInfoImport(c))
				.collect(Collectors.toList());
		return data;
	}
	
	/**
	 * Cover emp basic info import.
	 *
	 * @param empBasicInfoExport the emp basic info export
	 * @return the emp basic info import
	 */
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

	@Override
	public List<PersonInfoImport> getByListId(List<String> personId) {
		// TODO Auto-generated method stub
		return null;
	}

}
