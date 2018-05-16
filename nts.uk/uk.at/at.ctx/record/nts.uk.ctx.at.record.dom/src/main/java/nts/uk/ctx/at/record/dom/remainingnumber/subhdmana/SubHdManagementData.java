/**
 * 
 */
package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author nam.lh
 *
 */
@Getter
public class SubHdManagementData {
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

	/**
	 * @param employeeId
	 * @param checkedHoliday
	 * @param dateHoliday
	 * @param selectedCodeHoliday
	 * @param duedateHoliday
	 * @param checkedSubHoliday
	 * @param dateSubHoliday
	 * @param selectedCodeSubHoliday
	 * @param checkedSplit
	 * @param dateOptionSubHoliday
	 * @param selectedCodeOptionSubHoliday
	 * @param dayRemaining
	 */
	public SubHdManagementData(String employeeId, Boolean checkedHoliday, GeneralDate dateHoliday,
			String selectedCodeHoliday, GeneralDate duedateHoliday, String checkedSubHoliday,
			GeneralDate dateSubHoliday, String selectedCodeSubHoliday, String checkedSplit,
			GeneralDate dateOptionSubHoliday, String selectedCodeOptionSubHoliday, String dayRemaining) {
		super();
		this.employeeId = employeeId;
		this.checkedHoliday = checkedHoliday;
		this.dateHoliday = dateHoliday;
		this.selectedCodeHoliday = selectedCodeHoliday;
		this.duedateHoliday = duedateHoliday;
		this.checkedSubHoliday = checkedSubHoliday;
		this.dateSubHoliday = dateSubHoliday;
		this.selectedCodeSubHoliday = selectedCodeSubHoliday;
		this.checkedSplit = checkedSplit;
		this.dateOptionSubHoliday = dateOptionSubHoliday;
		this.selectedCodeOptionSubHoliday = selectedCodeOptionSubHoliday;
		this.dayRemaining = dayRemaining;
	}
}
