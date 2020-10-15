package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;

/**
 * 加給自動計算設定
 * @author keisuke_hoshina
 *
 */
@Value
public class BonusPayAutoCalcSet {
	private CompanyId comanyID;
	private int BonusPayItemNo;
	private WorkingTimesheetCalculationSetting workingTimesheet;
	private OvertimeTimesheetCalculationSetting overTimeWorksheet;
	private HolidayTimesheetCalculationSetting holidayTimesheet;
	
	/**
	 * 計算区分から計算するかどうかを判定する
	 * @return　計算する
	 */
	public boolean getCalcAtr(ActualWorkTimeSheetAtr actualAtr, CalAttrOfDailyAttd calcAtrOfDaily) {
		switch(actualAtr) {
		case WithinWorkTime:
			return workingTimesheet.isCalculateAutomatic();
		case HolidayWork:
			return calcAtrOfDaily.getHolidayTimeSetting().getLateNightTime().getCalAtr().isCalculateEmbossing();
		case EarlyWork:
			return calcAtrOfDaily.getOvertimeSetting().getEarlyOtTime().getCalAtr().isCalculateEmbossing();
		case OverTimeWork:
			return calcAtrOfDaily.getOvertimeSetting().getLegalOtTime().getCalAtr().isCalculateEmbossing();
		case StatutoryOverTimeWork:
			return calcAtrOfDaily.getOvertimeSetting().getNormalOtTime().getCalAtr().isCalculateEmbossing();
		default:
			throw new RuntimeException("unknown actualAtr" + actualAtr);
		}
	}
}
