package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class MaxNumberDaysOfContinuousWorkTimeHelper {
	
	public static MaxNumberDaysOfContinuousWorkTimeCom DUMMY = new MaxNumberDaysOfContinuousWorkTimeCom(
			new WorkTimeContinuousCode("003"), new WorkTimeContinuousName("name"),
			new MaxNumberOfContinuousWorktime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new NumberOfConsecutiveDays(5)));
	
	public static MaxNumberDaysOfContinuousWorkTimeOrg DUMMY_ORG = new MaxNumberDaysOfContinuousWorkTimeOrg(
			TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"), 
			new WorkTimeContinuousCode("003"), new WorkTimeContinuousName("name"),
			new MaxNumberOfContinuousWorktime(
					Arrays.asList( new WorkTimeCode("001"),
								   new WorkTimeCode("002"),
								   new WorkTimeCode("003")),
					new NumberOfConsecutiveDays(5)));
	
	/**
	 * List<WorkTimeCode>　-> List<String>
	 * @param worktimeCodeLst
	 * @return
	 */
	public static List<String> convertToWorkTimeCode(List<WorkTimeCode> worktimeCodeLst) {
		return worktimeCodeLst.stream().map(c -> {
			return c.v();
		}).collect(Collectors.toList());
	}
}
