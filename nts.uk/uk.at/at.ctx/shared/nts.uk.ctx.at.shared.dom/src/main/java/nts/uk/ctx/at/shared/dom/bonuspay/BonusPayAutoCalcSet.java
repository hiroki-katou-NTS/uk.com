package nts.uk.ctx.at.shared.dom.bonuspay;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

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
	
//	/**
//	 * 計算区分から計算するかどうかを判定する
//	 * @return　計算する
//	 */
//	public boolean getCalcAtr(ActualWorkTimeSheetAtr actualAtr, CalAttrOfDailyPerformance calcAtrOfDaily) {
//		switch(actualAtr) {
//		case WithinWorkTime:
//			return workingTimesheet.isCalculateAutomatic();
//		case HolidayWork:
//			return calcAtrOfDaily.getHolidayTimeSetting().getLateNightTime().getCalculationAttr().isCalculateEmbossing();
//		case EarlyWork:
//			return calcAtrOfDaily.getOvertimeSetting().getEarlyOverTime().getCalculationAttr().isCalculateEmbossing();
//		case OverTimeWork:
//			return calcAtrOfDaily.getOvertimeSetting().getLegalOverTime().getCalculationAttr().isCalculateEmbossing();
//		case StatutoryOverTimeWork:
//			return calcAtrOfDaily.getOvertimeSetting().getNormalOverTime().getCalculationAttr().isCalculateEmbossing();
//		default:
//			throw new RuntimeException("unknown actualAtr" + actualAtr);
//		}
//	}
}
