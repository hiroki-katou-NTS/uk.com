package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;

@Value
@AllArgsConstructor
public class ApprovalSttDetailRecord {
	List<AttendanceResultImport> listAttendance;
	AchievementOutput achievement;
}
