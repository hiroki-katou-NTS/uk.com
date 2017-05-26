package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OccurrenceVacationSetting.
 */
@Getter
public class OccurrenceVacationSetting extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The compen transfer setting. */
	private CompensatoryTransferSetting transferSetting;

	/** The compen occurrence div. */
	private CompensatoryOccurrenceDivision occurrenceDivision;
	
}
