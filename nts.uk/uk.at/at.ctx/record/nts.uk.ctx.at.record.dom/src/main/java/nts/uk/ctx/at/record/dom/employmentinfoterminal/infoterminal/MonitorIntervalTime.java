package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * @author ThanhNX
 * 
 *         監視間隔時間
 */
@IntegerMinValue(1)
@IntegerMaxValue(60)
public class MonitorIntervalTime extends IntegerPrimitiveValue<MonitorIntervalTime> {

	private static final long serialVersionUID = 1L;

	public MonitorIntervalTime(Integer rawValue) {
		super(rawValue);
	}

}
