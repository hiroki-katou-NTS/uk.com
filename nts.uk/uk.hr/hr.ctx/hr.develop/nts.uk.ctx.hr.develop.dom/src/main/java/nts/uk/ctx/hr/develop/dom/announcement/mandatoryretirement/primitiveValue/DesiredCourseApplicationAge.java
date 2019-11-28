package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhpv
 * 希望コース申請年齢
 */
@IntegerRange(min=50, max=98)
public class DesiredCourseApplicationAge extends IntegerPrimitiveValue<DesiredCourseApplicationAge>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DesiredCourseApplicationAge(Integer rawValue) {
		super(rawValue);
	}
}
