package nts.uk.screen.at.app.schedule.personal.correction.shiftdisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.GetCombineShiftMasterandWorkHolidayClassificationService;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.GetListWorkInfoByDailyAttendanceRecordService;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.WorkScheManaStatusService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.screen.at.app.schedule.personal.correction.WorkShiftScheduleDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 勤務予定（シフト）を取得する
 */
@Stateless
public class GetWorkScheduleQuery {

	@Inject
	private WorkScheduleRepository workScheduleRepo;

	@Inject
	private ShiftMasterRepository shiftMasterRp;

	public WorkScheduleDto getWorkSchedule(List<ShiftMaster> shiftMasters, List<String> empIds, DatePeriod period) {
		
		String cid = AppContexts.user().companyId();
		
		WorkScheManaStatusRequiredImpl workRequired = new WorkScheManaStatusRequiredImpl(workScheduleRepo);
		// 1 取得する(Require, List<社員ID>, 期間)
		Map<ScheManaStatuTempo, Optional<WorkSchedule>> workSchedules = WorkScheManaStatusService.getScheduleManagement(workRequired, empIds, period);
		// 勤務情報リスト = 管理状態と勤務予定Map.values(): map $.勤務情報
		List<WorkInfoOfDailyAttendance> workInfosOfDaily = new ArrayList<>();
		for (Map.Entry<ScheManaStatuTempo, Optional<WorkSchedule>> entry : workSchedules.entrySet()) {
		    if(entry.getValue().isPresent()) {
		    	workInfosOfDaily.add(entry.getValue().get().getWorkInfo());
		    }
		}
		
		// 2 取得する(List<日別勤怠の勤務情報>)
		List<WorkInformation> workInfos = GetListWorkInfoByDailyAttendanceRecordService.get(workInfosOfDaily);
		
		
		// 3 勤務情報で取得する(Require, 会社ID, List<勤務情報>)
		CombineShiftMasterandWorkHolidayClassificationRqImpl shiftRequired = new CombineShiftMasterandWorkHolidayClassificationRqImpl(shiftMasterRp);
		
		List<String> shiftMasterCodes = shiftMasters.stream().map(s -> s.getShiftMasterCode().v()).collect(Collectors.toList());
		Map<ShiftMaster, Optional<WorkStyle>> shiftWorks = GetCombineShiftMasterandWorkHolidayClassificationService.get(shiftRequired, cid, shiftMasterCodes);
		
		
		return null;
	}

	@Data
	class WorkScheduleDto {
		private List<WorkShiftScheduleDto> schedules;
		private Map<ShiftMaster, Optional<AttendanceHolidayAttr>> shiftHollidayAtt;
	}

	@AllArgsConstructor
	private class CombineShiftMasterandWorkHolidayClassificationRqImpl
			implements GetCombineShiftMasterandWorkHolidayClassificationService.Required {

		@Inject
		private ShiftMasterRepository shiftMasterRp;

		@Override
		public List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode) {
			return shiftMasterRp.getByListShiftMaterCd(companyId, listShiftMaterCode);
		}

		@Override
		public List<ShiftMaster> getbyCidAndWorkInfo(String cid, List<WorkInformation> workInfos) {
			return shiftMasterRp.getbyCidAndWorkInfo(cid, workInfos);
		}

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
