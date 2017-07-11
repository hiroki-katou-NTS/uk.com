/**
 * 9:20:03 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.bonuspay.setting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.RoundingAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UnitAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPayTime;

/**
 * @author hungnm
 *
 */
@Getter
public class SpecBonusPayTimesheet extends BonusPayTimesheet {

	private int dateCode;

	private SpecBonusPayTimesheet(int timeSheetId, UseAtr useAtr, String timeItemId,
			BonusPayTime startTime, BonusPayTime endTime, UnitAtr roundingTimeAtr,
			RoundingAtr roundingAtr, int dateCode) {
		super(timeSheetId, useAtr, timeItemId, startTime, endTime, roundingTimeAtr, roundingAtr);
		this.dateCode = dateCode;
	}

	private SpecBonusPayTimesheet() {
		super();
	}

	public static SpecBonusPayTimesheet createFromJavaType(int timeSheetId, int useAtr, String timeItemId,
			Long startTime, Long endTime, int roundingTimeAtr, int roundingAtr, int dateCode) {
		return new SpecBonusPayTimesheet(timeSheetId, EnumAdaptor.valueOf(useAtr, UseAtr.class),
				timeItemId, new BonusPayTime(startTime),
				new BonusPayTime(endTime), EnumAdaptor.valueOf(roundingTimeAtr, UnitAtr.class),
				EnumAdaptor.valueOf(roundingAtr, RoundingAtr.class), dateCode);
	}

}
