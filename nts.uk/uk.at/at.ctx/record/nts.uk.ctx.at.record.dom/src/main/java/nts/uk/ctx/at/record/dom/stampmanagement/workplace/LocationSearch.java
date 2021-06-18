package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/** 所在地 **/
@StringMaxLength(140)

public class LocationSearch extends StringPrimitiveValue<LocationSearch> {

	public LocationSearch(String rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}