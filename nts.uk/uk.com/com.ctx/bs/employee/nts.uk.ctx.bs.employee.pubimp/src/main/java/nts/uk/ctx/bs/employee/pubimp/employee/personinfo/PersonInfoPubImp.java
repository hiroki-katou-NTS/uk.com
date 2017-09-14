package nts.uk.ctx.bs.employee.pubimp.employee.personinfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.personinfo.JobEntryHistoryDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.personinfo.PersonInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.personinfo.PersonInforPub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

public class PersonInfoPubImp implements PersonInforPub {

	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	@Override
	public PersonInfoDtoExport getPersonInfomation(String employeeId) {
		boolean checkEmpExit = empRepo.getBySid(employeeId).isPresent();
		Employee employee = new Employee();
		PersonInfoDtoExport dto = new PersonInfoDtoExport();
		List<JobEntryHistoryDtoExport> listJobEntryHist = new ArrayList<>();
		if (checkEmpExit) {
			employee = empRepo.getBySid(employeeId).get();
			if (!employee.getListEntryJobHist().isEmpty()) {
				employee.getListEntryJobHist().forEach(c -> {
					
					listJobEntryHist.add(new JobEntryHistoryDtoExport(
							c.getCompanyId(), 
							c.getSId(), 
							c.getHiringType().v(),
							c.getRetirementDate(), 
							c.getJoinDate(), 
							c.getAdoptDate()));
					
				});
				dto.setListJobEntryHist(listJobEntryHist);
			} else {
				dto.setListJobEntryHist(null);
			}

			boolean checkPersonExit = personRepo.getByPersonId(employee.getPId()).isPresent();
			Person person = new Person();
			if (checkPersonExit) {
				person = personRepo.getByPersonId(employee.getPId()).get();
				String businessName = person.getPersonNameGroup().getBusinessName().v();
				if (businessName == null) {
					businessName = person.getPersonNameGroup().getPersonName().v();
				}
				
				dto.setEmployeeCode(employee.getSCd().v());
				dto.setEmployeeName(businessName);
				dto.setEmployeeId(employee.getSId());
				dto.setCompanyMail(employee.getCompanyMail().v());
			}

		}
		return dto;
	}

}
