package nts.uk.ctx.bs.person.dom.person.contact;

public interface PersonContactRepository {
	
	void add(PersonContact domain);
	
	void update(PersonContact domain);
	
	void delete(String pID);
}
