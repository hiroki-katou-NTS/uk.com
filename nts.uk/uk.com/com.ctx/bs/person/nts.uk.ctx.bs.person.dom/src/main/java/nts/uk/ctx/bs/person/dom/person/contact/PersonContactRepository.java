package nts.uk.ctx.bs.person.dom.person.contact;

import java.util.Optional;

public interface PersonContactRepository {
	
	Optional<PersonContact> getByPId(String perId);
	
	void add(PersonContact domain);
	
	void update(PersonContact domain);
	
	void delete(String pID);
}
