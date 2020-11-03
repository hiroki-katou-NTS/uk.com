package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;

/**
 * The class Extraction range year dto.<br>
 * Dto 単年
 */
@Data
@AllArgsConstructor
public class ExtractionRangeYearDto {

	/**
	 * The Extraction id.
	 */
	private String extractionId;

	/**
	 * The Extraction range.
	 */
	private int extractionRange;

	/**
	 * The Year.
	 */
	private int year;

	/**
	 * The This year.
	 */
	private int thisYear;

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Extraction range year dto
	 */
	public static ExtractionRangeYearDto createFromDomain(AYear domain) {
		if (domain == null) {
			return null;
		}
		return new ExtractionRangeYearDto(domain.getExtractionId(),
										  domain.getExtractionRange().value,
										  domain.getYear(),
										  domain.isToBeThisYear() ? 1 : 0);
	}

}
