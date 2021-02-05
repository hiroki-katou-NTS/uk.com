package nts.uk.screen.at.app.kmk004.g;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class YearMonthPeriodDto {

	Integer start;
	Integer end;
	
	public static YearMonthPeriodDto fromDomain(YearMonthPeriod domain) {
		
		return new YearMonthPeriodDto(domain.start().v(), domain.end().v());
	}

}
