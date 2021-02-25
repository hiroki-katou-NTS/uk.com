package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class MaxNumberDaysOfContinuousWorkTimeHelper {
	
	public static MaxDaysOfContinuousWorkTimeCompany DUMMY = new MaxDaysOfContinuousWorkTimeCompany(
			new ConsecutiveWorkTimeCode("003"), new ConsecutiveWorkTimeName("name"),
			new MaxDaysOfConsecutiveWorkTime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new ConsecutiveNumberOfDays(5)));
	
	public static MaxDaysOfContinuousWorkTimeOrganization DUMMY_ORG = new MaxDaysOfContinuousWorkTimeOrganization(
			TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"), 
			new ConsecutiveWorkTimeCode("003"), new ConsecutiveWorkTimeName("name"),
			new MaxDaysOfConsecutiveWorkTime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new ConsecutiveNumberOfDays(5)));
}
