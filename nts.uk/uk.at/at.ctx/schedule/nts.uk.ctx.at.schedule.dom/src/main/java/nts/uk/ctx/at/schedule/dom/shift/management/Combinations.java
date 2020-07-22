package nts.uk.ctx.at.schedule.dom.shift.management;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;

/**
 * シフトパレットのシフト
 * 
 * @author phongtq
 *
 */

public class Combinations implements DomainValue {

	/** 順番 */
	@Getter
	private final int order;

	@Getter
	/** シフトコード */
	private ShiftMasterCode shiftCode;

	public Combinations(int order, ShiftMasterCode shiftCode) {

		// inv-1 1 <= 順番 <= 31
		if (!(1 <= order && order <= 31)) {
			throw new BusinessException("Msg_1626");
		}
		this.order = order;
		this.shiftCode = shiftCode;
	}
}
