package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;

/**
 * The class Extraction aver month dto.<br>
 * Dto 複数月平均基準月
 */
@Data
@AllArgsConstructor
public class ExtractionAverMonthDto {
	/**
	 * The Extraction id.
	 */
	private String extractionId;

	/**
	 * The Extraction range.
	 */
	private int extractionRange;

	/**
	 * The Start month.
	 */
	private int strMonth;

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Extraction aver month dto
	 */
	public static ExtractionAverMonthDto createFromDomain(AverageMonth domain) {
		if (domain == null) {
			return null;
		}
		return new ExtractionAverMonthDto(domain.getExtractionId(),
										  domain.getExtractionRange().value,
										  domain.getStrMonth().value);
	}

}
