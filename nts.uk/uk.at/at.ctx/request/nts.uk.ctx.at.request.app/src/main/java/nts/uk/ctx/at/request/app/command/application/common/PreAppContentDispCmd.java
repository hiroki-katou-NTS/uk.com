package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkCmd;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeCommand;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PreAppContentDispCmd {
	
	private String date;
	// 残業申請
	private AppOverTimeCommand apOptional;
	// 休日出勤申請
	private AppHolidayWorkCmd appHolidayWork;
	
	public PreAppContentDisplay toDomain() {
		return new PreAppContentDisplay(
				GeneralDate.fromString(date, "yyyy/MM/dd"), 
				apOptional != null ? Optional.of(apOptional.toDomain()) : Optional.empty(), 
				appHolidayWork != null ? Optional.of(appHolidayWork.toDomain()) : Optional.empty());
	}
}
