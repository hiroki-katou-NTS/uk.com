package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import java.util.Collections;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class MaxDayOfWorkTimeHelper {
	public static MaxDayOfWorkTimeCompany DUMMY = new MaxDayOfWorkTimeCompany(
			new MaxDayOfWorkTimeCode("003"), new MaxDayOfWorkTimeName("シフト１"),
			new MaxDayOfWorkTime(Collections.emptyList(), new MaxDay(3)));

	public static MaxDayOfWorkTimeOrganization DUMMY_ORG = new MaxDayOfWorkTimeOrganization(
			TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
			new MaxDayOfWorkTimeCode("003"), new MaxDayOfWorkTimeName("シフト１"),
			new MaxDayOfWorkTime(Collections.emptyList(), new MaxDay(3)));
}
