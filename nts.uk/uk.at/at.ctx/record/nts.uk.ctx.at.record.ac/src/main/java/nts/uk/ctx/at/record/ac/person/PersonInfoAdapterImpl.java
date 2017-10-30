package nts.uk.ctx.at.record.ac.person;

import javax.ejb.Stateless;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoImportedDto;

@Stateless
public class PersonInfoAdapterImpl implements PersonInfoAdapter {

	//@Inject
	//private IPersonInfoPub IPersonInfoPub;

	@Override
	public PersonInfoImportedDto getPersonInfo(String sID) {
//		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(sID);
//		PersonInfoImportedDto personInfoImported = PersonInfoImportedDto.builder().employeeId(personInfoExport.getEmployeeId())
//				.employeeName(personInfoExport.getEmployeeName()).build();
		
		PersonInfoImportedDto personInfoImported = PersonInfoImportedDto.builder().employeeId("90000000-0000-0000-0000-000000000001").employeeName("Son").build();
		//TODO: mock data.
		return personInfoImported;
		
	}

}
