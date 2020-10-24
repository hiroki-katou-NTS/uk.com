package nts.uk.screen.at.app.ksu003.getempworkfixedworkkinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu003.start.GetFixedWorkInformation;
import nts.uk.screen.at.app.ksu003.start.dto.BreakTimeOfDailyAttdDto;
import nts.uk.screen.at.app.ksu003.start.dto.EmployeeWorkScheduleDto;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInforDto;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInformationDto;
import nts.uk.shr.com.context.AppContexts;
/**
 * 社員勤務予定と勤務固定情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.社員勤務予定と勤務固定情報を取得する
 * @author phongtq
 *
 */
@Stateless
public class GetEmpWorkFixedWorkInfoSc {
	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject 
	private GetFixedWorkInformation fixedWorkInformation;
	
	public EmpWorkFixedWorkInfoDto getEmpWorkFixedWorkInfo(WorkInformationDto information) {
		EmpWorkFixedWorkInfoDto infoDto = null;
		EmployeeWorkScheduleDto workScheduleDto = null; // 社員勤務予定
		FixedWorkInforDto fixedWorkInforDto = null; // 勤務固定情報
		// 1 .create
		WorkInformation workInformation = new WorkInformation(information.getWorkType(), information.getWorkTime());
		
		// 2 .エラー状態をチェックする(Require)
		WorkInformationImpl require = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository, 
				workTimeSettingService, basicScheduleService);
		
		// 3 .勤務情報のエラー状態 <> 正常
		int errorStatusWorkInfo = workInformation.checkErrorCondition(require).value;
		String messErr = "";
		switch (errorStatusWorkInfo) {
		case 2:
			messErr = "Msg_29";
			break;
		
		case 3:
			messErr = "Msg_434";
			break;
		
		case 5:
			messErr = "Msg_591";
			break;
			
		case 7:
			messErr = "Msg_591";
			break;
			
		case 4:
			messErr = "Msg_590";
			break;
			
		case 6:
			messErr = "Msg_590";
			break;	

		default:
			break;
		}
		
		if(errorStatusWorkInfo != 1) {
			throw new BusinessException(messErr);
		}
		
		// 4 .勤務情報と補正済み所定時間帯を取得する(Require)
		Optional<WorkInfoAndTimeZone> timZone = workInformation.getWorkInfoAndTimeZone(require);
		
		// 5 .
		FixedWorkInformationDto fixedWorkInformationDto = fixedWorkInformation.getFixedWorkInfo(information);
		
		//List<休憩時間帯>
		List<BreakTimeOfDailyAttdDto> listBreakTimeZoneDto = fixedWorkInformationDto.getListBreakTimeZoneDto();
		
		// 
		List<TimeZone> listTimeZone = timZone.get().getListTimeZone();
		int startTime1 = listTimeZone.get(0).getStart().v();
		int endTime1 = listTimeZone.get(0).getEnd().v();
		
		// 
		int startTime2 = listTimeZone.get(1).getStart().v();
		int endTime2 = listTimeZone.get(1).getEnd().v();
		
		
		// 6 .
		workScheduleDto = new EmployeeWorkScheduleDto(startTime1, null, endTime1, 
				null, startTime2, null, endTime2, null, 
				listBreakTimeZoneDto, information.getWorkType(), null, 
				null, information.getWorkTime(), null);
		return infoDto;
	}

	@AllArgsConstructor
	public static class WorkInformationImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private WorkTimeSettingService workTimeSettingService;

		@Inject
		private BasicScheduleService basicScheduleService;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}

	}
}
