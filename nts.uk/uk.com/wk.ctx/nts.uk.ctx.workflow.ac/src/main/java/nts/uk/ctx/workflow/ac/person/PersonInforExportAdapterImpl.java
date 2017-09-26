package nts.uk.ctx.workflow.ac.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.person.PersonInforExportAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.person.PersonInforExportDto;
@Stateless
public class PersonInforExportAdapterImpl implements PersonInforExportAdapter{

	@Inject
	private IPersonInfoPub psInfor;
	@Override
	public PersonInforExportDto getPersonInfo(String sID) {
		PersonInfoExport data = psInfor.getPersonInfo(sID);
		return new PersonInforExportDto(data.getEmployeeId(), data.getEmployeeCode(),
				data.getEmployeeName(),
				data.getCompanyMail());	
	}

}
