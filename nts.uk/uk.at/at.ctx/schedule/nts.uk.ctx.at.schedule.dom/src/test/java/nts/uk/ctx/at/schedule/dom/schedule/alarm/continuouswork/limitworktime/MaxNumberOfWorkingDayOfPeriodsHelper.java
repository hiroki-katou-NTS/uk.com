package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class MaxNumberOfWorkingDayOfPeriodsHelper {
	public static MaxNumberOfWorkingDayOfPeriodsCom DUMMY = new MaxNumberOfWorkingDayOfPeriodsCom("000000000000-0315",
			new WorkTimeUpperLimitCode("003"), new WorkTimeUpperLimitName("シフト１"),
			new MaxNumberOfWorkingDayOfPeriods(new MaxNumberOfWorkingDay(3)));

	public static MaxNumberOfWorkingDayOfPeriodsOrg DUMMY_ORG = new MaxNumberOfWorkingDayOfPeriodsOrg(
			TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
			new WorkTimeUpperLimitCode("003"), new WorkTimeUpperLimitName("シフト１"),
			new MaxNumberOfWorkingDayOfPeriods(new MaxNumberOfWorkingDay(3)));
}
