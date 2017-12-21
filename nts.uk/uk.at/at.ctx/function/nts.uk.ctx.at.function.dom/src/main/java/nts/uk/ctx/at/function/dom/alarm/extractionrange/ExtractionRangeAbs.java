package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ExtractionRangeAbs {

	private String ExtractionId;
	
	private ExtractionRange extractionRange;

	public ExtractionRangeAbs(String extractionId, ExtractionRange extractionRange) {
		super();
		this.ExtractionId = extractionId;
		this.extractionRange = extractionRange;
	}
}
