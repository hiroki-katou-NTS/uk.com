package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.WorkScheduleSidImport;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.CalendarInfoImport;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.RecCalendarCompanyAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkPlaceSidImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.primitivevalue.ClassificationCode;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.AutoStampForFutureDayClass;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.AutomaticStampSetDetailOutput;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidImport;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTimeCode;
import nts.uk.ctx.at.record.dom.workinformation.primitivevalue.WorkTypeCode;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.DailyRecreateClassification;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	private AffWorkplaceAdapter affWorkplaceAdapter;

	@Inject
	private AffClassificationAdapter affClassificationAdapter;

	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;

	@Inject
	private AffJobTitleAdapter affJobTitleAdapter;

	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;

	@Inject
	private BasicScheduleAdapter basicScheduleAdapter;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private RecCalendarCompanyAdapter calendarCompanyAdapter;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private StampReflectionManagementRepository stampReflectionManagementRepository;

	@Inject
	private ReflectStampDomainService reflectStampDomainServiceImpl;

	@Override
	public void reflectWorkInformation(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr) {

		AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor = new AffiliationInforOfDailyPerfor();

		// Get Data
		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		// Imported(就業．勤務実績)「所属職場履歴」を取得する
		Optional<AffWorkPlaceSidImport> workPlaceHasData = this.affWorkplaceAdapter.findBySidAndDate(employeeId, day);

		// ドメインモデル「日別実績の勤務情報」を削除する - rerun
		if (reCreateAttr == ExecutionType.RERUN) {
			this.workInformationRepository.delete(employeeId, day);
			// this.approvalStatusOfDailyPerforRepository.delete(employeeId,
			// day);
			this.affiliationInforOfDailyPerforRepository.delete(employeeId, day);
			// this.identificationRepository.delete(employeeId, day);
			// this.timeLeavingOfDailyPerformanceRepository.delete(employeeId,
			// day);
			// this.temporaryTimeOfDailyPerformanceRepository.delete(employeeId,
			// day);
			// this.editStateOfDailyPerformanceRepository.delete(employeeId,
			// day);
			// this.breakTimeOfDailyPerformanceRepository.delete(employeeId,
			// day);
			// this.outingTimeOfDailyPerformanceRepository.delete(employeeId,
			// day);
		}
		// ドメインモデル「日別実績の勤務情報」を取得する - not rerun
		if (!this.workInformationRepository.find(employeeId, day).isPresent()) {

			// Imported(就業．勤務実績)「所属雇用履歴」を取得する
			Optional<SyEmploymentImport> employmentHasData = this.syEmploymentAdapter.findByEmployeeId(companyId,
					employeeId, day);

			// Imported(就業．勤務実績)「所属分類履歴」を取得する
			Optional<AffClassificationSidImport> classificationHasData = this.affClassificationAdapter
					.findByEmployeeId(companyId, employeeId, day);

			// Imported(就業．勤務実績)「所属職位履歴」を取得する
			Optional<AffJobTitleSidImport> jobTitleHasData = this.affJobTitleAdapter.findByEmployeeId(employeeId, day);

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
			if (employmentHasData.isPresent() && workPlaceHasData.isPresent() && classificationHasData.isPresent()
					&& jobTitleHasData.isPresent()) {
				affiliationInforOfDailyPerfor = new AffiliationInforOfDailyPerfor(
						new EmploymentCode(employmentHasData.get().getEmploymentCode()), employeeId,
						jobTitleHasData.get().getJobTitleId(), workPlaceHasData.get().getWorkplaceId(), day,
						new ClassificationCode(classificationHasData.get().getClassificationCode()), null);
			}
			// Imported(就業.勤務実績)「社員の勤務予定管理」を取得する
			this.workschedule(companyId, employeeId, day, empCalAndSumExecLogID, affiliationInforOfDailyPerfor,
					workPlaceHasData);

			errMesInfos.forEach(action -> {
				this.errMessageInfoRepository.add(action);
			});
		}
	}

	private void workschedule(String companyId, String employeeID, GeneralDate day, String empCalAndSumExecLogID,
			AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor,
			Optional<AffWorkPlaceSidImport> workPlaceHasData) {

		// status
		// 正常終了 : 0
		// 中断 : 1

		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		// ドメインモデル「個人労働条件．予定管理区分」を取得する
		Optional<PersonalLaborCondition> personalLaborHasData = this.personalLaborConditionRepository
				.findById(employeeID, day);

		// Imported(就業.勤務実績)「勤務予定基本情報」を取得する
		Optional<BasicScheduleSidDto> basicScheduleHasData = this.basicScheduleAdapter.findAllBasicSchedule(employeeID,
				day);

		// Imported(就業.勤務実績)「勤務予定時間帯」を取得する
		List<WorkScheduleSidImport> workScheduleHasData = basicScheduleHasData.get().getWorkScheduleSidImports();

		// ドメインモデル「打刻反映管理」を取得する
		Optional<StampReflectionManagement> stampReflectionManagement = this.stampReflectionManagementRepository
				.findByCid(companyId);
		// 日別実績の出退勤
		TimeLeavingOfDailyPerformance timeLeavingOptional = new TimeLeavingOfDailyPerformance();

		// check data
		// 存在しない - no data
		WorkInfoOfDailyPerformance workInfoOfDailyPerformanceUpdate = new WorkInfoOfDailyPerformance();

		workInfoOfDailyPerformanceUpdate.setEmployeeId(employeeID);
		workInfoOfDailyPerformanceUpdate.setCalculationState(CalculationState.Calculated);
		workInfoOfDailyPerformanceUpdate.setYmd(day);

		if (!personalLaborHasData.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
					new ErrMessageResource("005"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent("Msg_430"));
			errMesInfos.add(employmentErrMes);
		} else {
			if (personalLaborHasData.get().getScheduleManagementAtr() == UseAtr.USE) {
				// 勤務予定から勤務種類と就業時間帯を写す
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
					workInfoOfDailyPerformanceUpdate.setScheduleWorkInformation(
							new WorkInformation(basicScheduleHasData.get().getWorkTypeCode(),
									basicScheduleHasData.get().getWorkTimeCode()));
					workInfoOfDailyPerformanceUpdate
							.setRecordWorkInformation(new WorkInformation(basicScheduleHasData.get().getWorkTypeCode(),
									basicScheduleHasData.get().getWorkTimeCode()));
				}

				// 取得したImported(就業.勤務実績)「勤務予定時間帯」が存在するか確認する
				// 存在しない - no data
				if (workScheduleHasData.isEmpty()) {
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeID, empCalAndSumExecLogID,
							new ErrMessageResource("007"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
							new ErrMessageContent("Msg_432"));
					errMesInfos.add(employmentErrMes);
				}

				// copy information for employeeId has data
				List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();
				workScheduleHasData.forEach(items -> {

					if (items.getBounceAtr() == 3) {
						workInfoOfDailyPerformanceUpdate.setBackStraightAtr(NotUseAttribute.Not_use);
						workInfoOfDailyPerformanceUpdate.setGoStraightAtr(NotUseAttribute.Not_use);
					} else if (items.getBounceAtr() == 2) {
						workInfoOfDailyPerformanceUpdate.setBackStraightAtr(NotUseAttribute.Not_use);
						workInfoOfDailyPerformanceUpdate.setGoStraightAtr(NotUseAttribute.Use);
					} else if (items.getBounceAtr() == 0) {
						workInfoOfDailyPerformanceUpdate.setBackStraightAtr(NotUseAttribute.Use);
						workInfoOfDailyPerformanceUpdate.setGoStraightAtr(NotUseAttribute.Not_use);
					} else if (items.getBounceAtr() == 1) {
						workInfoOfDailyPerformanceUpdate.setBackStraightAtr(NotUseAttribute.Use);
						workInfoOfDailyPerformanceUpdate.setGoStraightAtr(NotUseAttribute.Use);
					}

					ScheduleTimeSheet scheduleTimeSheet = new ScheduleTimeSheet();
					scheduleTimeSheet.setWorkNo(new WorkNo(new BigDecimal(items.getScheduleCnt())));
					scheduleTimeSheet.setAttendance(new TimeWithDayAttr(items.getScheduleStartClock()));
					scheduleTimeSheet.setLeaveWork(new TimeWithDayAttr(items.getScheduleStartClock()));
					scheduleTimeSheets.add(scheduleTimeSheet);
				});

				workInfoOfDailyPerformanceUpdate.setScheduleTimeSheets(scheduleTimeSheets);

			} else {
				// 個人情報から勤務種類と就業時間帯を写す
				// 個人情報に処理中の曜日の設定が存在するか確認する
				// 存在する - has data
				WorkInformation recordWorkInformation = new WorkInformation();
				if (personalLaborHasData.get().getWorkDayOfWeek().equals(day.dayOfWeek())) {
					// monday
					if (day.dayOfWeek() == 1) {
						// this.workInformationRepository.updateRecordWorkInfo(employeeID,
						// day,
						// personalLaborHasData.get().getWorkDayOfWeek().getMonday().get()
						// .getWorkTimeCode().get().v(),
						// personalLaborHasData.get().getWorkDayOfWeek().getMonday().get()
						// .getWorkTypeCode().v());
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(
								personalLaborHasData.get().getWorkDayOfWeek().getMonday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getMonday()
								.get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getMonday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// tuesday
					else if (day.dayOfWeek() == 2) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(personalLaborHasData.get()
								.getWorkDayOfWeek().getTuesday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getTuesday()
								.get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getTuesday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// wednesday
					else if (day.dayOfWeek() == 3) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(personalLaborHasData.get()
								.getWorkDayOfWeek().getWednesday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek()
								.getWednesday().get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getWednesday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// thursday
					else if (day.dayOfWeek() == 4) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(personalLaborHasData.get()
								.getWorkDayOfWeek().getThursday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek()
								.getThursday().get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getThursday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// friday
					else if (day.dayOfWeek() == 5) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(
								personalLaborHasData.get().getWorkDayOfWeek().getFriday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getFriday()
								.get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getFriday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// saturday
					else if (day.dayOfWeek() == 6) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(personalLaborHasData.get()
								.getWorkDayOfWeek().getSaturday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek()
								.getSaturday().get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getSaturday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
					// sunday
					else if (day.dayOfWeek() == 7) {
						recordWorkInformation.setWorkTypeCode(new WorkTypeCode(
								personalLaborHasData.get().getWorkDayOfWeek().getSunday().get().getWorkTypeCode().v()));
						recordWorkInformation.setWorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getSunday()
								.get().getWorkTimeCode().isPresent()
										? new WorkTimeCode(personalLaborHasData.get().getWorkDayOfWeek().getSunday()
												.get().getWorkTimeCode().get().v())
										: new WorkTimeCode(""));
					}
				}
				// 存在しない - no data
				else {
					recordWorkInformation.setWorkTypeCode(new WorkTypeCode(
							personalLaborHasData.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().v()));
					recordWorkInformation.setWorkTimeCode(
							personalLaborHasData.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent()
									? new WorkTimeCode(personalLaborHasData.get().getWorkCategory().getWeekdayTime()
											.getWorkTimeCode().get().v())
									: new WorkTimeCode(""));
				}

				workInfoOfDailyPerformanceUpdate.setRecordWorkInformation(recordWorkInformation);

				// 直行直帰区分を写す - autoStampSetAtr of PersonalLaborCondition
				// 自動打刻セット区分を判断
				if (personalLaborHasData.get().getAutoStampSetAtr().value == 0) {
					String workTypeCode = workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTypeCode()
							.v();
					Optional<WorkType> workType = this.workTypeRepository.findByPK(companyId, workTypeCode);
					// 打刻の扱い方に従って、直行区分、直帰区分を更新
					if (workType.get().getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {
						if (workType.get().getWorkTypeSetList().get(0).getAttendanceTime() == WorkTypeSetCheck.CHECK) {
							workInfoOfDailyPerformanceUpdate
									.setGoStraightAtr(EnumAdaptor.valueOf(1, NotUseAttribute.class));
						} else if (workType.get().getWorkTypeSetList().get(0)
								.getAttendanceTime() == WorkTypeSetCheck.NO_CHECK) {
							workInfoOfDailyPerformanceUpdate
									.setGoStraightAtr(EnumAdaptor.valueOf(0, NotUseAttribute.class));
						}
						if (workType.get().getWorkTypeSetList().get(0).getTimeLeaveWork() == WorkTypeSetCheck.CHECK) {
							workInfoOfDailyPerformanceUpdate
									.setBackStraightAtr(EnumAdaptor.valueOf(1, NotUseAttribute.class));
						} else if (workType.get().getWorkTypeSetList().get(0)
								.getTimeLeaveWork() == WorkTypeSetCheck.NO_CHECK) {
							workInfoOfDailyPerformanceUpdate
									.setBackStraightAtr(EnumAdaptor.valueOf(0, NotUseAttribute.class));
						}
					}
				}
				;

				// カレンダー情報を取得する
				// a part of Du's team
				CalendarInfoImport calendarInfoDto = calendarCompanyAdapter.findCalendarCompany(companyId,
						affiliationInforOfDailyPerfor.getWplID(), affiliationInforOfDailyPerfor.getClsCode().v(), day);
				WorkInformation scheduleWorkInformation = new WorkInformation(calendarInfoDto.getWorkTimeCode(),
						calendarInfoDto.getWorkTypeCode());
				workInfoOfDailyPerformanceUpdate.setScheduleWorkInformation(scheduleWorkInformation);
				workInfoOfDailyPerformanceUpdate.setRecordWorkInformation(scheduleWorkInformation);

				// 所定時間帯を取得する
				// 所定時間設定
				// this step can not processing, waiting Newwave
			}
		}

		// 自動打刻セットする - set new 自動打刻セット詳細
		// 自動打刻セット詳細をクリア
		AutomaticStampSetDetailOutput automaticStampSetDetailDto = new AutomaticStampSetDetailOutput();
		// ドメインモデル「個人労働条件」を取得する
		if (personalLaborHasData.get().getAutoStampSetAtr() == UseAtr.USE) {
			// 出勤と退勤を反映する設定にする
			automaticStampSetDetailDto.setAttendanceReflectAttr(UseAtr.USE);
			automaticStampSetDetailDto.setAttendanceStamp(StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO);
			automaticStampSetDetailDto.setRetirementAttr(UseAtr.USE);
			automaticStampSetDetailDto.setLeavingStamp(StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO);
		}

		// ドメインモデル「日別実績の勤務情報」を取得する
		if (workInfoOfDailyPerformanceUpdate.getGoStraightAtr() == NotUseAttribute.Use) {
			automaticStampSetDetailDto.setAttendanceReflectAttr(UseAtr.USE);
			automaticStampSetDetailDto.setAttendanceStamp(StampSourceInfo.GO_STRAIGHT);
		}
		if (workInfoOfDailyPerformanceUpdate.getBackStraightAtr() == NotUseAttribute.Use) {
			automaticStampSetDetailDto.setRetirementAttr(UseAtr.USE);
			automaticStampSetDetailDto.setLeavingStamp(StampSourceInfo.GO_STRAIGHT);
		}

		// 自動打刻セット詳細に従って自動打刻セットする
		// 自動打刻セット詳細を確認する - confirm automaticStampSetDetailDto data
		if (automaticStampSetDetailDto.getAttendanceReflectAttr() == UseAtr.USE
				|| automaticStampSetDetailDto.getRetirementAttr() == UseAtr.USE) {
			// セットする打刻詳細を取得する
			// 勤務実績の勤務情報と勤務予定の勤務情報を比較
			// 予定時間帯を自動打刻セット詳細に入れる
			// temp class
			List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
			if (workInfoOfDailyPerformanceUpdate.getRecordWorkInformation() != null) {

				if (workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTimeCode()
						.equals(workInfoOfDailyPerformanceUpdate.getScheduleWorkInformation().getWorkTimeCode())
						&& workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTypeCode().equals(
								workInfoOfDailyPerformanceUpdate.getScheduleWorkInformation().getWorkTypeCode())) {

					// 自動打刻セット詳細．出退勤 ← 勤務予定時間帯
					workInfoOfDailyPerformanceUpdate.getScheduleTimeSheets().forEach(sheet -> {

						TimeLeavingWork timeLeavingWork = new TimeLeavingWork();
						TimeActualStamp attendanceStamp = new TimeActualStamp();
						TimeActualStamp leaveStamp = new TimeActualStamp();

						timeLeavingWork.setWorkNo(sheet.getWorkNo());

						// 出勤系時刻を丸める (làm tròn thời gian 出勤)
						// param : int workTimeMethodSet, String companyId,
						// String siftCode, int superitory
						// param : 0, companyId,
						// workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTimeCode(),
						// superitory
						// TODO - requetsList newwave
						// InstantRounding instantRounding = new
						// InstantRounding();
						// int attendanceTimeAfterRouding =
						// this.roudingTime(sheet.getAttendance().v(),
						// instantRounding.getFontRearSection().value,
						// instantRounding.getRoundingTimeUnit().value);
						// int leaveTimeAfterRounding =
						// this.roudingTime(sheet.getLeaveWork().v(),
						// instantRounding.getFontRearSection().value,
						// instantRounding.getRoundingTimeUnit().value);

						// ドメインモデル「所属職場履歴」を取得する
						attendanceStamp.setStamp(
								new WorkStamp(new TimeWithDayAttr(sheet.getAttendance().v()), sheet.getAttendance(),
										new WorkLocationCD("0001"), automaticStampSetDetailDto.getAttendanceStamp()));
						leaveStamp.setStamp(
								new WorkStamp(new TimeWithDayAttr(sheet.getLeaveWork().v()), sheet.getLeaveWork(),
										new WorkLocationCD("0001"), automaticStampSetDetailDto.getLeavingStamp()));

						timeLeavingWork.setAttendanceStamp(attendanceStamp);
						timeLeavingWork.setLeaveStamp(leaveStamp);
						timeLeavingWorks.add(timeLeavingWork);
					});
				} else {
					// 出勤休日区分を確認する (Xác nhận 出勤休日区分)
					Optional<WorkType> workTypeOptional = this.workTypeRepository.findByPK(companyId,
							workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTypeCode().v());
					WorkStyle workStyle = this.basicScheduleService
							.checkWorkDay(workTypeOptional.get().getWorkTypeCode().v());
					if (!(workStyle == WorkStyle.ONE_DAY_REST)) {
						// 所定時間帯を取得する - TODO
						// waiting newwave
						
						// TODO - Fake Data
						TimeLeavingWork timeLeavingWorkTemp = new TimeLeavingWork();
						TimeActualStamp attendanceStamp = new TimeActualStamp();
						TimeActualStamp leaveStamp = new TimeActualStamp();
						
						attendanceStamp.setStamp(
								new WorkStamp(new TimeWithDayAttr(100), new TimeWithDayAttr(100),
										new WorkLocationCD("0001"), automaticStampSetDetailDto.getAttendanceStamp()));
						leaveStamp.setStamp(
								new WorkStamp(new TimeWithDayAttr(100), new TimeWithDayAttr(100),
										new WorkLocationCD("0001"), automaticStampSetDetailDto.getLeavingStamp()));

						timeLeavingWorkTemp.setAttendanceStamp(attendanceStamp);
						timeLeavingWorkTemp.setLeaveStamp(leaveStamp);
						timeLeavingWorks.add(timeLeavingWorkTemp);
						
//						 PredetemineTimeSet predetemineTimeSet = null;
//						 所定時間設定を自動打刻セット詳細に入れる
//						 所定時間帯．時間帯を順次確認する
//						 predetemineTimeSet.getPrescribedTimezoneSetting().getTimezone().forEach(timezone
//						 -> {
//						 if (timezone.getUseAtr() == UseSetting.USE) {
//						 TimeLeavingWork timeLeavingWorkTemp = new
//						 TimeLeavingWork();
//						 TimeActualStamp attendanceStampTemp = new
//						 TimeActualStamp();
//						 TimeActualStamp leaveStampTemp = new
//						 TimeActualStamp();
						
						 // param : int workTimeMethodSet, String
						 // companyId, String siftCode, int
						 // superitory
						 // param : 0, companyId,
						 //
//						 workInfoOfDailyPerformanceUpdate.getRecordWorkInformation().getWorkTimeCode(),
						 // superitory
						 // TODO - requetsList newwave
//						 InstantRounding instantRounding = new
//						 InstantRounding();
						 // 出勤系時刻を丸める (làm tròn thời gian 出勤)
//						 int attendanceTimeAfterRouding =
//						 this.roudingTime(timezone.getStart().v(),
//						 instantRounding.getFontRearSection().value,
//						 instantRounding.getRoundingTimeUnit().value);
//						 int leaveTimeAfterRounding =
//						 this.roudingTime(timezone.getEnd().v(),
//						 instantRounding.getFontRearSection().value,
//						 instantRounding.getRoundingTimeUnit().value);
						
//						 timeLeavingWorkTemp.setWorkNo(new WorkNo(new
//						 BigDecimal(timezone.getWorkNo())));
//						 attendanceStampTemp.setStamp(
//						 new WorkStamp(new
//						 TimeWithDayAttr(attendanceTimeAfterRouding),
//						 timezone.getStart(),
//						 new
//						 WorkLocationCD(workPlaceHasData.get().getWorkLocationCode()),
//						 automaticStampSetDetailDto.getAttendanceStamp()));
//						 leaveStampTemp.setStamp(new WorkStamp(new
//						 TimeWithDayAttr(leaveTimeAfterRounding),
//						 timezone.getEnd(), new
//						 WorkLocationCD(workPlaceHasData.get().getWorkLocationCode()),
//						 automaticStampSetDetailDto.getLeavingStamp()));
//						
//						 timeLeavingWorks.add(timeLeavingWorkTemp);
//						 }
//						 });
					}
				}
			}
			automaticStampSetDetailDto.setTimeLeavingWorks(timeLeavingWorks);

			Calendar toDay = Calendar.getInstance();
			int hour = toDay.get(Calendar.HOUR_OF_DAY);
			int minute = toDay.get(Calendar.MINUTE);
			int currentMinuteOfDay = ((hour * 60) + minute);

			// 出勤反映 = true
			// 出勤に自動打刻セットする
			if (automaticStampSetDetailDto.getAttendanceReflectAttr() == UseAtr.USE) {
				
				List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();
				
				// ドメインモデル「日別実績の出退勤」を取得する
				// 自動打刻セット詳細．出退勤を順次確認する
				automaticStampSetDetailDto.getTimeLeavingWorks().stream().forEach(timeLeaving -> {
					TimeLeavingWork stamp = null;
					if(timeLeavingOptional.getTimeLeavingWorks() != null){
						stamp = timeLeavingOptional.getTimeLeavingWorks().stream().filter(itemx -> itemx.getWorkNo().v().equals(timeLeaving.getWorkNo().v())).findFirst().get();
					}
					
					if (stampReflectionManagement.get()
							.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.SET_AUTO_STAMP
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& timeLeaving.getAttendanceStamp().getStamp().getTimeWithDay()
											.lessThanOrEqualTo(currentMinuteOfDay))) {
						
						TimeLeavingWork work = new TimeLeavingWork();
						// 勤務NOが同じ実績．出退勤を確認する
						// 存在しない
						if (timeLeavingOptional.getTimeLeavingWorks() == null || (timeLeavingOptional.getTimeLeavingWorks() != null && !timeLeavingOptional.getTimeLeavingWorks().stream()
								.anyMatch(item -> item.getWorkNo().v() == timeLeaving.getWorkNo().v()))) {

							// 実績．出退勤．出勤．打刻←詳細．出退勤．出勤．打刻
							TimeActualStamp timeActualStamp = new TimeActualStamp();
							timeActualStamp.setStamp(timeLeaving.getAttendanceStamp().getStamp());

							work.setWorkNo(timeLeaving.getWorkNo());
							work.setAttendanceStamp(timeActualStamp);

							timeLeavingWorkList.add(work);
							// this.lateCorrection(timeLeavingOptional.get().getTimeLeavingWorks().stream()
							// .filter(item ->
							// item.getWorkNo().equals(timeLeaving.getWorkNo())).findFirst().get()
							// .getAttendanceStamp());
						}
						// 存在する && 入っていない
						if ((timeLeavingOptional.getTimeLeavingWorks() != null && timeLeavingOptional.getTimeLeavingWorks().stream()
								.anyMatch(item -> item.getWorkNo().v() == timeLeaving.getWorkNo().v()))
								&& (stamp != null
								&& (stamp.getLeaveStamp() == null 
								|| (stamp.getLeaveStamp() != null && stamp.getLeaveStamp().getStamp() == null)))) {
							
							TimeActualStamp timeActualStamp = new TimeActualStamp();
							timeActualStamp.setStamp(timeLeaving.getAttendanceStamp().getStamp());
							
							stamp.setAttendanceStamp(timeActualStamp);
							
							// this.lateCorrection(timeLeavingOptional.get().getTimeLeavingWorks().stream()
							// .filter(item ->
							// item.getWorkNo().equals(timeLeaving.getWorkNo())).findFirst().get()
							// .getAttendanceStamp());
						}
					}
					;
				});
				timeLeavingOptional.setTimeLeavingWorks(timeLeavingWorkList);

			}
			
			// 退勤反映 = true
			if (automaticStampSetDetailDto.getRetirementAttr() == UseAtr.USE) {
				
				List<TimeLeavingWork> timeLeavingWorkLst = new ArrayList<>();
				
				automaticStampSetDetailDto.getTimeLeavingWorks().stream().forEach(timeLeavingWork -> {

					TimeLeavingWork stamp = null;
					if (!timeLeavingOptional.getTimeLeavingWorks().isEmpty()) {
						stamp = timeLeavingOptional.getTimeLeavingWorks().stream()
								.filter(itemm -> itemm.getWorkNo().v().equals(timeLeavingWork.getWorkNo().v())).findAny().get();
					}					

					if (stampReflectionManagement.get()
							.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.SET_AUTO_STAMP
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& timeLeavingWork.getLeaveStamp().getStamp().getTimeWithDay()
											.lessThanOrEqualTo(currentMinuteOfDay))) {

						TimeLeavingWork timeLeaving = new TimeLeavingWork();
						// 勤務NOが同じ実績．出退勤を確認する
						// 存在しない
						if (timeLeavingOptional.getTimeLeavingWorks() != null && !timeLeavingOptional.getTimeLeavingWorks().stream()
								.anyMatch(item -> item.getWorkNo().v() == timeLeavingWork.getWorkNo().v())) {

							// 実績．出退勤．出勤．打刻←詳細．出退勤．出勤．打刻
							TimeActualStamp actualStamp = new TimeActualStamp();
							actualStamp.setStamp(timeLeavingWork.getLeaveStamp().getStamp());

							timeLeaving.setWorkNo(timeLeavingWork.getWorkNo());
							timeLeaving.setLeaveStamp(actualStamp);

							timeLeavingWorkLst.add(timeLeaving);

							// this.lateCorrection(timeLeavingOptional.get().getTimeLeavingWorks().stream()
							// .filter(item ->
							// item.getWorkNo().equals(timeLeaving.getWorkNo())).findFirst().get()
							// .getLeaveStamp());
						}
						// 存在する && 入っていない
						if ((timeLeavingOptional.getTimeLeavingWorks() != null 
								&& timeLeavingOptional.getTimeLeavingWorks().stream()
								.anyMatch(item -> item.getWorkNo().v() == timeLeavingWork.getWorkNo().v())) 
								&& (stamp != null 
								&& (stamp.getLeaveStamp() == null 
								|| (stamp.getLeaveStamp() != null && stamp.getLeaveStamp().getStamp() == null)))) {
							
							TimeActualStamp timeActualStamp = new TimeActualStamp();
							timeActualStamp.setStamp(timeLeavingWork.getAttendanceStamp().getStamp());
							
							stamp.setLeaveStamp(timeActualStamp);
							
//							timeLeavingOptional.getTimeLeavingWorks().stream()
//									.filter(item -> item.getWorkNo().equals(timeLeaving.getWorkNo())).findFirst().get()
//									.getLeaveStamp().setStamp(timeLeaving.getLeaveStamp().getStamp());
							// this.lateCorrection(timeLeavingOptional.get().getTimeLeavingWorks().stream()
							// .filter(item ->
							// item.getWorkNo().equals(timeLeaving.getWorkNo())).findFirst().get()
							// .getLeaveStamp());
						}
					}
				});

				timeLeavingOptional.setTimeLeavingWorks(timeLeavingWorkLst);
			}
		}

		this.errMessageInfoRepository.addList(errMesInfos);

		this.reflectStampDomainServiceImpl.reflectStampInfo(companyId, employeeID, day, workInfoOfDailyPerformanceUpdate);

		if (errMesInfos.isEmpty()) {
			// 登録する - register - activity ⑤社員の日別実績を作成する
			// ドメインモデル「日別実績の勤務情報」を更新する - update
			// WorkInfoOfDailyPerformance
			if (this.workInformationRepository.find(employeeID, day).isPresent()) {
				this.workInformationRepository.updateByKey(workInfoOfDailyPerformanceUpdate);
			} else {
				this.workInformationRepository.insert(workInfoOfDailyPerformanceUpdate);
			}
			// ドメインモデル「日別実績の所属情報」を更新する - update
			// AffiliationInforOfDailyPerformance
			if (this.affiliationInforOfDailyPerforRepository.findByKey(employeeID, day).isPresent()) {
				this.affiliationInforOfDailyPerforRepository.updateByKey(affiliationInforOfDailyPerfor);
			} else {
				this.affiliationInforOfDailyPerforRepository.add(affiliationInforOfDailyPerfor);
			}
			// ドメインモデル「日別実績の休憩時間帯」を更新する
			// BreakTimeSheetOfDaily

			// ドメインモデル「日別実績の出退勤」を更新する - update
			// TimeLeavingOfDailyPerformance
//			timeLeavingOptional.getTimeLeavingWorks();
		}

	}

	private int roudingTime(int time, int fontRearSection, int roundingTimeUnit) {

		BigDecimal result = new BigDecimal(time).divide(new BigDecimal(roundingTimeUnit));

		if (!(result.signum() == 0 || result.scale() <= 0 || result.stripTrailingZeros().scale() <= 0)) {
			if (fontRearSection == 0) {
				result = result.setScale(0, RoundingMode.DOWN);
			} else if (fontRearSection == 1) {
				result = result.setScale(0, RoundingMode.UP);
				;
			}
		} else {
			return result.intValue();
		}
		return result.intValue();
	}

	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

	// private void lateCorrection(TimeActualStamp timeActualStamp) {
	// // ドメインモデル「就業時間帯の遅刻・早退設定」を取得する
	// OtherEmTimezoneLateEarlySet earlySet = new OtherEmTimezoneLateEarlySet();
	// if (earlySet.isStampExactlyTimeIsLateEarly() &&
	// !timeActualStamp.getStamp().equals(null)) {
	// timeActualStamp.setStamp(new WorkStamp(
	// new TimeWithDayAttr(timeActualStamp.getStamp().getAfterRoundingTime().v()
	// - 1),
	// new
	// TimeWithDayAttr(timeActualStamp.getStamp().getTimeWithDay().valueAsMinutes()
	// - 1),
	// timeActualStamp.getStamp().getLocationCode(),
	// timeActualStamp.getStamp().getStampSourceInfo()));
	// }
	// }

}
