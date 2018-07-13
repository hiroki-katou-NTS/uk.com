package nts.uk.ctx.exio.ac.exo.bs.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoAdapter;
import nts.uk.ctx.exio.dom.exo.adapter.bs.employee.PersonInfoImport;

@Stateless
public class PersonInfoAcFinder implements PersonInfoAdapter {
	@Inject
	private IPersonInfoPub iPersonInfoPub;

	@Override
	public PersonInfoImport getPersonInfo(String sID) {
		PersonInfoExport person = iPersonInfoPub.getPersonInfo(sID);
		return new PersonInfoImport(person.getPid(), person.getBusinessName(), person.getEntryDate(),
				person.getGender(), person.getBirthDay(), person.getEmployeeId(), person.getEmployeeCode(),
				person.getRetiredDate());
	}

}
