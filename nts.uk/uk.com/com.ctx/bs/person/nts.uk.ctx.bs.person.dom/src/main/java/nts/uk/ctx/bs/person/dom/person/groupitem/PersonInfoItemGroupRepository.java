package nts.uk.ctx.bs.person.dom.person.groupitem;

public interface PersonInfoItemGroupRepository {
	
	void add(PersonInfoItemGroup personInfoItemGroup);
	
	void update(PersonInfoItemGroup personInfoItemGroup);
	
	void remove(String personInfoItemGroupID);

}
