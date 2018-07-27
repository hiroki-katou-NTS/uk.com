package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author sang.nv
 *
 */
@Getter
public class CompensatoryDayOffManaDataCommand {

	//closureId
	private Integer closureId;

	// SID
	private String employeeId;

	// 代休データID
	private String comDayOffID;

	// 代休日
	private GeneralDate dayOffDate;

	// 必要日数
	private Double requireDays;

	// 必要時間数
	private Integer requiredTimes;

	// 未相殺日数
	private Double remainDays;

	// 未相殺時間数
	private Integer remainTimes;
	
	private int unknownDate;
}
