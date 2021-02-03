/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;

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
	private Double selectedCodeHoliday;
	/**
	 * 期限
	 */
	private GeneralDate duedateHoliday;

	// Domain CompensatoryDayOffManaData
	/**
	 * 代休
	 */
	private Boolean checkedSubHoliday;
	/**
	 * 年月日
	 */
	private GeneralDate dateSubHoliday;

	/**
	 * 代休日数
	 */
	private Double selectedCodeSubHoliday;

	// Option checked
	/**
	 * 分割消化
	 */
	private Boolean checkedSplit;
	/**
	 * 年月日
	 */
	private GeneralDate dateOptionSubHoliday;
	/**
	 * 代休日数
	 */
	private Double selectedCodeOptionSubHoliday;

	// Domain LeaveComDayOffManagement
	private Double dayRemaining;

	private int closureId;

	private List<String> lstLinkingDate;
	
	private Double linkingDate;
	
	private Double displayRemainDays;
}
