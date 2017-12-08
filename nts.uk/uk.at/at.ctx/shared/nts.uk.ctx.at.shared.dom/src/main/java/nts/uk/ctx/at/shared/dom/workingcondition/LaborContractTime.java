package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.arc.primitive.TimeClockPrimitiveValue;

/**
 * The Class LaborContractTime.
 */
public class LaborContractTime extends TimeClockPrimitiveValue<LaborContractTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6044051654921619509L;
	
	/**
	 * Instantiates a new labor contract time.
	 *
	 * @param minutesFromZeroOClock the minutes from zero O clock
	 */
	public LaborContractTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
