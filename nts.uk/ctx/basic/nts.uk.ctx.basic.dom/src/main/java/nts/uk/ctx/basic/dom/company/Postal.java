package nts.uk.ctx.basic.dom.company;

import org.eclipse.persistence.internal.jpa.parsing.StringFunctionNode;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(10)
public class Postal extends StringPrimitiveValue<Postal>{
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	/**
	 * constructors
	 * @param rawValue raw value
	 */
	public Postal(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}



}
