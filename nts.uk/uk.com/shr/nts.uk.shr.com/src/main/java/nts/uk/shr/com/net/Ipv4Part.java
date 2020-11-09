package nts.uk.shr.com.net;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min=0,max=255)
public class Ipv4Part extends IntegerPrimitiveValue<Ipv4Part> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public Ipv4Part(int rawValue) {
		super(rawValue);
	}
}
