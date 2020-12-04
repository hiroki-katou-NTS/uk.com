package nts.uk.screen.at.app.ksu003.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.GetTimezoneOfCoreTimeService;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.kdl045.query.BreakTimeKdl045Dto;
import nts.uk.screen.at.app.ksu003.start.dto.ChangeableWorkTimeDto;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInforDto;
import nts.uk.screen.at.app.ksu003.start.dto.FixedWorkInformationDto;
import nts.uk.screen.at.app.ksu003.start.dto.TimeOfDayDto;
import nts.uk.screen.at.app.ksu003.start.dto.TimeZoneDto;
import nts.uk.screen.at.app.ksu003.start.dto.WorkInforDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務固定情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).A：個人スケジュール修正(日付別).メニュー別OCD.勤務固定情報を取得する
 * 
 * @author phongtq
 *
 */
@Stateless
public class GetFixedWorkInformation {

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
	private FlexWorkSettingRepository flexWorkSet;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;

	public FixedWorkInformationDto getFixedWorkInfo(List<WorkInforDto> information) {
		FixedWorkInformationDto fixedWorkInforDto = null;
		List<FixedWorkInforDto> inforDtos = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		BreakTimeKdl045Dto breakTime = null;
		FixedWorkInforDto inforDto = null;

		// 1 get(ログイン会社ID、取得した勤務種類コード):勤務種類
		Optional<WorkType> type = workTypeRepo.findByPK(cid, information.get(0).getWorkTypeCode());

		// 2 1日半日出勤・1日休日系の判定（休出判定あり） : 出勤日区分
		AttendanceDayAttr dayAttr = type.get().chechAttendanceDay();

		// 3 変更可能な勤務時間帯を取得する(Require):List<勤務NOごとの変更可能な勤務時間帯>
		List<ChangeableWorkingTimeZonePerNo> lstNo = new ArrayList<>();
		WorkInformation workInformation = new WorkInformation(information.get(0).getWorkTypeCode(),
				information.get(0).getWorkTimeCode());
		WorkInformationImpl impl = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository,
				workTimeSettingService, basicScheduleService, fixedWorkSet, flowWorkSet, flexWorkSet,
				predetemineTimeSet);
		
		// 4 出勤日区分 == 1日休日系
		if(dayAttr == AttendanceDayAttr.HOLIDAY) {
			inforDto = new FixedWorkInforDto(null, null, null, new ArrayList<>(), null, null,
					type.get().getAbbreviationName().v(), null, null, null, null);
			inforDtos.add(inforDto);
		} else {
			lstNo = workInformation.getChangeableWorkingTimezones(impl);
		}

