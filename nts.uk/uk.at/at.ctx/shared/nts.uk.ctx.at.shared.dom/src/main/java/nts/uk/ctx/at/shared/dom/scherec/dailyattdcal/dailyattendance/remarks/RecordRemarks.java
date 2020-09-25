package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/** 実績の備考 */
@StringMaxLength(200)
public class RecordRemarks extends StringPrimitiveValue<RecordRemarks> {

	/***/
	private static final long serialVersionUID = 1L;

	public RecordRemarks(String rawValue) {
		super(rawValue);
	}

}
