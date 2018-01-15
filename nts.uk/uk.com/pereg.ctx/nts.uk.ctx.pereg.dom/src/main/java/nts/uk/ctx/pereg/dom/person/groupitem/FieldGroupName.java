/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.groupitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author laitv
 *
 */
@StringCharType(CharType.ALPHABET)
@StringMaxLength(30)
public class FieldGroupName extends StringPrimitiveValue<FieldGroupName>{

	private static final long serialVersionUID = 1L;
	
	public FieldGroupName(String rawValue) {
		super(rawValue);
	}

}
