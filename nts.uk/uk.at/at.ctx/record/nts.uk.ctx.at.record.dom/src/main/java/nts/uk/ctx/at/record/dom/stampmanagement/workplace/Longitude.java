package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
/**
 * 
 * @author hieult
 *
 */
public class Longitude extends StringPrimitiveValue<Longitude> {

	private static final long serialVersionUID = 1L;

	public Longitude(String rawValue) {
		super(rawValue);
	}
}
