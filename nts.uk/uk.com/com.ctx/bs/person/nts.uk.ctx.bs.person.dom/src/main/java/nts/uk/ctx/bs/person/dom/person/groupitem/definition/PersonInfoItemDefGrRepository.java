/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.groupitem.definition;

/**
 * @author laitv
 *
 */
public interface PersonInfoItemDefGrRepository {

	void add(PersonInfoItemDefGroup personInfoItemDefGroup);

	void update(PersonInfoItemDefGroup personInfoItemDefGroup);

	void remove(String personInfoItemDefinitionID, String personInfoItemGroupID);

}
