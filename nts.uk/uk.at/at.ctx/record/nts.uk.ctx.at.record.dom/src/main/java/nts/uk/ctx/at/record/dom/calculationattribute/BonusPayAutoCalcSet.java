package nts.uk.ctx.at.record.dom.calculationattribute;

import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.calculationattribute.CalAttrOfDailyPerformance;
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
	
	/**
	 * 計算区分から計算するかどうかを判定する
	 * @return　計算する
	 */
	public boolean getCalcAtr(ActualWorkTimeSheetAtr actualAtr, CalAttrOfDailyPerformance calcAtrOfDaily) {
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
