package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;


/**
 * The Class TempAbsenceFrameNo.
 */
//休職休業枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class TempAbsenceFrameNo extends DecimalPrimitiveValue<TempAbsenceFrameNo> {
	
	public TempAbsenceFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}

