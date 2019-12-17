package nts.uk.ctx.at.shared.app.find.person;

import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.person.PersonAdaptor;
import nts.uk.ctx.at.shared.dom.person.PersonImport;

public class PersonFinder {
	@Inject
	private PersonAdaptor personAdaptor;
	
	public List<PersonImport> findByPids(List<String> personIds) {
		return this.personAdaptor.findByPids(personIds);

	}
}
