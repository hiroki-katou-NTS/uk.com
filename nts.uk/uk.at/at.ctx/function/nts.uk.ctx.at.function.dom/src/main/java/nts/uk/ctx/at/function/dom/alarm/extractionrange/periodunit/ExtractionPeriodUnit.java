package nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

@Getter
@Setter
//抽出期間(周期単位)
public class ExtractionPeriodUnit extends ExtractionRangeBase {
	//周期の区分
	private SegmentationOfCycle segmentationOfCycle;

	public ExtractionPeriodUnit(String extractionId, int extractionRangeValue, int segmentationOfCycle) {
		super(extractionId, extractionRangeValue);
		this.segmentationOfCycle = EnumAdaptor.valueOf(segmentationOfCycle, SegmentationOfCycle.class);
	}

}

