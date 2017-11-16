package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.WorkScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkPlaceSidDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidDto;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ReflectWorkInforDomainServiceImpl implements ReflectWorkInforDomainService {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;

	@Inject
	private IdentificationRepository identificationRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;

	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyPerformanceRepository;

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Inject
	private AffClassificationAdapter affClassificationAdapter;

	@Inject
	private AffJobTitleAdapter affJobTitleAdapter;

	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;

	@Inject
	private BasicScheduleAdapter basicScheduleAdapter;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public boolean reflectWorkInformation(List<String> employeeIds, DatePeriod periodTime, String empCalAndSumExecLogID,
			int reCreateAttr) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<WorkInfoOfDailyPerformance> workInfoOfDailyPerformances = new ArrayList<>();

		// lits day between startDate and endDate
		List<GeneralDate> listDay = this.getDaysBetween(periodTime.start(), periodTime.end());

		// ドメインモデル「日別実績の勤務情報」を削除する - rerun
		if (reCreateAttr == 0) {
			this.workInformationRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.approvalStatusOfDailyPerforRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.affiliationInforOfDailyPerforRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.identificationRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.timeLeavingOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.temporaryTimeOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.editStateOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.breakTimeOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.outingTimeOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
		}
		// ドメインモデル「日別実績の勤務情報」を取得する - not rerun
		else {
			workInfoOfDailyPerformances = this.workInformationRepository.findByListEmployeeId(employeeIds, listDay);
		}

		if (workInfoOfDailyPerformances.isEmpty()) {
			List<ErrMessageInfo> errMesInfos = new ArrayList<>();

			List<AffiliationInforOfDailyPerfor> affiliationInforOfDailyPerfors = new ArrayList<>();

			// Imported(就業．勤務実績)「所属雇用履歴」を取得する
			// param List<String> employeeIds, List<GeneralDate> processingDate
			List<EmploymentHistoryImported> employmentHistoryImporteds = new ArrayList<>();

			// Imported(就業．勤務実績)「所属職場履歴」を取得する
			// param List<String> employeeIds, GeneralDate processingDates
			// data : employeeId, workPlaceId
			List<AffWorkPlaceSidDto> affWorkplaceDtos = new ArrayList<>();

			// Imported(就業．勤務実績)「所属分類履歴」を取得する
			// param List<String> employeeIds, GeneralDate processingDates
			// data : employeeId, classificationCode
			List<AffClassificationSidDto> affClassificationSidDtos = new ArrayList<>();

			// Imported(就業．勤務実績)「所属職位履歴」を取得する
			// param List<String> employeeIds, GeneralDate processingDates
			// data employeeId, jobTitle
			List<AffJobTitleSidDto> affJobTitleSidDtos = new ArrayList<>();

			employeeIds.forEach(employeeId -> {
				listDay.forEach(day -> {
					Optional<EmploymentHistoryImported> employmentHasData = employmentHistoryImporteds.stream()
							.filter(item -> item.getEmployeeId().equals(employeeId)
									&& item.getPeriod().start().beforeOrEquals(day)
									&& item.getPeriod().end().afterOrEquals(day) && item.getEmploymentCode() != null)
							.findAny();

					Optional<AffWorkPlaceSidDto> workPlaceHasData = affWorkplaceDtos.stream()
							.filter(item -> item.getEmployeeId().equals(employeeId)
									&& item.getDateRange().start().beforeOrEquals(day)
									&& item.getDateRange().end().afterOrEquals(day) && item.getWorkPlaceId() != null)
							.findAny();

					Optional<AffClassificationSidDto> classificationHasData = affClassificationSidDtos.stream()
							.filter(item -> item.getEmployeeId().equals(employeeId)
									&& item.getDateRange().start().beforeOrEquals(day)
									&& item.getDateRange().end().afterOrEquals(day)
									&& item.getClassificationCode() != null)
							.findAny();

					Optional<AffJobTitleSidDto> jobTitleHasData = affJobTitleSidDtos.stream()
							.filter(item -> item.getEmployeeId().equals(employeeId)
									&& item.getDateRange().start().beforeOrEquals(day)
									&& item.getDateRange().end().afterOrEquals(day) && item.getJobTitleId() != null)
							.findAny();

					// 取得したImported(就業．勤務実績)「所属雇用履歴」が存在するか確認する
					// 存在しない - no data
					if (!employmentHasData.isPresent()) {
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("001"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent("Msg_426"));
						errMesInfos.add(employmentErrMes);
					}
					if (!workPlaceHasData.isPresent()) {
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("002"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent("Msg_427"));
						errMesInfos.add(employmentErrMes);
					}
					if (!classificationHasData.isPresent()) {
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("003"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent("Msg_428"));
						errMesInfos.add(employmentErrMes);
					}
					if (!jobTitleHasData.isPresent()) {
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("004"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent("Msg_429"));
						errMesInfos.add(employmentErrMes);
					}

					// 存在する - has data
					if (employmentHasData.isPresent() && workPlaceHasData.isPresent()
							&& classificationHasData.isPresent() && jobTitleHasData.isPresent()) {
						AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor = new AffiliationInforOfDailyPerfor(
								new EmploymentCode(employmentHasData.get().getEmploymentCode()), employeeId,
								jobTitleHasData.get().getJobTitleId(), workPlaceHasData.get().getWorkPlaceId(), day,
								new ClassificationCode(classificationHasData.get().getClassificationCode()), null);
						affiliationInforOfDailyPerfors.add(affiliationInforOfDailyPerfor);
					}
				});
			});

			// Imported(就業.勤務実績)「社員の勤務予定管理」を取得する
			this.personalWorking(employeeIds, listDay, empCalAndSumExecLogID, affiliationInforOfDailyPerfors);

			errMesInfos.forEach(action -> {
				this.errMessageInfoRepository.add(action);
			});

		}

		return false;
	}

	private void personalWorking(List<String> employeeIds, List<GeneralDate> listDay, String empCalAndSumExecLogID,
			List<AffiliationInforOfDailyPerfor> affiliationInforOfDailyPerfors) {

		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		Map<String, AffiliationInforOfDailyPerfor> affiationInfoEmployeeIdMap = affiliationInforOfDailyPerfors.stream()
				.collect(Collectors.toMap(AffiliationInforOfDailyPerfor::getEmployeeId, x -> x));

		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		// param List<String> employeeIds, List<GeneralDate> listday
		List<PersonalLaborCondition> personalLaborConditions = new ArrayList<>();

		// Imported(就業.勤務実績)「勤務予定基本情報」を取得する
		List<BasicScheduleSidDto> basicScheduleSidDtos = new ArrayList<>();

		// Imported(就業.勤務実績)「勤務予定時間帯」を取得する
		List<WorkScheduleSidDto> workScheduleSidDtos = new ArrayList<>();

		employeeIds.forEach(employeeID -> {
			listDay.forEach(day -> {
				Optional<PersonalLaborCondition> personalLaborHasData = personalLaborConditions.stream()
						.filter(item -> item.getEmployeeId().equals(employeeID)
								&& item.getPeriod().start().beforeOrEquals(day)
								&& item.getPeriod().end().afterOrEquals(day))
						.findAny();

				Optional<BasicScheduleSidDto> basicScheduleHasData = basicScheduleSidDtos.stream()
						.filter(item -> item.getSId().equals(employeeID) && item.getDate().equals(day)).findAny();

				Optional<WorkScheduleSidDto> workScheduleHasData = workScheduleSidDtos.stream()
						.filter(item -> item.getSId().equals(employeeID) && item.getDate().equals(day)).findAny();

				// 取得したImported(就業．勤務実績)「社員の勤務予定管理」が存在するか確認する - check data
				// 存在しない - no data
				if (!personalLaborHasData.isPresent()) {
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
							new ErrMessageResource("005"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
							new ErrMessageContent("Msg_430"));
					errMesInfos.add(employmentErrMes);
				}

				// 取得したImported(就業.勤務実績)「勤務予定基本情報」が存在するか確認する
				// 存在しない - no data
				if (!basicScheduleHasData.isPresent()) {
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
							new ErrMessageResource("006"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
							new ErrMessageContent("Msg_431"));
					errMesInfos.add(employmentErrMes);
				}
				// 存在する - has data
				else {
					this.workInformationRepository.updateWorkInfo(employeeID, day,
							basicScheduleHasData.get().getWorkTypeCode(), basicScheduleHasData.get().getWorkTimeCode());
				}

				// 取得したImported(就業.勤務実績)「勤務予定時間帯」が存在するか確認する
				// 存在しない - no data
				if (!workScheduleHasData.isPresent()) {
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
							new ErrMessageResource("007"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
							new ErrMessageContent("Msg_432"));
					errMesInfos.add(employmentErrMes);
				}

				// copy information for employeeId has data
				this.workInformationRepository.updateScheduleTime(employeeID, day,
						new BigDecimal(workScheduleHasData.get().getScheduleCnt()),
						workScheduleHasData.get().getScheduleStartClock(),
						workScheduleHasData.get().getScheduleEndClock());
				if (workScheduleHasData.get().getBounceAtr() == 0) {
					this.workInformationRepository.updateDirectLine(employeeID, day, 0, 1);
				} else if (workScheduleHasData.get().getBounceAtr() == 1) {
					this.workInformationRepository.updateDirectLine(employeeID, day, 1, 1);
				} else if (workScheduleHasData.get().getBounceAtr() == 2) {
					this.workInformationRepository.updateDirectLine(employeeID, day, 1, 0);
				} else if (workScheduleHasData.get().getBounceAtr() == 3) {
					this.workInformationRepository.updateDirectLine(employeeID, day, 0, 0);
				}

				// 個人情報に処理中の曜日の設定が存在するか確認する
				// 存在する - has data
				if (personalLaborHasData.get().getWorkDayOfWeek().equals(day.dayOfWeek())) {
					// monday
					if (day.dayOfWeek() == 1) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getMonday().get().getWorkTimeCode().get()
										.v(),
								personalLaborHasData.get().getWorkDayOfWeek().getMonday().get().getWorkTypeCode().v());
					}
					// tuesday
					else if (day.dayOfWeek() == 2) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getTuesday().get().getWorkTimeCode().get()
										.v(),
								personalLaborHasData.get().getWorkDayOfWeek().getTuesday().get().getWorkTypeCode().v());
					}
					// wednesday
					else if (day.dayOfWeek() == 3) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getWednesday().get().getWorkTimeCode()
										.get().v(),
								personalLaborHasData.get().getWorkDayOfWeek().getWednesday().get().getWorkTypeCode()
										.v());
					}
					// thursday
					else if (day.dayOfWeek() == 4) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getThursday().get().getWorkTimeCode()
										.get().v(),
								personalLaborHasData.get().getWorkDayOfWeek().getThursday().get().getWorkTypeCode()
										.v());
					}
					// friday
					else if (day.dayOfWeek() == 5) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getFriday().get().getWorkTimeCode().get()
										.v(),
								personalLaborHasData.get().getWorkDayOfWeek().getFriday().get().getWorkTypeCode().v());
					}
					// saturday
					else if (day.dayOfWeek() == 6) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getSaturday().get().getWorkTimeCode()
										.get().v(),
								personalLaborHasData.get().getWorkDayOfWeek().getSaturday().get().getWorkTypeCode()
										.v());
					}
					// sunday
					else if (day.dayOfWeek() == 7) {
						this.workInformationRepository.updateDayOfWeek(employeeID, day,
								personalLaborHasData.get().getWorkDayOfWeek().getSunday().get().getWorkTimeCode().get()
										.v(),
								personalLaborHasData.get().getWorkDayOfWeek().getSunday().get().getWorkTypeCode().v());
					}
				}
				// 存在しない - no data
				else {
					this.workInformationRepository.updateDayOfWeek(employeeID, day,
							personalLaborHasData.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().v(),
							personalLaborHasData.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().v());
				}

				// 直行直帰区分を写す - autoStampSetAtr of PersonalLaborCondition root
				if (personalLaborHasData.get().getAutoStampSetAtr().value == 0) {
					Optional<WorkInfoOfDailyPerformance> workInfoOfDaily = this.workInformationRepository
							.find(employeeID, day);
					String workTypeCode = workInfoOfDaily.get().getRecordWorkInformation().getWorkTypeCode().v();
					Optional<WorkType> workType = this.workTypeRepository.findByPK(companyId, workTypeCode);
					// TODO
					// 打刻の扱い方に従って、直行区分、直帰区分を更新 - last step. can not find
				}
				;

				// カレンダー情報を取得する
				// affiationInfoEmployeeIdMap.get(employeeID).getWplID();
				this.calendarInformation(companyId, affiationInfoEmployeeIdMap.get(employeeID).getWplID(),
						affiationInfoEmployeeIdMap.get(employeeID).getClsCode().v(), day);

			});
		});

		this.errMessageInfoRepository.addList(errMesInfos);
	}

	private void calendarInformation(String companyId, String workPlaceId, String classificationCode,
			GeneralDate date) {
		
	};

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

}
