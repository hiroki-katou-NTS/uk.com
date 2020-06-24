package nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.screen.at.app.schedule.personal.correction.WorkShiftScheduleDto;

/**
 * @author anhdt
 *	 勤務予定（シフト）を取得する
 */
@Stateless
public class GetWorkScheduleQuery {
	
	@Inject
	private WorkScheduleRepository workScheduleRepo;
	
	public WorkScheduleDto getWorkSchedule(List<ShiftMaster> shiftMasters, List<String> empIds, DatePeriod period) {
		
		WorkScheManaStatusRequiredImpl workRequired = new WorkScheManaStatusRequiredImpl(workScheduleRepo);
		// 1 取得する(Require, List<社員ID>, 期間)
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> workSchedules = WorkScheManaStatusService.getScheduleManagement(workRequired, empIds, period);
		
		
		return null;
	}
	
	@Data
	class WorkScheduleDto {
		private List<WorkShiftScheduleDto> schedules;
		private Map<ShiftMaster, Optional<AttendanceHolidayAttr>> shiftHollidayAtt;
	}
	
	@AllArgsConstructor
	private class WorkScheManaStatusRequiredImpl implements WorkScheManaStatusService.Require {
		
		@Inject
		private WorkScheduleRepository workScheduleRepo;
		
		@Override
		public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
			return workScheduleRepo.get(employeeID, ymd);
		}
		
	}
}
