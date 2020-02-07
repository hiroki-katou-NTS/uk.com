package nts.uk.ctx.at.schedule.dom.shift.management;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.error.BusinessException;

/**
 * シフトパレットのシフト		
 * @author phongtq
 *
 */

public class Combinations extends ValueObject{

	/** 順番 */
	@Getter
	private final int order;
	
	@Getter
	/** シフトコード */
	private ShiftPalletCode shiftCode;

	public Combinations(int order, ShiftPalletCode shiftCode) {
		
		// inv-1	1 <= 順番 <= 31	
		if(1 <= order && order <= 30){
			throw new BusinessException("Msg_1627");
		}
		this.order = order;
		this.shiftCode = shiftCode;
	}
}
