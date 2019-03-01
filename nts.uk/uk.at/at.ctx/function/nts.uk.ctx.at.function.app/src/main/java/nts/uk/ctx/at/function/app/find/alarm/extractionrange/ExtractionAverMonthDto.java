package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.Value;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
@Value
public class ExtractionAverMonthDto {
	private String extractionId;

	private int extractionRange;

	private int strMonth;


	public static ExtractionAverMonthDto fromDomain(AverageMonth domain) {
		return new ExtractionAverMonthDto(domain.getExtractionId(), domain.getExtractionRange().value, domain.getStrMonth().value);
	}
}
