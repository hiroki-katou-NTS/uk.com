package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;

@Getter
public class ExtractionRangeBase {

	private String ExtractionId;
	
	private ExtractionRange extractionRange;

	public ExtractionRangeBase(String extractionId, ExtractionRange extractionRange) {
		super();
		ExtractionId = extractionId;
		this.extractionRange = extractionRange;
	}
	
	

}
