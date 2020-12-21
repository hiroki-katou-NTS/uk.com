package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import nts.arc.primitive.TimeClockPrimitiveValue;

/**
 * The Class LaborContractTime.
 */
// 労働契約時間
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
	
	/**
	 * 1時間単位で切り上げ
	 * @return
	 */
	public LaborContractTime roundUp1Hour(){
		int time = this.v();
		if ( time % 60 > 0 ){
			int timeUpTo1Hour = (time/60)*60 + 60;
			return new LaborContractTime(timeUpTo1Hour);
		}
		return new LaborContractTime(time);
	}

}
