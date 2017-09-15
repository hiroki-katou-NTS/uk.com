package nts.uk.ctx.bs.employee.pubimp.person;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.JobEntryHistoryExport;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class PersonInfoPubImp implements IPersonInfoPub{
	

	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	@Override
	public PersonInfoExport getPersonInfo(String employeeId) {
		boolean checkEmpExit = empRepo.getBySid(employeeId).isPresent();
		Employee employee = new Employee();
		PersonInfoExport dto = new PersonInfoExport();
		List<JobEntryHistoryExport> listJobEntryHist = new ArrayList<>();
		if (checkEmpExit) {
			employee = empRepo.getBySid(employeeId).get();
			if (!employee.getListEntryJobHist().isEmpty()) {
				employee.getListEntryJobHist().forEach(c -> {
					
					listJobEntryHist.add(new JobEntryHistoryExport(
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
