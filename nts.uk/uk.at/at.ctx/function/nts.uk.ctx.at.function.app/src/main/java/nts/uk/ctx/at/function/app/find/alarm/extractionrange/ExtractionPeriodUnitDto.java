package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;

/**
 * The class Extraction period unit dto.<br>
 * Dto 抽出期間(周期単位)
 */
@Data
@AllArgsConstructor
public class ExtractionPeriodUnitDto {

	/**
	 * The Extraction id.
	 */
	private String extractionId;

	/**
	 * The Extraction range.
	 */
	private int extractionRange;

	/**
	 * The Segmentation of cycle.
	 */
	private int segmentationOfCycle;

	/**
	 * No args constructor.
	 */
	private ExtractionPeriodUnitDto() {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Extraction period unit dto
	 */
	public static ExtractionPeriodUnitDto createFromDomain(ExtractionPeriodUnit domain) {
		if (domain == null) {
			return null;
		}
		return new ExtractionPeriodUnitDto(domain.getExtractionId(),
										   domain.getExtractionRange().value,
										   domain.getSegmentationOfCycle().value);
	}

}
