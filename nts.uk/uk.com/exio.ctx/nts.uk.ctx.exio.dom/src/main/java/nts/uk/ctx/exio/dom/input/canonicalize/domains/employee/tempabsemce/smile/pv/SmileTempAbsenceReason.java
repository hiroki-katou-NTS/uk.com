package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * Smile休職情報 - 休職理由
 */
@StringMaxLength(32)
public class SmileTempAbsenceReason extends StringPrimitiveValue<SmileTempAbsenceReason>{
	
	private static final long serialVersionUID = 1L;

	public SmileTempAbsenceReason(String rawValue) {
		super(rawValue);
	}
}
