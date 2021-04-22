package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;

@Data
@Builder
public class ScheFixCondDayDto {
	
	
	/**
	 * Convert Domain to DTO
	 * @param domain FixedExtractionSDailyItems
	 * @return ScheFixItemDayDto
	 */
	public static ScheFixCondDayDto fromDomain(FixedExtractionSDailyCon domain) {
		
		return ScheFixCondDayDto.builder()
				.build();
	}
}
