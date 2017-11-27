package nts.uk.ctx.sys.auth.ac.personinfor;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.bs.person.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.pub.person.PersonPub;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
@Stateless
public class AuthPersonInforImpl implements PersonAdapter{

	@Inject
	private PersonPub personPub;
	
	private PersonImport toImport(PersonInfoExport ex){
		return new PersonImport(ex.getPersonId(), ex.getPersonName(), ex.getBirthDay(), ex.getPMailAddr(), ex.getGender());
	}
	@Override
	public List<PersonImport> findByPersonIds(List<String> personIds) {
		List<PersonImport> data =personPub.findByListId(personIds).stream().map(c -> this.toImport(c)).collect(Collectors.toList());
		
		return data;
	}

}
