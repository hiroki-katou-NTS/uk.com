package nts.uk.ctx.at.record.dom.stamp.application;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 
 * @author chungnt
 *
 */

public class MapAddress extends StringPrimitiveValue<PrimitiveValue<String>>{
	private static final long serialVersionUID = 1L;
	
	public MapAddress(String rawValue) {
		super(rawValue);
	}

}
