package nts.uk.ctx.bs.employee.pubimp.employee.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class PersonEmpBasicInfoPubImpl implements PersonEmpBasicInfoPub {

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;
	
	@Inject
	private PersonRepository personRepo;

	@Override
	public List<EmployeeBasicInfoExport> getFromEmployeeIdList(List<String> employeeIds) {

		List<EmployeeDataMngInfo> employeeDataInfoList = empDataRepo.findByListEmployeeId(employeeIds);
		if (employeeDataInfoList.isEmpty()) {
			return new ArrayList<>();
		}
		List<String> personIds = employeeDataInfoList.stream().map(e -> e.getPersonId()).collect(Collectors.toList());
		
		Map<String, Person> personMap = personRepo.getPersonByPersonIds(personIds).stream()
				.collect(Collectors.toMap(x -> x.getPersonId(), x -> x));
		
		if (personMap.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<EmployeeBasicInfoExport> resultList = new ArrayList<>();
		
		for (EmployeeDataMngInfo emp : employeeDataInfoList) {
			Person person = personMap.get(emp.getPersonId());
			
			if (person == null) {
				continue;
			}
			
			//:TODO
			
		}
		
		return resultList;
	}

}
