package nts.uk.ctx.bs.employee.pubimp.person;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

@Stateless
public class PersonInfoPubImp implements IPersonInfoPub{

	@Override
	public PersonInfoExport getPersonInfo(String sID) {
		// TODO Auto-generated method stub
		return null;
	}

}
