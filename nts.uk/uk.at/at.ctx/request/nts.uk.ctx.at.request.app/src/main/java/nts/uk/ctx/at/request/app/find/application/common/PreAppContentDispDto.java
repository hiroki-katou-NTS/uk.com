package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PreAppContentDispDto {
	
	private String date;
	
	private AppHolidayWorkDto appHolidayWork;
	
	public static PreAppContentDispDto fromDomain(PreAppContentDisplay preAppContentDisplay) {
		return new PreAppContentDispDto(preAppContentDisplay.getDate().toString(), AppHolidayWorkDto.fromDomain(preAppContentDisplay.getAppHolidayWork().orElse(null)));
	}
	
	public PreAppContentDisplay toDomain() {
		return new PreAppContentDisplay(GeneralDate.fromString(date, "yyyy/MM/dd"), null, null);
	}
	
}
