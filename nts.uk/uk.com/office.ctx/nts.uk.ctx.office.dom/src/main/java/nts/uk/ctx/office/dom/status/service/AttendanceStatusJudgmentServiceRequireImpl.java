package nts.uk.ctx.office.dom.status.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.ActivityStatusRepository;
import nts.uk.ctx.office.dom.status.adapter.AttendanceAdapter;
import nts.uk.ctx.office.dom.status.adapter.AttendanceStateImport;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService.Required;

@AllArgsConstructor
public class AttendanceStatusJudgmentServiceRequireImpl implements Required {

	private GoOutEmployeeInformationRepository goOutRepo;
	
	private ActivityStatusRepository statusRepo;
	
	private AttendanceAdapter attendanceAdapter;
	
	@Override
	public Optional<GoOutEmployeeInformation> getGoOutEmployeeInformation(String sid, GeneralDate date) {
		return goOutRepo.getBySidAndDate(sid, date);
	}

	@Override
	public Optional<ActivityStatus> getActivityStatus(String sid, GeneralDate date) {
		return statusRepo.getBySidAndDate(sid, date);
	}

	@Override
	public AttendanceStateImport getAttendace(String sid) {
		return attendanceAdapter.getAttendace(sid);
	}
}
