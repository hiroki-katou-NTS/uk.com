package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(2)
/**
 * 
 * @author hieult
 *
 */
public class Latitude extends StringPrimitiveValue<Latitude> {

	public Latitude(String rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;
}
