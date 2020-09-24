package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import java.util.Arrays;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class MaxNumberDaysOfContinuousWorkTimeHelper {
	
	public static MaxNumberDaysOfContinuousWorkTimeCom DUMMY = new MaxNumberDaysOfContinuousWorkTimeCom(
			"000000000000-0315", new WorkTimeContinuousCode("003"), new WorkTimeContinuousName("通常勤務１"),
			new MaxNumberOfContinuousWorktime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new NumberOfConsecutiveDays(5)));
	
	public static MaxNumberDaysOfContinuousWorkTimeOrg DUMMY_ORG = new MaxNumberDaysOfContinuousWorkTimeOrg(
			TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"), 
			new WorkTimeContinuousCode("003"), new WorkTimeContinuousName("通常勤務１"),
			new MaxNumberOfContinuousWorktime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new NumberOfConsecutiveDays(5)));
}