		// 5 List<勤務NOごとの変更可能な勤務時間帯>isPresentList
		if (!lstNo.isEmpty()) {

			for (ChangeableWorkingTimeZonePerNo x : lstNo) {

				// 5.1 休憩時間帯を取得する(Require):Optional<休憩時間>
				Optional<BreakTimeZone> brkTime = workInformation.getBreakTimeZone(impl);
				// 5.1.1
				List<TimeSpanForCalcDto> calcDto = brkTime.get().getBreakTimes().stream()
						.map(y -> new TimeSpanForCalcDto(y.getStart().v(), y.getEnd().v()))
						.collect(Collectors.toList());
				breakTime = new BreakTimeKdl045Dto(brkTime.get().isFixed(), calcDto);

				// 5.2 get(ログイン会社ID、取得した就業時間帯コード):就業時間帯の設定
				Optional<WorkTimeSetting> worktimeSet = workTimeSettingRepository.findByCode(cid,
						information.get(0).getWorkTimeCode());

				// 5.3 取得する(就業時間帯コード) : 就業時間帯の勤務形態 (勤務形態を取得する)
				WorkTimeForm timeForm = null;
				try {
					timeForm = worktimeSet.get().getWorkTimeDivision().getWorkTimeForm();
				} catch (Exception e) {
				}

				// 就業時間帯の勤務形態 == フレックス勤務用 : 取得する(勤務種類コード) : コアタイム時間帯
				Integer coreStartTime = null;
				Integer coreEndTime = null;
				String workTimeName = worktimeSet.isPresent()
						? worktimeSet.get().getWorkTimeDisplayName().getWorkTimeAbName().v()
						: null;
				if (timeForm == WorkTimeForm.FLEX) {
					// 5.4 就業時間帯の勤務形態 == フレックス勤務用 : 取得する(Require, 勤務種類コード, 就業時間帯コード):
					// Optional<計算時間帯>
					WorkSettingImpl settingImpl = new WorkSettingImpl(workTypeRepo, flexWorkSet, predetemineTimeSet);
					Optional<TimeSpanForCalc> coreTime = GetTimezoneOfCoreTimeService.get(settingImpl,
							new WorkTypeCode(information.get(0).getWorkTypeCode()),
							new WorkTimeCode(information.get(0).getWorkTimeCode()));
					if (coreTime.isPresent()) {
						coreStartTime = coreTime.get().getStart().v();
						coreEndTime = coreTime.get().getEnd().v();
					}

				}
				List<ChangeableWorkTimeDto> lstOverTime = new ArrayList<>();
				// 5.5 就業時間帯の勤務形態 == 固定勤務 : get(会社ID, 就業時間帯コード):Optional<固定勤務設定>
				if (timeForm == WorkTimeForm.FIXED) {
					// 5.6
					Optional<FixedWorkSetting> fixedSet = fixedWorkSet.findByKey(cid,
							information.get(0).getWorkTimeCode());
					if (fixedSet.isPresent()) {
						List<TimeSpanForCalc> lstOver = fixedSet.get()
								.getTimeZoneOfOvertimeWorkByAmPmAtr(dayAttr.toAmPmAtr().get());
						for (int i = 1; i <= lstOver.size(); i++) {
							ChangeableWorkTimeDto timeDto = new ChangeableWorkTimeDto(i,
									lstOver.get(i - 1).getStart().v(), lstOver.get(i - 1).getEnd().v());
							lstOverTime.add(timeDto);
						}
					}
				}

//				switch (timeForm) {
//				case FLEX:
//				case FLOW:
//					break;
//
//				case FIXED:
//					lstOverTime = overtimeHours;
//					break;
//				default :
//					break;
//				}
				// 5.7
				TimeZoneDto startTimeRange1 = null, endTimeRange1 = null, startTimeRange2 = null, endTimeRange2 = null;
				if (x.getWorkNo().v() == 1) {
					startTimeRange1 = new TimeZoneDto(
							new TimeOfDayDto(x.getForStart().getStart().getDayDivision().value,
									x.getForStart().getStart().v()),
							new TimeOfDayDto(x.getForStart().getEnd().getDayDivision().value,
									x.getForStart().getEnd().v()));
					endTimeRange1 = new TimeZoneDto(
							new TimeOfDayDto(x.getForEnd().getStart().getDayDivision().value,
									x.getForEnd().getStart().v()),
							new TimeOfDayDto(x.getForEnd().getEnd().getDayDivision().value,
									x.getForEnd().getEnd().v()));
				} else if (x.getWorkNo().v() == 2) {
					startTimeRange2 = new TimeZoneDto(
							new TimeOfDayDto(x.getForStart().getStart().getDayDivision().value,
									x.getForStart().getStart().v()),
							new TimeOfDayDto(x.getForStart().getEnd().getDayDivision().value,
									x.getForStart().getEnd().v()));
					endTimeRange2 = new TimeZoneDto(
							new TimeOfDayDto(x.getForEnd().getStart().getDayDivision().value,
									x.getForEnd().getStart().v()),
							new TimeOfDayDto(x.getForEnd().getEnd().getDayDivision().value,
									x.getForEnd().getEnd().v()));
				}
				Integer fixBreakTime = brkTime.isPresent() ? brkTime.get().isFixed() == true ? 1 : 0 : null;
				inforDto = new FixedWorkInforDto(workTimeName, coreStartTime, coreEndTime, lstOverTime, startTimeRange1,
						endTimeRange1, type.get().getAbbreviationName().v(), startTimeRange2, endTimeRange2,
						fixBreakTime, timeForm.value);
				inforDtos.add(inforDto);
			}

		} else {
			// 6
			inforDto = new FixedWorkInforDto(null, null, null, new ArrayList<>(), null, null,
					type.get().getAbbreviationName().v(), null, null, null, null);
			inforDtos.add(inforDto);
		}

		fixedWorkInforDto = new FixedWorkInformationDto(inforDtos,
				breakTime != null ? breakTime.getTimeZoneList() : new ArrayList<>());

		return fixedWorkInforDto;
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
		private FlexWorkSettingRepository flexWorkSet;

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
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTypeCd, workTimeCd, workNo);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			Optional<FlowWorkSetting> workSetting = flowWorkSet.find(companyId, code.v());
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

	@AllArgsConstructor
	public static class WorkSettingImpl implements GetTimezoneOfCoreTimeService.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private FlexWorkSettingRepository flexWorkSet;

		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSet;

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}

		@Override
		public Optional<WorkType> getWorkType(WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<FlexWorkSetting> getFlexWorkSetting(WorkTimeCode workTimeCode) {
			Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, workTimeCode.v());
			return workSetting;
		}

	}

}
