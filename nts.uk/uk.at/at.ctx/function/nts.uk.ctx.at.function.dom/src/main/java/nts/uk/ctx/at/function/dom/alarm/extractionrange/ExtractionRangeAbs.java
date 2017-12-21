package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;

@Getter
@Setter
public abstract class ExtractionRangeAbs extends DomainObject {

	private String ExtractionId;
	
	private ExtractionRange extractionRange;

	public ExtractionRangeAbs(String extractionId, ExtractionRange extractionRange) {
		super();
		ExtractionId = extractionId;
		this.extractionRange = extractionRange;
	}
}
