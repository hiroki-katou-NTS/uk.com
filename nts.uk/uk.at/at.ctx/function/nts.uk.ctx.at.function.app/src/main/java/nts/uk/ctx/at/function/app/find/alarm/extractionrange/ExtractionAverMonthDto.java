package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtractionAverMonthDto {
	private String extractionId;

	private int extractionRange;

	private int strMonth;


	public static ExtractionAverMonthDto fromDomain(AverageMonth domain) {
		return new ExtractionAverMonthDto(domain.getExtractionId(), domain.getExtractionRange().value, domain.getStrMonth().value);
	}
}
