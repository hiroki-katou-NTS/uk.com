package nts.uk.screen.at.app.ksu003.getempworkfixedworkkinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
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
import nts.uk.screen.at.app.ksu003.start.dto.WorkInforDto;
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
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSet;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSet ;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;
	
	public EmpWorkFixedWorkInfoDto getEmpWorkFixedWorkInfo(List<WorkInforDto> information) {
		EmpWorkFixedWorkInfoDto infoDto = null;
		EmployeeWorkScheduleDto workScheduleDto = null; // 社員勤務予定
		FixedWorkInforDto fixedWorkInforDto = null; // 勤務固定情報
		// 1 .create
		WorkInformation workInformation = new WorkInformation(information.get(0).getWorkTypeCode(), information.get(0).getWorkTimeCode());
		
		// 2 .エラー状態をチェックする(Require)
		WorkInformationImpl require = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository, 
				workTimeSettingService, basicScheduleService, fixedWorkSet, flowWorkSet, flexWorkSet, predetemineTimeSet);
		
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
		fixedWorkInforDto = fixedWorkInformationDto.getFixedWorkInforDto().isEmpty() ? null : fixedWorkInformationDto.getFixedWorkInforDto().get(0);
		
		//List<休憩時間帯>
		List<TimeSpanForCalcDto> listBreakTimeZoneDto = fixedWorkInformationDto.getListBreakTimeZoneDto();
		
		// 
		List<TimeZone> listTimeZone = timZone.get().getTimeZones();
		Integer startTime1 = null, startTime2 = null;
		Integer endTime1 = null, endTime2 = null;
		
		if(!listTimeZone.isEmpty() && listTimeZone.get(0) != null) {
			startTime1 = listTimeZone.get(0).getStart().v();
			endTime1 = listTimeZone.get(0).getEnd().v();
		}
		
		if(!listTimeZone.isEmpty() && listTimeZone.size() > 1) {
			startTime2 = listTimeZone.get(1).getStart().v();
			endTime2 = listTimeZone.get(1).getEnd().v();
		}
		
		// 6 .
		workScheduleDto = new EmployeeWorkScheduleDto(startTime1, null, endTime1, 
				null, startTime2, null, endTime2, null, 
				listBreakTimeZoneDto, information.get(0).getWorkTypeCode(), null, 
				null, information.get(0).getWorkTimeCode(), null);
		infoDto = new EmpWorkFixedWorkInfoDto(null, workScheduleDto, fixedWorkInforDto);
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
		
		@Inject
		private FixedWorkSettingRepository fixedWorkSet;
		
		@Inject
		private FlowWorkSettingRepository flowWorkSet;
		
		@Inject
		private FlexWorkSettingRepository flexWorkSet ;
		
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSet;

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			Optional<FlowWorkSetting> workSetting =  flowWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

	}
}
