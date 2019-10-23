package nts.uk.ctx.bs.person.dom.person.contact;

import java.util.List;
import java.util.Optional;

public interface PersonContactRepository {
	
	Optional<PersonContact> getByPId(String perId);
	
	List<PersonContact> getByPersonIdList(List<String> personIds);
	
	void add(PersonContact domain);
	
	void update(PersonContact domain);
	
	void delete(String pID);
}
