package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author Sonnlb
 *
 */
public class TimeZoneUseDto {
	int useAtr;
	int workNo;
	int startTime;
	int endTime;

	public static TimeZoneUseDto fromDomain(TimezoneUse domain) {
		return new TimeZoneUseDto(domain.getUseAtr().value, domain.getWorkNo(), domain.getStart().v(),
				domain.getEnd().v());
	}
}
