package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import java.util.Collections;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class MaxDayOfWorkTimeHelper {
	public static MaxDayOfWorkTimeCompany DUMMY = new MaxDayOfWorkTimeCompany(
			new WorkTimeMaximumCode("003"), new WorkTimeMaximumName("シフト１"),
			new MaxDayOfWorkTime(Collections.EMPTY_LIST, new MaxDay(3)));

	public static MaxDayOfWorkTimeOrganization DUMMY_ORG = new MaxDayOfWorkTimeOrganization(
			TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
			new WorkTimeMaximumCode("003"), new WorkTimeMaximumName("シフト１"),
			new MaxDayOfWorkTime(Collections.EMPTY_LIST, new MaxDay(3)));
}
