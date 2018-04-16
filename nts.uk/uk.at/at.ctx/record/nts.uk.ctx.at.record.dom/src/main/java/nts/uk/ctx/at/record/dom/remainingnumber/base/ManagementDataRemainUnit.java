package nts.uk.ctx.at.record.dom.remainingnumber.base;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 管理データ残数単位
 * @author HopNT
 *
 */
@HalfIntegerRange(min=0d, max=1d)
public class ManagementDataRemainUnit extends HalfIntegerPrimitiveValue<ManagementDataRemainUnit>{

	private static final long serialVersionUID = 1L;

	public ManagementDataRemainUnit(Double rawValue) {
		super(rawValue);
	}
	
}
