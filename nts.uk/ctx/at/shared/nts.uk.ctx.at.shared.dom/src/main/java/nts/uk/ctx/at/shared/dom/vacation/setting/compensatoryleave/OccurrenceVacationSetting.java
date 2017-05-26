package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class OccurrenceVacationSetting.
 */
@Setter
public class OccurrenceVacationSetting extends AggregateRoot {

	/** The company id. */
	private String companyId;

	/** The compen transfer setting. */
	private CompensatoryTransferSetting transferSetting;

	/** The compen occurrence div. */
	private CompensatoryOccurrenceDivision occurrenceDivision;

}
