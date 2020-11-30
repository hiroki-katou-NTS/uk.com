package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;

@Data
public class ExtractionPeriodUnitDto {

	private String extractionId;

	private int extractionRange;

	private int segmentationOfCycle;


	public static ExtractionPeriodUnitDto fromDomain(ExtractionPeriodUnit domain){
		ExtractionPeriodUnitDto dto = new  ExtractionPeriodUnitDto();

		dto.setExtractionId(domain.getExtractionId());
		dto.setExtractionRange(domain.getExtractionRange().value);
		dto.setSegmentationOfCycle(domain.getSegmentationOfCycle().value);
		return dto;
	}
}
