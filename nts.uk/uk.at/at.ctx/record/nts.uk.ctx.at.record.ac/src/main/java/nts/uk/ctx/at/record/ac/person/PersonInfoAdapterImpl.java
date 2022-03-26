/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
//import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpBasicInfoExport;
//import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.person.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.pub.person.PersonPub;

/**
 * The Class PersonInfoAdapterImpl.
 */
@Stateless
public class PersonInfoAdapterImpl implements PersonInfoAdapter {

	/** The employee info pub. */
//	@Inject
//	private EmployeeInfoPub employeeInfoPub;
	
	/** The person pub. */
	@Inject
	private PersonPub personPub;
	
	@Inject
	private SyEmployeePub syEmployeePub;
	
	@Inject
	private IPersonInfoPub iPersonInfoPub;
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getPersonInfo(java.lang.String)
	 */
	@Override
	public PersonInfoImport getPersonInfo(String employeeId) {
		List<String> listId = new ArrayList<>();
		listId.add(employeeId);
		
		List<PersonInfoExport> result = this.personPub.findByListId(listId);
		if (result.isEmpty()) {
			return null;
		}

		return PersonInfoImport.builder()
				.personId(result.get(0).getPersonId())
				.personName(result.get(0).getPersonName())
				.build();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getAllPersonInfo()
	 */
	@Override
	public List<PersonInfoImport> getByListId(List<String> listPersonId) {		
		return this.personPub.findByListId(listPersonId).stream()
				.map(item -> {					
					return PersonInfoImport.builder()
							.personId(item.getPersonId())
							.personName(item.getPersonName())
							.build();
				})
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter#getListPersonInfo(java.util.List)
	 */
	@Override
	public List<EmpBasicInfoImport> getListPersonInfo(List<String> listSid) {
		List<EmpBasicInfoImport> data1 = new ArrayList<>();
		if (listSid.isEmpty()) {
			return data1;
		}
		List<EmployeeBasicInfoExport> data = syEmployeePub.findBySIds(listSid);
		if(data.isEmpty()){
			return data1;
		}
		data1 = data.stream().map(c->coverEmpBasicInfoImport(c)).collect(Collectors.toList());
		List<String> listPid = data.stream().map(c->c.getPId()).collect(Collectors.toList());
		List<PersonInfoImport> listPerson = this.getByListId(listPid);
		for(EmpBasicInfoImport empBasicInfoImport:data1) {
			for(PersonInfoImport personInfoImport :listPerson) {
				if(empBasicInfoImport.getPId().equals(personInfoImport.getPersonId()))
					empBasicInfoImport.setNamePerson(personInfoImport.getPersonName());
			}
		}
		return data1;
	}
	
	/**
	 * Cover emp basic info import.
	 *
	 * @param empBasicInfoExport the emp basic info export
	 * @return the emp basic info import
	 */
	private EmpBasicInfoImport coverEmpBasicInfoImport(EmployeeBasicInfoExport empBasicInfoExport) {
		EmpBasicInfoImport empBasicInfoImport = new EmpBasicInfoImport(
				empBasicInfoExport.getPId(),
				null,
				empBasicInfoExport.getEmployeeId(),
				empBasicInfoExport.getPName(),
				empBasicInfoExport.getGender(),
				empBasicInfoExport.getBirthDay(),
				empBasicInfoExport.getEmployeeCode(),
				empBasicInfoExport.getEntryDate(),
				empBasicInfoExport.getRetiredDate()
				);
		return empBasicInfoImport;
	}

	@Override
	public EmpBasicInfoImport getEmpBasicInfoImport(String sID) {
		nts.uk.ctx.bs.employee.pub.person.PersonInfoExport personInfoExport = iPersonInfoPub.getPersonInfo(sID);
		return new EmpBasicInfoImport(
				personInfoExport.getPid(), 
				personInfoExport.getBusinessName(), 
				personInfoExport.getEmployeeId(), 
				personInfoExport.getBusinessName(), 
				personInfoExport.getGender(), 
				personInfoExport.getBirthDay(), 
				personInfoExport.getEmployeeCode(), 
				personInfoExport.getEntryDate(), 
				personInfoExport.getRetiredDate());
	}

}
