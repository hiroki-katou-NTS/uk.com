/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.groupitem.definition;

/**
 * @author laitv
 *
 */
public interface PersonInfoItemDefGrRepository {

	void add(PersonInfoItemDefGroup personInfoItemDefGroup);

	void update(PersonInfoItemDefGroup personInfoItemDefGroup);

	void remove(String personInfoItemDefinitionID, String personInfoItemGroupID);

}
