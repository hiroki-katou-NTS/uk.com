package nts.uk.ctx.bs.employee.pubimp.person;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.pub.person.IPersonPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoOutput;

@Stateless
public class PersonPubImp implements IPersonPub{

	@Override
	public PersonInfoOutput getPersonInfo(String sID) {
		// TODO Auto-generated method stub
		return null;
	}

}
