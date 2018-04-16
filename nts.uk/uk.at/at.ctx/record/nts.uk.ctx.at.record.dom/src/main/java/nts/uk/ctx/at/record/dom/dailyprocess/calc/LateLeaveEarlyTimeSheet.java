package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.MidNightTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.BonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.bonuspay.setting.SpecBonusPayTimesheet;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * 遅刻早退時間帯
 * @author ken_takasu
 *
 */
public class LateLeaveEarlyTimeSheet extends CalculationTimeSheet{

	public LateLeaveEarlyTimeSheet(TimeZoneRounding timeSheet, TimeSpanForCalc calcrange) {
		super(timeSheet, calcrange);
		// TODO Auto-generated constructor stub
	}


}
