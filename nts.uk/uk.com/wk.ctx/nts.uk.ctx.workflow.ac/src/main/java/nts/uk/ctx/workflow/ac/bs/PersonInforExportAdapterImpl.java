package nts.uk.ctx.workflow.ac.bs;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
@Stateless
public class PersonInforExportAdapterImpl implements PersonAdapter{

	@Inject
	private IPersonInfoPub psInfor;
	@Override
	public PersonImport getPersonInfo(String sID) {
		PersonInfoExport data = psInfor.getPersonInfo(sID);
		if(data==null){
			return new PersonImport("", "", "", "");
		}
		return new PersonImport(data.getEmployeeId(), data.getEmployeeCode(),
				data.getBusinessName(),
				"");	
	}

}
