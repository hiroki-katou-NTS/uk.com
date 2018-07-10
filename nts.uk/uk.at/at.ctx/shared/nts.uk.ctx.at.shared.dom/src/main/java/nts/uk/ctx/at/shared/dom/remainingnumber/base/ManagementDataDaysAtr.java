package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 管理データ日数単位
 * @author HopNT
 *
 */
@HalfIntegerRange(min=0d, max = 1d)
public class ManagementDataDaysAtr extends HalfIntegerPrimitiveValue<ManagementDataDaysAtr> {
	
	private static final long serialVersionUID = 1L;

	public ManagementDataDaysAtr(Double rawValue) {
		super(rawValue);
	}
}
