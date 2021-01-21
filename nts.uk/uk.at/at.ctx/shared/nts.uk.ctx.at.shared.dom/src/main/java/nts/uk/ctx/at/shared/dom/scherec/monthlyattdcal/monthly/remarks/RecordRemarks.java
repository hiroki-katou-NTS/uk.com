package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author phongtq
 *
 */
@StringMaxLength(50)
public class RecordRemarks extends StringPrimitiveValue<RecordRemarks> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordRemarks(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
