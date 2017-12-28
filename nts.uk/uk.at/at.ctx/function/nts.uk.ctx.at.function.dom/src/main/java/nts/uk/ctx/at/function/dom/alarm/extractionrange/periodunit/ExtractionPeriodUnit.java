package nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

@Getter
@Setter
public class ExtractionPeriodUnit extends ExtractionRangeBase {

	SegmentationOfCycle segmentationOfCycle;

	public ExtractionPeriodUnit(String extractionId, int extractionRangeValue, int segmentationOfCycle) {
		super(extractionId, extractionRangeValue);
		
	}

}

