package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import java.util.Collections;

import nts.arc.time.GeneralDate;

public class WorkExpectationOfOneDayTestHelper {
	
	public static WorkExpectationOfOneDay createExpectationOfOneDay() {
		return WorkExpectationOfOneDay
				.create("001", 
						GeneralDate.ymd(2020, 9, 18), 
						new WorkExpectationMemo("memo"), 
						AssignmentMethod.HOLIDAY, 
						Collections.emptyList(), 
						Collections.emptyList());
	}

}
