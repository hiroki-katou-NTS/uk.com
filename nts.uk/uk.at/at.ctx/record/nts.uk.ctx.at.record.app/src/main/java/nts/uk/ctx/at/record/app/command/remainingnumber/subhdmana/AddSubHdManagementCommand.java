/**
 * 
 */
package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author nam.lh
 *
 */
@Getter
public class AddSubHdManagementCommand {
	// Domain LeaveManagementData
	private String employeeId;

	/**
	 * 休出
	 */
	private Boolean checkedHoliday;

	/**
	 * 年月日
	 */
	private GeneralDate dateHoliday;

	/**
	 * 休出日数
	 */
	private String selectedCodeHoliday;
	/**
	 * 期限
	 */
	private GeneralDate duedateHoliday;

	// Domain CompensatoryDayOffManaData
	/**
	 * 代休
	 */
	private String checkedSubHoliday;
	/**
	 * 年月日
	 */
	private GeneralDate dateSubHoliday;

	/**
	 * 代休日数
	 */
	private String selectedCodeSubHoliday;

	// Option checked
	/**
	 * 分割消化
	 */
	private String checkedSplit;
	/**
	 * 年月日
	 */
	private GeneralDate dateOptionSubHoliday;
	/**
	 * 代休日数
	 */
	private String selectedCodeOptionSubHoliday;

	// Domain LeaveComDayOffManagement
	private String dayRemaining;

}
