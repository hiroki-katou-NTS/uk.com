package nts.uk.ctx.at.record.dom.bonuspay.autocalc;

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.CalcAtrOfDaily;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.ActualWorkTimeSheetAtr;
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
	public boolean getCalcAtr(ActualWorkTimeSheetAtr actualAtr, CalcAtrOfDaily calcAtrOfDaily) {
		switch(actualAtr) {
		case WithinWorkTime:
			return workingTimesheet.isCalculateAutomatic();
		case HolidayWork:
			return calcAtrOfDaily.getHolidayHolidayWorkTime().getLateNightTime().getCalculationClassification().isCalculateEmbossing();
		case EarlyWork:
			return calcAtrOfDaily.getOverTimeWork().getEarlyOtTime().getCalculationClassification().isCalculateEmbossing();
		case OverTimeWork:
			return calcAtrOfDaily.getOverTimeWork().getLegalOtTime().getCalculationClassification().isCalculateEmbossing();
		case StatutoryOverTimeWork:
			return calcAtrOfDaily.getOverTimeWork().getNormalOtTime().getCalculationClassification().isCalculateEmbossing();
		default:
			throw new RuntimeException("unknown actualAtr" + actualAtr);
		}
	}
}
