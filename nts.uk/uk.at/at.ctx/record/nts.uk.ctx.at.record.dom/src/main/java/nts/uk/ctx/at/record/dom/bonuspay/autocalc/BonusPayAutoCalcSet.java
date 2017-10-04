package nts.uk.ctx.at.record.dom.bonuspay.autocalc;

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
	private WorkingTimesheetCalculationSetting workingTimesheet;
	private OvertimeTimesheetCalculationSetting overTimeWorksheet;
	private HolidayTimesheetCalculationSetting holidayTimesheet;
}
