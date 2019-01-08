package nts.uk.ctx.exio.ac.exo.bs.employee;

import java.util.List;
import java.util.stream.Collectors;

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
		return toPersonInfoImport(iPersonInfoPub.getPersonInfo(sID));
	}

	@Override
	public List<PersonInfoImport> listPersonInfor(List<String> listSID) {
		return iPersonInfoPub.listPersonInfor(listSID).stream().map(export -> {
			return toPersonInfoImport(export);
		}).collect(Collectors.toList());
	}

	private PersonInfoImport toPersonInfoImport(PersonInfoExport export) {
		return new PersonInfoImport(export.getPid(), export.getBusinessName(), export.getEntryDate(),
				export.getGender(), export.getBirthDay(), export.getEmployeeId(), export.getEmployeeCode(),
				export.getRetiredDate());
	}
}
