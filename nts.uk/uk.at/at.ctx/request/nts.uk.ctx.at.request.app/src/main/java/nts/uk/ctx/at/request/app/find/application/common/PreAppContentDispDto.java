package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeDto;
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
	
	private AppOverTimeDto apOptional;
	
	public static PreAppContentDispDto fromDomain(PreAppContentDisplay preAppContentDisplay) {
		return new PreAppContentDispDto(
				preAppContentDisplay.getDate().toString(),
				AppHolidayWorkDto.fromDomain(preAppContentDisplay.getAppHolidayWork().orElse(null)),
				preAppContentDisplay.getApOptional().isPresent() ? AppOverTimeDto.fromDomain(preAppContentDisplay.getApOptional().get()) : null
				);
	}
	
	public PreAppContentDisplay toDomain() {
		return new PreAppContentDisplay(GeneralDate.fromString(date, "yyyy/MM/dd"), Optional.empty(), Optional.empty());
	}
	
}
