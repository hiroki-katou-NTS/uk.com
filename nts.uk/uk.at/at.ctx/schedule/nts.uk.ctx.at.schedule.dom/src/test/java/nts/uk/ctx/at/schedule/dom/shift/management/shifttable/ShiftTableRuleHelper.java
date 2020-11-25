package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Arrays;
import java.util.Optional;

import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ShiftTableRuleHelper {
	
	/**
	 * @param useWorkExpectationAtr 
	 * @param fromNoticeDays
	 * @return
	 */
	/**
	 * @param useWorkExpectationAtr勤務希望運用するかどうか
	 * @param setting シフト表の設定のインスタンス
	 * @param fromNoticeDays
	 * @return
	 */
	public static ShiftTableRule createWithParam(NotUseAtr useWorkExpectationAtr, Optional<WorkAvailabilityRule> setting, Optional<FromNoticeDays> fromNoticeDays) {
		
		return ShiftTableRule.create( 
				NotUseAtr.USE, 
				useWorkExpectationAtr, 
				setting, 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				fromNoticeDays);
	}

}
