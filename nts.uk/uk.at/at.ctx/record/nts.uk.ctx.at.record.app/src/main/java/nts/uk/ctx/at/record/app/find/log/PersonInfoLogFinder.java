package nts.uk.ctx.at.record.app.find.log;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;

@Stateless
public class PersonInfoLogFinder {

	@Inject
	private PersonInfoAdapter personInfoAdapter;
	
	public List<EmpBasicInfoImport> getListPersonByListSid(List<String> listSid){
		List<EmpBasicInfoImport> data = personInfoAdapter.getListPersonInfo(listSid);
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
}
