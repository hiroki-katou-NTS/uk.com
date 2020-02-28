package nts.uk.ctx.at.shared.ac.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.person.FullNameSetImport;
import nts.uk.ctx.at.shared.dom.person.PersonAdaptor;
import nts.uk.ctx.at.shared.dom.person.PersonImport;
import nts.uk.ctx.at.shared.dom.person.PersonNameGroupImport;
import nts.uk.ctx.bs.person.pub.person.PersonExport;
import nts.uk.ctx.bs.person.pub.person.PersonNameGroupExport;
import nts.uk.ctx.bs.person.pub.person.PersonPub;

@Stateless
public class PersonAdaptorImpl implements PersonAdaptor {

	@Inject
	private PersonPub perPub;

	@Override
	public List<PersonImport> findByPids(List<String> personIds) {

		return this.perPub.findByPids(personIds).stream().map(x -> toImport(x)).collect(Collectors.toList());
	}

	private PersonImport toImport(PersonExport ex) {

		PersonNameGroupExport group = ex.getPersonNameGroup();

		if (group != null) {

			FullNameSetImport personName = new FullNameSetImport(
					group.getPersonName() == null ? null : group.getPersonName().getFullName(),
					group.getPersonName() == null ? null : group.getPersonName().getFullNameKana());
			FullNameSetImport personalNameMultilingual = new FullNameSetImport(
					group.getPersonalNameMultilingual() == null ? null
							: group.getPersonalNameMultilingual().getFullName(),
					group.getPersonalNameMultilingual() == null ? null
							: group.getPersonalNameMultilingual().getFullNameKana());
			FullNameSetImport personRomanji = new FullNameSetImport(  
					group.getPersonRomanji() == null ? null : group.getPersonRomanji().getFullName(),
					group.getPersonRomanji() == null ? null : group.getPersonRomanji().getFullNameKana());
			FullNameSetImport todokedeFullName = new FullNameSetImport(
					group.getTodokedeFullName() == null ? null : group.getTodokedeFullName().getFullName(),
					group.getTodokedeFullName() == null ? null : group.getTodokedeFullName().getFullNameKana());
			FullNameSetImport oldName = new FullNameSetImport(
					group.getOldName() == null ? null : group.getOldName().getFullName(),
					group.getOldName() == null ? null : group.getOldName().getFullNameKana());

			PersonNameGroupImport groupExport = new PersonNameGroupImport(group.getBusinessName(),
					group.getBusinessNameKana(), group.getBusinessOtherName(), group.getBusinessEnglishName(),
					personName, personalNameMultilingual, personRomanji, todokedeFullName, oldName);

			return new PersonImport(ex.getBirthDate(), ex.getGender(), ex.getPersonId(), groupExport);
		}

		return new PersonImport(ex.getBirthDate(), ex.getGender(), ex.getPersonId(), null);
	}

}
