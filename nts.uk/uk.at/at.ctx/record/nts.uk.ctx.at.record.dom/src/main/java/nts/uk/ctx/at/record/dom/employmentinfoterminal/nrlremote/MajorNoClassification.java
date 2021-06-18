package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author ThanhNX
 *
 *         リモート項目NO
 */
@IntegerRange(min = 1, max = 999)
public class MajorNoClassification extends IntegerPrimitiveValue<MajorNoClassification> {

	private static final long serialVersionUID = 1L;

	public MajorNoClassification(Integer rawValue) {
		super(rawValue);
	}

}
