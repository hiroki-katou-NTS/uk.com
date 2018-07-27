package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkTimeInfoDto {
	private WorkTypeDto wkType;
	private List<TimeZoneUseDto> TimezoneUseDtos;
}
