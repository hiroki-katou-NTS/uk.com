package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDay;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.WorkTimeMaximumCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.WorkTimeMaximumName;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

public class MaxDayOfWorkTimeHelper {
	public static MaxDayOfWorkTimeCompany DUMMY = new MaxDayOfWorkTimeCompany(
			new WorkTimeMaximumCode("003"), new WorkTimeMaximumName("シフト１"),
			new MaxDayOfWorkTime(Collections.emptyList(), new MaxDay(3)));

	public static MaxDayOfWorkTimeOrganization DUMMY_ORG = new MaxDayOfWorkTimeOrganization(
			TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
			new WorkTimeMaximumCode("003"), new WorkTimeMaximumName("シフト１"),
			new MaxDayOfWorkTime(Collections.emptyList(), new MaxDay(3)));
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
