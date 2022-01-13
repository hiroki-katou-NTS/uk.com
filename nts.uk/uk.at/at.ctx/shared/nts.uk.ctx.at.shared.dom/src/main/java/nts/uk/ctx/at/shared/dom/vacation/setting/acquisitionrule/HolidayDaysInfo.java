package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休暇の日数情報
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class HolidayDaysInfo {
	/**
	 * 休む日数
	 */
	private double numberOfDay;
	
	/**
	 * 休暇残日数
	 */
	private RemainingVacationDays remainingVacationDays;
	
	/**
	 * 勤務種類
	 */
	private WorkType workType;
	
	/**
	 * 管理設定
	 */
	private ManagementSetting managementSetting;
}
