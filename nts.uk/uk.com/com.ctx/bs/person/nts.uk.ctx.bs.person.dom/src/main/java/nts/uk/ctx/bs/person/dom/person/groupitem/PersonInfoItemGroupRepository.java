package nts.uk.ctx.bs.person.dom.person.groupitem;

import java.util.List;

public interface PersonInfoItemGroupRepository {
	
	void add(PersonInfoItemGroup personInfoItemGroup);
	
	void update(PersonInfoItemGroup personInfoItemGroup);
	
	void remove(String personInfoItemGroupID);
	
	List<PersonInfoItemGroup> getAllPersonInfoItemGroup();

}
