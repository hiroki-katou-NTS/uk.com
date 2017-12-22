package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
public abstract class ExtractionRangeBase extends DomainObject{

	/**Id*/
	private String extractionId;
	
	/**抽出する範囲*/
	private ExtractionRange extractionRange;

	public ExtractionRangeBase(String extractionId, ExtractionRange extractionRange) {
		super();
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
	}
}
