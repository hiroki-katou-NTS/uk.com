package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

public class GoOutSetHelper {

	public static WorkTimezoneGoOutSet createWorkTimezoneGoOutSet(GoOutTimeRoundingMethod method, TimeRoundingSetting rounding) {
		return new WorkTimezoneGoOutSet(method, createGoOutTimezoneRoundingSet(rounding, rounding, rounding));
	}

	public static GoOutTimezoneRoundingSet createGoOutTimezoneRoundingSet(TimeRoundingSetting holidayWork, TimeRoundingSetting within, TimeRoundingSetting overTime) {
		return new GoOutTimezoneRoundingSet(
				createGoOutTypeRoundingSet(holidayWork, holidayWork),
				createGoOutTypeRoundingSet(within, within),
				createGoOutTypeRoundingSet(overTime, overTime));
	}
	
	public static GoOutTypeRoundingSet createGoOutTypeRoundingSet(TimeRoundingSetting officalUseCompen, TimeRoundingSetting privateUnion) {
		return new GoOutTypeRoundingSet(
				createDeductGoOutRoundingSet(officalUseCompen, officalUseCompen),
				createDeductGoOutRoundingSet(privateUnion, privateUnion));
	}
	
	public static DeductGoOutRoundingSet createDeductGoOutRoundingSet(TimeRoundingSetting deduct, TimeRoundingSetting appro) {
		return new DeductGoOutRoundingSet(
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING, deduct),
				new GoOutTimeRoundingSetting(RoundingGoOutTimeSheet.INDIVIDUAL_ROUNDING, appro));
	}

}
