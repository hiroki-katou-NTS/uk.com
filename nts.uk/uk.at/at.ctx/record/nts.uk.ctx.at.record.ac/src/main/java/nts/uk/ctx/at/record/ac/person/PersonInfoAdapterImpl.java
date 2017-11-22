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
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedDto;
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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfoImportedDto getPersonInfo(String employeeId) {
		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(employeeId);
		if (personInfoExport == null) {
			return null;
		}
		PersonInfoImportedDto personInfoImported = PersonInfoImportedDto.builder()
				.employeeId(personInfoExport.getEmployeeId())
				.employeeName(personInfoExport.getEmployeeName())
				.build();
		
		return personInfoImported;
		
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getAllPersonInfo()
	 */
	@Override
	public List<PersonInfoImportedDto> getListPerson(List<String> listPersonId) {		
		return this.employeeInfoPub.getListEmpBasicInfo(listPersonId).stream()
				.map(item -> {					
					return PersonInfoImportedDto.builder()
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

}
