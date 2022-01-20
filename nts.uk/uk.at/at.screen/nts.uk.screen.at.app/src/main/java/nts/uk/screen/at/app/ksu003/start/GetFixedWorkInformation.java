package nts.uk.screen.at.app.ksu003.start;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.app.query.worktime.GetWorkingHoursInformationQuery;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
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
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

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

	@Inject
	private GetWorkingHoursInformationQuery hoursInformationQuery;

	public FixedWorkInformationDto getFixedWorkInfo(List<WorkInformation> information) {
		FixedWorkInformationDto fixedWorkInforDto = null;
		List<FixedWorkInforDto> inforDtos = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		BreakTimeKdl045Dto breakTime = null;
		FixedWorkInforDto inforDto = null;
		// 1 loop：勤務情報 in 勤務情報リスト
		for (WorkInformation x : information) {
			// 1.1 get(ログイン会社ID、取得した勤務種類コード):勤務種類
			Optional<WorkType> type = workTypeRepo.findByPK(cid, x.getWorkTypeCode().v());

			// 1.2 call 就業時間帯の必須チェック
			SetupType workTimeSetting = basicScheduleService
					.checkNeededOfWorkTimeSetting(x.getWorkTypeCode().v());

			List<ChangeableWorkingTimeZonePerNo> lstNo = new ArrayList<>();
			WorkInformation workInformation = new WorkInformation(x.getWorkTypeCode(),
					x.getWorkTimeCode());
			WorkInformationImpl impl = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository,
					workTimeSettingService, basicScheduleService, fixedWorkSet, flowWorkSet, flexWorkSet,
					predetemineTimeSet);

			// 1.3 必須任意不要区分 == 不要
			if (workTimeSetting == SetupType.NOT_REQUIRED) {
				inforDto = new FixedWorkInforDto(null, null, null, new ArrayList<>(), null, null,
						(type.isPresent() && type.get().getAbbreviationName() != null) ? type.get().getAbbreviationName().v() : TextResource.localize("KSU003_54"),
								null, null, null, null, true, SetupType.NOT_REQUIRED.name());
				inforDtos.add(inforDto);
				fixedWorkInforDto = new FixedWorkInformationDto(inforDtos,
						breakTime != null ? breakTime.getTimeZoneList() : new ArrayList<>());

				return fixedWorkInforDto;
			}

			// 1.4 勤務情報.就業時間帯コード.isPresent : 就業時間帯情報リストを取得する(会社ID, List<就業時間帯コード>) (New) : List<就業時間帯の設定>
			Optional<WorkTimeSetting> timeSettings = Optional.empty();
			if(x.getWorkTimeCodeNotNull().isPresent()) {
				timeSettings = workTimeSettingRepository.findByCode(cid, x.getWorkTimeCode().v());
//				if(timeSettings.isEmpty()) {
//					throw new RuntimeException("SystemError : List<就業時間帯の設定> is empty");
//				}
			}
			
			if(!timeSettings.isPresent()) {
				inforDto = new FixedWorkInforDto(
						null, null, null, new ArrayList<>(), null, null,
						type.isPresent() ? type.get().getAbbreviationName().v() : null, null, null, null, null, false, SetupType.OPTIONAL.name());
				inforDtos.add(inforDto);
				fixedWorkInforDto = new FixedWorkInformationDto(inforDtos,
						breakTime != null ? breakTime.getTimeZoneList() : new ArrayList<>());

				return fixedWorkInforDto;
			}
			// 1.5 必須任意不要区分 == 任意
			 if(workTimeSetting == SetupType.OPTIONAL){
				inforDto = new FixedWorkInforDto(
						x.getWorkTimeCodeNotNull().isPresent() ? timeSettings.get().getWorkTimeDisplayName().getWorkTimeAbName().v() : null,
						null, null, new ArrayList<>(), null, null, type.isPresent() ? type.get().getAbbreviationName().v() : null, 
						null, null, null, null, false, SetupType.OPTIONAL.name());
				inforDtos.add(inforDto);
				fixedWorkInforDto = new FixedWorkInformationDto(inforDtos,
						breakTime != null ? breakTime.getTimeZoneList() : new ArrayList<>());

				return fixedWorkInforDto;
			}

			// 1.6.1 1日半日出勤・1日休日系の判定（休出判定あり） : 出勤日区分
			AttendanceDayAttr dayAttr = type.get().chechAttendanceDay();

				// 1.7
				TimeZoneDto startTimeRange1 = null, endTimeRange1 = null, startTimeRange2 = null, endTimeRange2 = null;
					// 1.7.1 休憩時間帯を取得する(Require):Optional<休憩時間>
					Optional<BreakTimeZone> brkTime = workInformation.getBreakTimeZone(impl, cid);
					List<TimeSpanForCalcDto> calcDto = brkTime.get().getBreakTimes().stream()
							.map(y -> new TimeSpanForCalcDto(y.getStart().v(), y.getEnd().v()))
							.collect(Collectors.toList());
					// 1.7.1.1
					breakTime = new BreakTimeKdl045Dto(brkTime.get().isFixed(), calcDto);

					// 1.7.2 変更可能な勤務時間帯を取得する(Require)
					lstNo = workInformation.getChangeableWorkingTimezones(impl, cid);

					// List<勤務NOごとの変更可能な勤務時間帯>.isEmpty
//					if (lstNo.isEmpty()) {
//						throw new RuntimeException("SystemError : List<勤務NOごとの変更可能な勤務時間帯> is empty");
//					}

					// 1.7.3 取得する(就業時間帯コード) : 就業時間帯の勤務形態 (勤務形態を取得する)
					WorkTimeForm timeForm = null;
					try {
						timeForm = timeSettings.get().getWorkTimeDivision().getWorkTimeForm();
					} catch (Exception e) {
					}

					//  就業時間帯の勤務形態 == フレックス勤務用 : 取得する(勤務種類コード) : コアタイム時間帯
					Integer coreStartTime = null;
					Integer coreEndTime = null;
					String workTimeName = timeSettings.get().getWorkTimeDisplayName().getWorkTimeAbName() != null
							? timeSettings.get().getWorkTimeDisplayName().getWorkTimeAbName().v()
							: null;
					if (timeForm == WorkTimeForm.FLEX) {
						// 1.7.4 就業時間帯の勤務形態 == フレックス勤務用 : 取得する(Require, 勤務種類コード, 就業時間帯コード):
						// Optional<計算時間帯>
						WorkSettingImpl settingImpl = new WorkSettingImpl(workTypeRepo, flexWorkSet, predetemineTimeSet);
						Optional<TimeSpanForCalc> coreTime = GetTimezoneOfCoreTimeService.get(settingImpl,
								x.getWorkTypeCode(), x.getWorkTimeCode());
						if (coreTime.isPresent()) {
							coreStartTime = coreTime.get().getStart().v();
							coreEndTime = coreTime.get().getEnd().v();
						}

					}
					List<ChangeableWorkTimeDto> lstOverTime = new ArrayList<>();
					// 1.7.5  就業時間帯の勤務形態 == 固定勤務 : get(会社ID, 就業時間帯コード)
					if (timeForm == WorkTimeForm.FIXED) {
						// 1.7.5.1 就業時間帯の勤務形態 == 固定勤務 : get(会社ID, 就業時間帯コード):Optional<固定勤務設定>
						Optional<FixedWorkSetting> fixedSet = fixedWorkSet.findByKey(cid,
								x.getWorkTimeCode().v());
						// 1.7.5.2 固定勤務設定.isPresent : 指定した午前午後区分の残業時間帯を取得する(午前午後区分) : List<計算用時間帯>
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

//					switch (timeForm) {
//					case FLEX:
//					case FLOW:
//						break;
	//
//					case FIXED:
//						lstOverTime = overtimeHours;
//						break;
//					default :
//						break;
//					}
					// 1.6.7
					startTimeRange1 = new TimeZoneDto(
							new TimeOfDayDto(lstNo.get(0).getForStart().getStart().getDayDivision().value,
									lstNo.get(0).getForStart().getStart().v()),
							new TimeOfDayDto(lstNo.get(0).getForStart().getEnd().getDayDivision().value,
									lstNo.get(0).getForStart().getEnd().v()));
					endTimeRange1 = new TimeZoneDto(
							new TimeOfDayDto(lstNo.get(0).getForEnd().getStart().getDayDivision().value,
									lstNo.get(0).getForEnd().getStart().v()),
							new TimeOfDayDto(lstNo.get(0).getForEnd().getEnd().getDayDivision().value,
									lstNo.get(0).getForEnd().getEnd().v()));
					if (lstNo.size() > 1) {
						startTimeRange2 = new TimeZoneDto(
								new TimeOfDayDto(lstNo.get(1).getForStart().getStart().getDayDivision().value,
										lstNo.get(1).getForStart().getStart().v()),
								new TimeOfDayDto(lstNo.get(1).getForStart().getEnd().getDayDivision().value,
										lstNo.get(1).getForStart().getEnd().v()));
						endTimeRange2 = new TimeZoneDto(
								new TimeOfDayDto(lstNo.get(1).getForEnd().getStart().getDayDivision().value,
										lstNo.get(1).getForEnd().getStart().v()),
								new TimeOfDayDto(lstNo.get(1).getForEnd().getEnd().getDayDivision().value,
										lstNo.get(1).getForEnd().getEnd().v()));
					}
					Integer fixBreakTime = brkTime.isPresent() ? brkTime.get().isFixed() == true ? 1 : 0 : null;
					inforDto = new FixedWorkInforDto(workTimeName, coreStartTime, coreEndTime, lstOverTime, startTimeRange1,
							endTimeRange1, type.get().getAbbreviationName().v(), startTimeRange2, endTimeRange2,
							fixBreakTime, timeForm.value, false, SetupType.REQUIRED.name());
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
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

		// fix bug 113211
//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd,
//				Integer workNo) {
//			return workTimeSettingService.getPredeterminedTimezone(companyId, workTypeCd, workTimeCd, workNo);
//		}

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return fixedWorkSet.findByKey(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flowWorkSet.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			return flexWorkSet.find(companyId, workTimeCode.v());
		}
		
		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSet.findByWorkTimeCode(companyId, workTimeCode.v());
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
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return predetemineTimeSet.findByWorkTimeCode(companyId, workTimeCode.v());
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
