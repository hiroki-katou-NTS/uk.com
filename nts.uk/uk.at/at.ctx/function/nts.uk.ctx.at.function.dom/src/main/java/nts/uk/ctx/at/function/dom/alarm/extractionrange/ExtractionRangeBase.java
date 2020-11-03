package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
@NoArgsConstructor
public abstract class ExtractionRangeBase extends DomainObject {

	/** Id */
	private String extractionId;

	/** 抽出する範囲 */
	private ExtractionRange extractionRange;

	public ExtractionRangeBase(String extractionId, int extractionRangeValue) {
		super();
		this.extractionId = extractionId;
		this.extractionRange = EnumAdaptor.valueOf(extractionRangeValue, ExtractionRange.class);
	}

	/**
	 * Checks constraint.
	 *
	 * @throws BusinessException the Business exception
	 */
	protected void checkConstraint() throws BusinessException {
	}

}
