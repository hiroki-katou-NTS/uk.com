package nts.uk.ctx.workflow.ac.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.person.PersonInforExportAdapter;
@Stateless
public class PersonInforExportAdapterImpl implements PersonInforExportAdapter{

	@Inject
	private IPersonInfoPub psInfor;
	@Override
	public String personName(String sID) {
		String data = psInfor.getPersonInfo(sID).getEmployeeName();
		return data;
	}

}
