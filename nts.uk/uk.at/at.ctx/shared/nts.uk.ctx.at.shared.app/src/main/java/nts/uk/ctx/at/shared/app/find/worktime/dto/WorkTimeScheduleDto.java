package nts.uk.ctx.at.shared.app.find.worktime.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;

/**
 * 
 * @author sonnh1
 *
 */

@Value
public class WorkTimeScheduleDto {
	private String siftCd;
	private String name;
	private String abName;
	private String symbol;
	private int dailyWorkAtr;
	private int methodAtr;
	private int displayAtr;
	private String note;

	public static WorkTimeScheduleDto fromDomain(WorkTime domain) {
		return new WorkTimeScheduleDto(domain.getSiftCD().v(), domain.getWorkTimeDisplayName().getWorkTimeName().v(),
				domain.getWorkTimeDisplayName().getWorkTimeAbName().v(),
				domain.getWorkTimeDisplayName().getWorkTimeSymbol().v(),
				domain.getWorkTimeDivision().getWorkTimeDailyAtr().value,
				domain.getWorkTimeDivision().getWorkTimeMethodSet().value, domain.getDispAtr().value,
				domain.getNote().v());
	}
}
