/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.family;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author danpv
 *
 */
@StringMaxLength(19)
public class FamilyId extends StringPrimitiveValue<FamilyId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public FamilyId(String id) {
		super(id);
	}

}
