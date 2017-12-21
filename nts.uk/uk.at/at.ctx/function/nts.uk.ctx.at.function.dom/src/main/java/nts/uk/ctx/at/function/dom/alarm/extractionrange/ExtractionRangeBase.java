package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtractionRangeBase {

	private String ExtractionId;
	
	private ExtractionRange extractionRange;

	public ExtractionRangeBase(String extractionId, ExtractionRange extractionRange) {
		super();
		ExtractionId = extractionId;
		this.extractionRange = extractionRange;
	}
	
	

}
