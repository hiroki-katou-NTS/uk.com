package nts.uk.ctx.at.record.app.command.divergence.time.history;

import lombok.Data;

/**
 * The Class DivergenceReferenceTimeUsageUnitCommand.
 */
@Data
public class DivergenceReferenceTimeUsageUnitCommand {

	/** The work type use set. */
	private boolean workTypeUseSet;

	/**
	 * Instantiates a new divergence reference time usage unit command.
	 */
	public DivergenceReferenceTimeUsageUnitCommand() {
		super();
	}

	/**
	 * Instantiates a new divergence divergence reference time usage unit
	 * command.
	 *
	 * @param listDataSetting
	 *            the list data setting
	 */
	public DivergenceReferenceTimeUsageUnitCommand(boolean workTypeUseSet) {
		this.workTypeUseSet = workTypeUseSet;
	}
}
