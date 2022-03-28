package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 理由コード 
 * 
 */
@StringMaxLength(6)
@ZeroPaddedCode
public class SmileTempAbsenceDataReasonCode extends StringPrimitiveValue<SmileTempAbsenceDataReasonCode>{
	
	private static final long serialVersionUID = 1L;

	public SmileTempAbsenceDataReasonCode(String rawValue) {
		super(rawValue.trim());
	}

}
