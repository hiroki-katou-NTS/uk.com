package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Collections;

import nts.arc.time.GeneralDate;

public class WorkAvailabilityOfOneDayTestHelper {
	
	public static WorkAvailabilityOfOneDay createWorkAvailabilityOfOneDay() {
		return WorkAvailabilityOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkAvailabilityMemo("memo"), 
						AssignmentMethod.HOLIDAY, 
						Collections.emptyList(), 
						Collections.emptyList());
	}

}
