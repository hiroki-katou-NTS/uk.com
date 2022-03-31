package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.statusofemployee.RecStatusOfEmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.statusofemployee.RecStatusOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkPlaceSidImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.AutomaticStampSetDetailOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimeActualStampOutPut;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimeLeavingWorkOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.WorkStampOutPut;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleAdapter;
import nts.uk.ctx.at.record.dom.jobtitle.affiliate.AffJobTitleSidImport;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.specificdatesetting.RecSpecificDateSettingImport;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.calculationsetting.AutoStampForFutureDayClass;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ReflectWorkInforDomainService;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.ClosureOfDailyPerOutPut;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.MasterList;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHis;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.BaseAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPUnitUseSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BPUnitUseSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ReflectWorkInforDomainServiceImpl implements ReflectWorkInforDomainService {

	@Inject
	private RecordDomRequireService requireService;

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

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
	private BasicScheduleAdapter basicScheduleAdapter;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private StampReflectionManagementRepository stampReflectionManagementRepository;

	@Inject
	private BusinessTypeEmpOfHistoryRepository businessTypeEmpOfHistoryRepository;

	@Inject
	private BusinessTypeOfEmployeeRepository businessTypeOfEmployeeRepository;


	@Inject
	private BPSettingRepository bPSettingRepository;

	@Inject
	private WTBonusPaySettingRepository wTBonusPaySettingRepository;

	@Inject
	private RecStatusOfEmployeeAdapter recStatusOfEmployeeAdapter;

	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;

	@Inject
	private ShortTimeOfDailyPerformanceRepository shortTimeOfDailyPerformanceRepository;

	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;

	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyPerformanceRepository;

	@Inject
	private BPUnitUseSettingRepository bPUnitUseSettingRepository;


	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;


	@Resource
	private SessionContext scContext;

	private ReflectWorkInforDomainService self;


	@PostConstruct
	public void init() {
		// Get self.
		this.self = scContext.getBusinessObject(ReflectWorkInforDomainService.class);
	}

	/**
	 * 所属情報を反映する
	 *
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param empCalAndSumExecLogID
	 * @param employeeGeneralInfoImport
	 * @return
	 */
	@Override
	public AffiliationInforState createAffiliationInforState(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID, EmployeeGeneralInfoImport employeeGeneralInfoImport) {

		// employment
		Map<String, List<ExEmploymentHistItemImport>> mapEmploymentHist = employeeGeneralInfoImport
				.getEmploymentHistoryImports().stream().collect(Collectors.toMap(
						ExEmploymentHistoryImport::getEmployeeId, ExEmploymentHistoryImport::getEmploymentItems));

		Optional<ExEmploymentHistItemImport> employmentHistItemImport = Optional.empty();
		List<ExEmploymentHistItemImport> exEmploymentHistItemImports = mapEmploymentHist.get(employeeId);
		if (exEmploymentHistItemImports != null) {
			employmentHistItemImport = exEmploymentHistItemImports.stream()
					.filter(empHistItem -> empHistItem.getPeriod().contains(day)).findFirst();
		}

		// classification
		Map<String, List<ExClassificationHistItemImport>> mapClassificationHist = employeeGeneralInfoImport
				.getExClassificationHistoryImports().stream()
				.collect(Collectors.toMap(ExClassificationHistoryImport::getEmployeeId,
						ExClassificationHistoryImport::getClassificationItems));

		Optional<ExClassificationHistItemImport> classificationHistItemImport = Optional.empty();
		List<ExClassificationHistItemImport> exClassificationHistItemImports = mapClassificationHist.get(employeeId);
		if (exClassificationHistItemImports != null) {
			classificationHistItemImport = exClassificationHistItemImports.stream()
					.filter(item -> item.getPeriod().contains(day)).findFirst();
		}

		// Job title
		Map<String, List<ExJobTitleHistItemImport>> mapJobTitleHist = employeeGeneralInfoImport
				.getExJobTitleHistoryImports().stream().collect(Collectors.toMap(ExJobTitleHistoryImport::getEmployeeId,
						ExJobTitleHistoryImport::getJobTitleItems));

		Optional<ExJobTitleHistItemImport> jobTitleHistItemImport = Optional.empty();
		List<ExJobTitleHistItemImport> exJobTitleHistItemImports = mapJobTitleHist.get(employeeId);
		if (exJobTitleHistItemImports != null) {
			jobTitleHistItemImport = exJobTitleHistItemImports.stream().filter(item -> item.getPeriod().contains(day))
					.findFirst();
		}

		// workPlace
		Map<String, List<ExWorkplaceHistItemImport>> mapWorkplaceHist = employeeGeneralInfoImport
				.getExWorkPlaceHistoryImports().stream().collect(Collectors
						.toMap(ExWorkPlaceHistoryImport::getEmployeeId, ExWorkPlaceHistoryImport::getWorkplaceItems));

		Optional<ExWorkplaceHistItemImport> workplaceHistItemImport = Optional.empty();
		List<ExWorkplaceHistItemImport> exWorkplaceHistItemImports = mapWorkplaceHist.get(employeeId);
		if (exWorkplaceHistItemImports != null) {
			workplaceHistItemImport = exWorkplaceHistItemImports.stream().filter(item -> item.getPeriod().contains(day))
					.findFirst();
		}

		// Get Data
		List<ErrMessageInfo> errMesInfos = new ArrayList<>();
		// 存在しない - no data
		if (!employmentHistItemImport.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("001"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_426")));
			errMesInfos.add(employmentErrMes);
		}
		if (!workplaceHistItemImport.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("002"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_427")));
			errMesInfos.add(employmentErrMes);
		}
		if (!classificationHistItemImport.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("003"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_428")));
			errMesInfos.add(employmentErrMes);
		}
		if (!jobTitleHistItemImport.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("004"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_429")));
			errMesInfos.add(employmentErrMes);
		}
		// 存在する - has data
		if (employmentHistItemImport.isPresent() && workplaceHistItemImport.isPresent()
				&& classificationHistItemImport.isPresent() && jobTitleHistItemImport.isPresent()) {
			return new AffiliationInforState(Collections.emptyList(),
					Optional.of(new AffiliationInforOfDailyAttd(
							new EmploymentCode(employmentHistItemImport.get().getEmploymentCode()),
							jobTitleHistItemImport.get().getJobTitleId(),
							workplaceHistItemImport.get().getWorkplaceId(),
							new ClassificationCode(classificationHistItemImport.get().getClassificationCode()),
							Optional.empty(), // TODO team daily add
							Optional.empty(), // TODO team daily add
							Optional.empty(), // TODO team daily add
							Optional.empty(),  // TODO team daily add
							Optional.empty()))); // TODO team daily add
		} else {
			// #日別作成修正 2018/07/17 前川 隼大
			// 社員の日別実績のエラーを作成する
			EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, day,
					new ErrorAlarmWorkRecordCode("S025"), new ArrayList<>());
			this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);

			return new AffiliationInforState(errMesInfos, Optional.empty());
		}
	}

	/**
	 * 勤務種別変更時に再作成する
	 * @param employeeId
	 * @param day
	 * @param empCalAndSumExecLogID
	 * @param reCreateWorkType 勤務種別変更時に再作成
	 * @param reCreateWorkPlace 異動時に再作成
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
	public ExitStatus reCreateWorkType(String employeeId, GeneralDate day, String empCalAndSumExecLogID,
			boolean reCreateWorkType, boolean reCreateWorkPlace) {
		ExitStatus exitStatus = ExitStatus.DO_NOT_RECREATE;

		/**
		 * KIF 001 reCreateWorkType == false
		 */
		if (reCreateWorkType == true) {
			// 社員の勤務種別を取得する -- start
			// 対応するドメインモデル「社員の勤務種別の履歴」を取得する
			Optional<BusinessTypeOfEmployeeHistory> businessTypeOfEmployeeHistory = this.businessTypeEmpOfHistoryRepository
					.findByBaseDate(day, employeeId);

			if (businessTypeOfEmployeeHistory.isPresent()) {
				String historyId = businessTypeOfEmployeeHistory.get().getHistory().get(0).identifier();
				// 社員の勤務種別を取得する -- end
				// 対応するドメインモデル「社員の勤務種別」を取得する

				Optional<AffiliationInforOfDailyPerfor> affiliationInfo = this.affiliationInforOfDailyPerforRepository
						.findByKey(employeeId, day);
				Optional<BusinessTypeOfEmployee> businessTypeOfEmployee = this.businessTypeOfEmployeeRepository
						.findByHistoryId(historyId);

				if (!businessTypeOfEmployee.isPresent()) {
					List<ErrMessageInfo> errMesInfos = new ArrayList<>();
					ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
							new ErrMessageResource("013"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
							new ErrMessageContent(TextResource.localize("Msg_1010")));
					errMesInfos.add(employmentErrMes);
					this.errMessageInfoRepository.addList(errMesInfos);
					return exitStatus;
				} else {
					// ドメインモデル「日別実績の勤務種別」を取得する
//					Optional<WorkTypeOfDailyPerformance> workTypeOfDailyPerformance = this.workTypeOfDailyPerforRepository
//							.findByKey(employeeId, day);
//					if (!workTypeOfDailyPerformance.isPresent()) {
//						return exitStatus;
//					} else {
						if (businessTypeOfEmployee.get().getBusinessTypeCode()
								.equals(affiliationInfo.get().getAffiliationInfor().getBusinessTypeCode().orElse(null))) {
							// パラメータ「異動時に再作成」を確認する
							// check reCreateWorkPlace
							if (reCreateWorkPlace == true) {
								// ReqL30 :
								Optional<AffWorkPlaceSidImport> workPlaceHasData = this.affWorkplaceAdapter
										.findBySidAndDate(employeeId, day);

								if (workPlaceHasData.isPresent() && affiliationInfo.isPresent()) {
									if (!workPlaceHasData.get().getWorkplaceId()
											.equals(affiliationInfo.get().getAffiliationInfor().getWplID())) {
										exitStatus = ExitStatus.RECREATE;
										return exitStatus;
									}
								}
							}

							return exitStatus;
						} else {
							exitStatus = ExitStatus.RECREATE;
							return exitStatus;
						}
//					}
				}
			} else {
				List<ErrMessageInfo> errMesInfos = new ArrayList<>();
				ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
						new ErrMessageResource("013"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
						new ErrMessageContent(TextResource.localize("Msg_1010")));
				errMesInfos.add(employmentErrMes);
				this.errMessageInfoRepository.addList(errMesInfos);
				return exitStatus;
			}
		} else {
			// パラメータ「異動時に再作成」を確認する
			// check reCreateWorkPlace
			if (reCreateWorkPlace == true) {
				// ReqL30 :
				Optional<AffWorkPlaceSidImport> workPlaceHasData = this.affWorkplaceAdapter.findBySidAndDate(employeeId,
						day);

				Optional<AffiliationInforOfDailyPerfor> affiliationInfo = this.affiliationInforOfDailyPerforRepository
						.findByKey(employeeId, day);

				if (workPlaceHasData.isPresent() && affiliationInfo.isPresent()) {
					if (!workPlaceHasData.get().getWorkplaceId().equals(affiliationInfo.get().getAffiliationInfor().getWplID())) {
						exitStatus = ExitStatus.RECREATE;
						return exitStatus;
					}
				}
			}
		}

		return exitStatus;
	}

	/**
	 * Importクラスを取得し日別実績の所属情報を作成する
	 *
	 * @param companyId
	 *            the companyId
	 * @param employeeId
	 *            the employeeId
	 * @param day
	 *            the day
	 * @param empCalAndSumExecLogID
	 * @return AffiliationInforState(nts.uk.ctx.at.record.dom.dailyperformanceprocessing)
	 */
	@Override
	public AffiliationInforState createAffiliationInforOfDailyPerfor(String companyId, String employeeId,
			GeneralDate day, String empCalAndSumExecLogID) {

		// Imported(就業．勤務実績)「所属雇用履歴」を取得する
		Optional<SyEmploymentImport> employmentHasData = this.syEmploymentAdapter.findByEmployeeId(companyId,
				employeeId, day);

		// Imported(就業．勤務実績)「所属職場履歴」を取得する
		Optional<AffWorkPlaceSidImport> workPlaceHasData = this.affWorkplaceAdapter.findBySidAndDate(employeeId, day);

		// Imported(就業．勤務実績)「所属分類履歴」を取得する
		Optional<AffClassificationSidImport> classificationHasData = this.affClassificationAdapter
				.findByEmployeeId(companyId, employeeId, day);

		// Imported(就業．勤務実績)「所属職位履歴」を取得する
		Optional<AffJobTitleSidImport> jobTitleHasData = this.affJobTitleAdapter.findByEmployeeId(employeeId, day);
		// Get Data
		List<ErrMessageInfo> errMesInfos = new ArrayList<>();
		// 存在しない - no data
		if (!employmentHasData.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("001"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_426")));
			errMesInfos.add(employmentErrMes);
		}
		if (!workPlaceHasData.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("002"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_427")));
			errMesInfos.add(employmentErrMes);
		}
		if (!classificationHasData.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("003"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_428")));
			errMesInfos.add(employmentErrMes);
		}
		if (!jobTitleHasData.isPresent()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("004"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
					new ErrMessageContent(TextResource.localize("Msg_429")));
			errMesInfos.add(employmentErrMes);
		}
		// 存在する - has data
		if (employmentHasData.isPresent() && workPlaceHasData.isPresent() && classificationHasData.isPresent()
				&& jobTitleHasData.isPresent()) {
			return new AffiliationInforState(Collections.emptyList(),
					Optional.of(new AffiliationInforOfDailyAttd(
							new EmploymentCode(employmentHasData.get().getEmploymentCode()),
							jobTitleHasData.get().getJobTitleId(), workPlaceHasData.get().getWorkplaceId(),
							new ClassificationCode(classificationHasData.get().getClassificationCode()),
							Optional.empty(), // TODO team daily add
							Optional.empty(), // TODO team daily add
							Optional.empty(),  // TODO team daily add
							Optional.empty(),  // TODO team daily add
							Optional.empty()))); // TODO team daily add
		} else {
			// #日別作成修正 2018/07/17 前川 隼大
			// 社員の日別実績のエラーを作成する
			EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, day,
					new ErrorAlarmWorkRecordCode("S025"), new ArrayList<>());
			this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);

			return new AffiliationInforState(errMesInfos, Optional.empty());
		}
	}

	/**
	 * 特定日を日別実績に反映する
	 *
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param workPlaceID
	 * @return SpecificDateAttrOfDailyPerfor
	 */
	@Override
	public SpecificDateAttrOfDailyAttd reflectSpecificDate(String companyId, String employeeId, GeneralDate day,
			String workPlaceID, PeriodInMasterList periodInMasterList) {
		List<MasterList> masterLists = new ArrayList<>();
		Optional<MasterList> newMasterLists = Optional.empty();
		if (periodInMasterList != null) {
			masterLists = periodInMasterList.getMasterLists();
			newMasterLists = masterLists.stream().filter(item -> item.getDatePeriod().contains(day)).findFirst();
		}

		Optional<RecSpecificDateSettingImport> specificDateSettingImport = Optional.empty();
		if (newMasterLists.isPresent()){
			specificDateSettingImport = newMasterLists.get().getSpecificDateSettingImport().stream().filter(c -> c.getDate().equals(day)).findFirst();
		}
		RecSpecificDateSettingImport data = new RecSpecificDateSettingImport();
		if (specificDateSettingImport.isPresent()) {
			data = specificDateSettingImport.get();
		} else {
//			data = this.recSpecificDateSettingAdapter.specificDateSettingService(companyId,
//					workPlaceID, day);
			data = new RecSpecificDateSettingImport(day, new ArrayList<>());
		}

		List<SpecificDateAttrSheet> specificDateAttrSheets = new ArrayList<>();
		for (int i = 1; i < 11; i++) {
			if (data.getNumberList().contains(i)) {
				SpecificDateAttrSheet specificDateAttrSheet = new SpecificDateAttrSheet(new SpecificDateItemNo(i),
						SpecificDateAttr.USE);
				specificDateAttrSheets.add(specificDateAttrSheet);
			} else {
				SpecificDateAttrSheet specificDateAttrSheet = new SpecificDateAttrSheet(new SpecificDateItemNo(i),
						SpecificDateAttr.NOT_USE);
				specificDateAttrSheets.add(specificDateAttrSheet);
			}
		}
		;
		SpecificDateAttrOfDailyPerfor specificDateAttrOfDailyPerfor = new SpecificDateAttrOfDailyPerfor(employeeId,
				specificDateAttrSheets, day);
		return specificDateAttrOfDailyPerfor.getSpecificDay();
	}

	/**
	 * 休業を日別実績に反映する
	 */
	@Override
	public ClosureOfDailyPerOutPut reflectHolidayOfDailyPerfor(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID, WorkInfoOfDailyAttendance workInfoOfDailyPerformance) {

		ClosureOfDailyPerOutPut closureOfDailyPerOutPut = new ClosureOfDailyPerOutPut();

		List<ErrMessageInfo> errMesInfos = new ArrayList<>();

		RecStatusOfEmployeeImport recStatusOfEmployeeImport = this.recStatusOfEmployeeAdapter
				.getStatusOfEmployeeService(employeeId, day);
		if (recStatusOfEmployeeImport != null) {
			// 休職・休業のチェック(check trang thai 休職 = 2 or 休業 = 3)
			if (recStatusOfEmployeeImport.getStatusOfEmployment() == 2
					|| recStatusOfEmployeeImport.getStatusOfEmployment() == 3) {
				List<WorkType> workTypeList = this.workTypeRepository.findByCompanyId(companyId);
				// 廃止区分＝廃止しない
				List<WorkType> workTypeOneDayList = workTypeList.stream()
						.filter(x -> x.isOneDay() && x.getDeprecate() == DeprecateClassification.NotDeprecated)
						.collect(Collectors.toList());
				WorkType workTypeNeed = null;
				// ドメインモデル「勤務予定基本情報」を取得
				Optional<BasicScheduleSidDto> optBasicSchedule = this.basicScheduleAdapter.findAllBasicSchedule(employeeId, day);
				if (optBasicSchedule.isPresent()) {
					// 社員の労働条件を取得する
					Optional<WorkingConditionItem> optWorkingConditionItem = WorkingConditionService
							.findWorkConditionByEmployee(requireService.createRequire(), employeeId, day);
					// 休業休職の勤務種類コードを返す
					String workTypeCode = this.basicScheduleService.getWorktypeCodeLeaveHolidayType(companyId,
							employeeId, day, optBasicSchedule.get().getWorkTypeCode(),
							recStatusOfEmployeeImport.getLeaveHolidayType(), optWorkingConditionItem);
					Optional<WorkType> optWorkType = workTypeOneDayList.stream().filter(x -> x.getWorkTypeCode().v().equals(workTypeCode)).findFirst();
					if(optWorkType.isPresent()){
						// 日別実績の勤務種類を更新(Update Worktype của 日別実績)
						workTypeNeed = optWorkType.get();
					}
				} else {
					// 勤務種類を取得(lấy thông tin Worktype)
					for (WorkType workType : workTypeOneDayList) {
						Optional<WorkTypeSet> workTypeSet = workType.getWorkTypeSetByAtr(WorkAtr.OneDay);
						if (recStatusOfEmployeeImport.getStatusOfEmployment() == 2
								&& WorkTypeClassification.LeaveOfAbsence == workType.getDailyWork().getOneDay()) {
							// 日別実績の勤務種類を更新(Update Worktype của 日別実績)
							workTypeNeed = workType;
							break;
						} else if (recStatusOfEmployeeImport.getStatusOfEmployment() == 3
								&& WorkTypeClassification.Closure == workType.getDailyWork().getOneDay()) {
							Integer closeAtr = null;
							switch (recStatusOfEmployeeImport.getLeaveHolidayType()) {
							case 2:
								closeAtr = 0;
								break;
							case 3:
								closeAtr = 1;
								break;
							case 4:
								closeAtr = 2;
								break;
							case 5:
								closeAtr = 3;
								break;
							case 6:
								closeAtr = 4;
								break;
							case 7:
								closeAtr = 5;
								break;
							case 8:
								closeAtr = 6;
								break;
							case 9:
								closeAtr = 7;
								break;
							case 10:
								closeAtr = 8;
								break;
							}

							if (workTypeSet.isPresent() && closeAtr == workTypeSet.get().getCloseAtr().value) {
								// 日別実績の勤務種類を更新(Update Worktype của 日別実績)
								workTypeNeed = workType;
								break;
							}
						}
					}
				}

				if (workTypeNeed == null) {
					// エラーを出力(xuất error)
					if (recStatusOfEmployeeImport.getStatusOfEmployment() == 2) {
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("014"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent(TextResource.localize("Msg_1150", "休職")));
						errMesInfos.add(employmentErrMes);
					} else if (recStatusOfEmployeeImport.getStatusOfEmployment() == 3) {
						String errorParam = "";
						switch (recStatusOfEmployeeImport.getLeaveHolidayType()) {
						case 2:
							errorParam = "産前休業";
							break;
						case 3:
							errorParam = "産後休業";
							break;
						case 4:
							errorParam = "育児介護";
							break;
						case 5:
							errorParam = "介護休業";
							break;
						case 6:
							errorParam = "傷病休業";
							break;
						case 7:
							errorParam = "任意休業1";
							break;
						case 8:
							errorParam = "任意休業2";
							break;
						case 9:
							errorParam = "任意休業3";
							break;
						case 10:
							errorParam = "任意休業4";
							break;
						}
						ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
								new ErrMessageResource("014"), EnumAdaptor.valueOf(0, ExecutionContent.class), day,
								new ErrMessageContent(TextResource.localize("Msg_1150", errorParam)));
						errMesInfos.add(employmentErrMes);
					}
				} else {
					WorkInformation recordInfo = workInfoOfDailyPerformance.getRecordInfo().clone();
					recordInfo.setWorkTypeCode(workTypeNeed.getWorkTypeCode());

					workInfoOfDailyPerformance.setRecordInfo(recordInfo);
					WorkTypeClassification oneDay = workTypeNeed.getDailyWork().getOneDay();

					if (oneDay == WorkTypeClassification.Holiday || oneDay == WorkTypeClassification.Pause
							|| oneDay == WorkTypeClassification.ContinuousWork
							|| oneDay == WorkTypeClassification.LeaveOfAbsence
							|| oneDay == WorkTypeClassification.Closure) {

						WorkInformation recordWorkInformation = workInfoOfDailyPerformance.getRecordInfo().clone();
						recordWorkInformation.removeWorkTimeInHolydayWorkType();
						workInfoOfDailyPerformance.setRecordInfo(recordWorkInformation);
					}
					// to show clear attendance item
					List<Integer> timeleavingAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.ATTENDACE_LEAVE);
					List<Integer> temporaryTimeAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.TEMPORARY_TIME);
					List<Integer> breakTimeAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.BREAK_TIME);
					List<Integer> shortTimeAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.SHORT_TIME);
					List<Integer> attTimeAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.ATTENDANCE_TIME);
					List<Integer> anyItemAttItemIds = AttendanceItemIdContainer
							.getItemIdByDailyDomains(DailyDomainGroup.OPTIONAL_ITEM);
					List<Integer> attItemList = new ArrayList<>();
					attItemList.addAll(timeleavingAttItemIds);
					attItemList.addAll(temporaryTimeAttItemIds);
					attItemList.addAll(breakTimeAttItemIds);
					attItemList.addAll(shortTimeAttItemIds);
					attItemList.addAll(attTimeAttItemIds);
					attItemList.addAll(anyItemAttItemIds);

					// 休職・休業で不要なドメインモデルを削除する
					// 日別実績のドメインモデルを削除する
					this.timeLeavingOfDailyPerformanceRepository.delete(employeeId, day);
					// 日別実績の臨時出退勤
					this.temporaryTimeOfDailyPerformanceRepository.delete(employeeId, day);
					// 日別実績の休憩時間帯
					this.breakTimeOfDailyPerformanceRepository.delete(employeeId, day);
					// 日別実績の短時間勤務時間帯
					this.shortTimeOfDailyPerformanceRepository.deleteByEmployeeIdAndDate(employeeId, day);
					// 日別実績の勤怠時間
					this.attendanceTimeRepository.deleteByEmployeeIdAndDate(employeeId, day);
					// 日別実績の作業別勤怠時間 - TODO : has not entity
					// 日別実績の任意項目
					this.anyItemValueOfDailyRepo.removeByEmployeeIdAndDate(employeeId, day);

					// ドメインモデル「日別実績の編集状態」を削除する
					this.editStateOfDailyPerformanceRepository.deleteByListItemId(employeeId, day, attItemList);
				}
			}
		}

		closureOfDailyPerOutPut.setErrMesInfos(errMesInfos);
		closureOfDailyPerOutPut.setWorkInfoOfDailyPerformance(workInfoOfDailyPerformance);
		return closureOfDailyPerOutPut;
	}

	/**
	 * 計算区分を日別実績に反映する
	 */
	@Override
	public CalAttrOfDailyAttd reflectCalAttOfDaiPer(String companyId, String employeeId, GeneralDate day,
			AffiliationInforOfDailyAttd affiliationInforOfDailyPerfor, PeriodInMasterList periodInMasterList) {

		CalAttrOfDailyPerformance calAttrOfDailyPerformance = new CalAttrOfDailyPerformance();

//		if (periodInMasterList == null) {
//			// reqList496
//			// 職場IDと基準日から上位職場を取得する
////			List<String> workPlaceIdList = this.affWorkplaceAdapter.findParentWpkIdsByWkpId(companyId,
////					affiliationInforOfDailyPerfor.getWplID(), day);
//
//			// [No.569]職場の上位職場を取得する
//			List<String> workPlaceIdList = this.affWorkplaceAdapter.getUpperWorkplace(companyId, affiliationInforOfDailyPerfor.getWplID(), day);
//
//			// 自動計算設定の取得(get auto calculation setting)
//			BaseAutoCalSetting baseAutoCalSetting = this.autoCalculationSetService.getAutoCalculationSetting(companyId,
//					employeeId, day, affiliationInforOfDailyPerfor.getWplID(),
//					affiliationInforOfDailyPerfor.getJobTitleID(), Optional.of(workPlaceIdList));
//
//			calAttrOfDailyPerformance = this.autoCalSetting(baseAutoCalSetting, employeeId, day);
//		} else {
			List<MasterList> masterLists = periodInMasterList.getMasterLists();
			Optional<MasterList> newMasterLists = masterLists.stream()
					.filter(item -> item.getDatePeriod().contains(day)).findFirst();

			BaseAutoCalSetting baseAutoCalSettingNew = newMasterLists.get().getBaseAutoCalSetting();

			calAttrOfDailyPerformance = this.autoCalSetting(baseAutoCalSettingNew, employeeId, day);
//		}
		return calAttrOfDailyPerformance.getCalcategory();
	}

	private CalAttrOfDailyPerformance autoCalSetting(BaseAutoCalSetting baseAutoCalSetting, String employeeId,
			GeneralDate day) {

		AutoCalSetting flexExcessTime = new AutoCalSetting(
				baseAutoCalSetting.getFlexOTTime().getFlexOtTime().getUpLimitORtSet(),
				baseAutoCalSetting.getFlexOTTime().getFlexOtTime().getCalAtr());
		AutoCalFlexOvertimeSetting autoCalFlexOvertimeSetting = new AutoCalFlexOvertimeSetting(flexExcessTime);

		// 加給: 加給の自動計算設定
		AutoCalRaisingSalarySetting autoCalRaisingSalarySetting = new AutoCalRaisingSalarySetting(
				baseAutoCalSetting.getRaisingSalary().isSpecificRaisingSalaryCalcAtr(),
				baseAutoCalSetting.getRaisingSalary().isRaisingSalaryCalcAtr());

		// 休出時間: 休出時間の自動計算設定
		AutoCalRestTimeSetting holidayTimeSetting = new AutoCalRestTimeSetting(
				new AutoCalSetting(baseAutoCalSetting.getRestTime().getRestTime().getUpLimitORtSet(),
						baseAutoCalSetting.getRestTime().getRestTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getRestTime().getLateNightTime().getUpLimitORtSet(),
						baseAutoCalSetting.getRestTime().getLateNightTime().getCalAtr()));

		// 残業時間: 残業時間の自動計算設定
		AutoCalOvertimeSetting overtimeSetting = new AutoCalOvertimeSetting(
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getEarlyOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getEarlyOtTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getEarlyMidOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getEarlyMidOtTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getNormalOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getNormalOtTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getNormalMidOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getNormalMidOtTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getLegalOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getLegalOtTime().getCalAtr()),
				new AutoCalSetting(baseAutoCalSetting.getNormalOTTime().getLegalMidOtTime().getUpLimitORtSet(),
						baseAutoCalSetting.getNormalOTTime().getLegalMidOtTime().getCalAtr()));

		// //遅刻早退: 遅刻早退の自動計算設定
		AutoCalcOfLeaveEarlySetting autoCalOfLeaveEarlySetting = new AutoCalcOfLeaveEarlySetting(
				baseAutoCalSetting.getLeaveEarly().isLate(), baseAutoCalSetting.getLeaveEarly().isLeaveEarly());
		// 乖離時間: 乖離時間の自動計算設定
		AutoCalcSetOfDivergenceTime autoCalcSetOfDivergenceTime = new AutoCalcSetOfDivergenceTime(EnumAdaptor
				.valueOf(baseAutoCalSetting.getDivergenceTime().getDivergenceTime().value, DivergenceTimeAttr.class));

		CalAttrOfDailyPerformance calAttrOfDailyPerformance = new CalAttrOfDailyPerformance(employeeId, day,
				autoCalFlexOvertimeSetting, autoCalRaisingSalarySetting, holidayTimeSetting, overtimeSetting,
				autoCalOfLeaveEarlySetting, autoCalcSetOfDivergenceTime);

		return calAttrOfDailyPerformance;
	}

//	private WorkInformation getWorkInfo(Optional<SingleDaySchedule> workingCondition, SingleDaySchedule category) {
//		WorkInformation recordWorkInformation;
//		if (workingCondition.isPresent()) {
//			recordWorkInformation = new WorkInformation(workingCondition.get().getWorkTypeCode().orElse(null),
//					workingCondition.get().getWorkTimeCode().orElse(null));
//		} else {
//			recordWorkInformation = new WorkInformation(category.getWorkTypeCode().orElse(null),
//					category.getWorkTimeCode().orElse(null));
//		}
//		return recordWorkInformation;
//	}

	@Override
	public TimeLeavingOfDailyAttd createStamp(String companyId,
			WorkInfoOfDailyAttendance workInfoOfDailyPerformanceUpdate,
			Optional<WorkingConditionItem> workingConditionItem, TimeLeavingOfDailyAttd timeLeavingOptional,
			String employeeID, GeneralDate day, Optional<StampReflectionManagement> stampReflectionManagement) {
		val require = requireService.createRequire();

		if (timeLeavingOptional == null) {
			// 日別実績の出退勤
			timeLeavingOptional = new TimeLeavingOfDailyAttd();
		}

		// ドメインモデル「打刻反映管理」を取得する
		if (stampReflectionManagement == null || !stampReflectionManagement.isPresent()) {
			stampReflectionManagement = this.stampReflectionManagementRepository.findByCid(companyId);
		}

		// 自動打刻セットする - set new 自動打刻セット詳細
		// 自動打刻セット詳細をクリア
		AutomaticStampSetDetailOutput automaticStampSetDetailDto = new AutomaticStampSetDetailOutput();
		// パラメータ「労働条件項目」を取得する
		if (workingConditionItem.get().getAutoStampSetAtr() == NotUseAtr.USE) {
			// 出勤と退勤を反映する設定にする
			automaticStampSetDetailDto.setAttendanceReflectAttr(UseAtr.USE);
			automaticStampSetDetailDto.setAttendanceStamp(TimeChangeMeans.AUTOMATIC_SET);
			automaticStampSetDetailDto.setRetirementAttr(UseAtr.USE);
			automaticStampSetDetailDto.setLeavingStamp(TimeChangeMeans.AUTOMATIC_SET);
		}

		// パラメータ「日別実績の勤務情報」を取得する
		// 直行区分
		if (workInfoOfDailyPerformanceUpdate.getGoStraightAtr() == NotUseAttribute.Use) {
			automaticStampSetDetailDto.setAttendanceReflectAttr(UseAtr.USE);
			automaticStampSetDetailDto.setAttendanceStamp(TimeChangeMeans.DIRECT_BOUNCE);
		}
		// 直帰区分
		if (workInfoOfDailyPerformanceUpdate.getBackStraightAtr() == NotUseAttribute.Use) {
			automaticStampSetDetailDto.setRetirementAttr(UseAtr.USE);
			automaticStampSetDetailDto.setLeavingStamp(TimeChangeMeans.DIRECT_BOUNCE);
		}

		// 自動打刻セット詳細に従って自動打刻セットする
		// 自動打刻セット詳細を確認する - confirm automaticStampSetDetailDto data
		if (automaticStampSetDetailDto.getAttendanceReflectAttr() == UseAtr.USE
				|| automaticStampSetDetailDto.getRetirementAttr() == UseAtr.USE) {
			// セットする打刻詳細を取得する
			// 勤務実績の勤務情報と勤務予定の勤務情報を比較
			// 予定時間帯を自動打刻セット詳細に入れる
			// temp class
			List<TimeLeavingWorkOutput> timeLeavingWorkTemps = new ArrayList<>();
			List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
			if (workInfoOfDailyPerformanceUpdate.getRecordInfo() != null) {
//				if(workInfoOfDailyPerformanceUpdate.getScheduleInfo() != null) {
//				if (workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode() != null  && workInfoOfDailyPerformanceUpdate.getScheduleInfo().getWorkTypeCode() != null && workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode()
//						.equals(workInfoOfDailyPerformanceUpdate.getScheduleInfo().getWorkTimeCode())
//						&& workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTypeCode()
//								.equals(workInfoOfDailyPerformanceUpdate.getScheduleInfo().getWorkTypeCode())) {
//
//					// 自動打刻セット詳細．出退勤 ← 勤務予定時間帯
//					workInfoOfDailyPerformanceUpdate.getScheduleTimeSheets().forEach(sheet -> {
//
//						TimeLeavingWorkOutput timeLeavingWorkOutput = new TimeLeavingWorkOutput();
//						TimeActualStampOutPut attendanceStampTemp = new TimeActualStampOutPut();
//						TimeActualStampOutPut leaveStampTemp = new TimeActualStampOutPut();
//
//						timeLeavingWorkOutput.setWorkNo(sheet.getWorkNo());
//
//						// 出勤系時刻を丸める (làm tròn thời gian 出勤)
//						Optional<WorkTimezoneCommonSet> workTimezoneCommonSet = GetCommonSet.workTimezoneCommonSet(require, companyId,
//								workInfoOfDailyPerformanceUpdate.getScheduleInfo().getWorkTimeCode().v());
//						WorkTimezoneStampSet stampSet = workTimezoneCommonSet.get().getStampSet();
//
//						// 出勤
//						RoundingSet atendanceRoundingSet = stampSet.getRoundingSets().stream()
//								.filter(item -> item.getSection() == Superiority.ATTENDANCE).findFirst().isPresent()
//										? stampSet.getRoundingSets().stream()
//												.filter(item -> item.getSection() == Superiority.ATTENDANCE).findFirst()
//												.get()
//										: null;
//
//						int attendanceTimeAfterRouding = atendanceRoundingSet != null ? this.roudingTime(
//								sheet.getAttendance().v(),
//								atendanceRoundingSet.getRoundingSet().getFontRearSection().value,
//								new Integer(atendanceRoundingSet.getRoundingSet().getRoundingTimeUnit().description)
//										.intValue())
//								: sheet.getAttendance().v();
//						// 退勤
//						RoundingSet leavingRoundingSet = stampSet.getRoundingSets().stream()
//								.filter(item -> item.getSection() == Superiority.OFFICE_WORK).findFirst().isPresent()
//										? stampSet.getRoundingSets().stream()
//												.filter(item -> item.getSection() == Superiority.OFFICE_WORK)
//												.findFirst().get()
//										: null;
//
//						int leaveTimeAfterRounding = leavingRoundingSet != null ? this.roudingTime(
//								sheet.getLeaveWork().v(),
//								leavingRoundingSet.getRoundingSet().getFontRearSection().value,
//								new Integer(leavingRoundingSet.getRoundingSet().getRoundingTimeUnit().description)
//										.intValue())
//								: sheet.getLeaveWork().v();
//
//						// ドメインモデル「所属職場履歴」を取得する
//						attendanceStampTemp
//								.setStamp(new WorkStampOutPut(new TimeWithDayAttr(attendanceTimeAfterRouding),
//										sheet.getAttendance(), null, automaticStampSetDetailDto.getAttendanceStamp()));
//						leaveStampTemp.setStamp(new WorkStampOutPut(new TimeWithDayAttr(leaveTimeAfterRounding),
//								sheet.getLeaveWork(), null, automaticStampSetDetailDto.getLeavingStamp()));
//						// attendanceStampTemp.setStamp(new WorkStampOutPut(new
//						// TimeWithDayAttr(sheet.getAttendance().v()),
//						// sheet.getAttendance(), null,
//						// automaticStampSetDetailDto.getAttendanceStamp()));
//						// leaveStampTemp.setStamp(new WorkStampOutPut(new
//						// TimeWithDayAttr(sheet.getLeaveWork().v()),
//						// sheet.getLeaveWork(), null,
//						// automaticStampSetDetailDto.getLeavingStamp()));
//						timeLeavingWorkOutput.setAttendanceStamp(attendanceStampTemp);
//						timeLeavingWorkOutput.setLeaveStamp(leaveStampTemp);
//						timeLeavingWorkTemps.add(timeLeavingWorkOutput);
//					});
//				} else {
					if(workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode() != null) {
						// 出勤休日区分を確認する (Xác nhận 出勤休日区分)
						Optional<WorkType> workTypeOptional = this.workTypeRepository.findByPK(companyId,
								workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTypeCode().v());
						WorkStyle workStyle = this.basicScheduleService
								.checkWorkDay(companyId, workTypeOptional.get().getWorkTypeCode().v());
						if (!(workStyle == WorkStyle.ONE_DAY_REST)) {

							// 所定時間帯を取得する
							PredetermineTimeSetForCalc predetemineTimeSetting = workTimeSettingService
									.getPredeterminedTimezone(companyId,
											workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode().v(),
											workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTypeCode().v(), null);

							if (!predetemineTimeSetting.getTimezones().isEmpty()) {
								List<TimezoneUse> lstTimezone = predetemineTimeSetting.getTimezones();
								for (TimezoneUse timezone : lstTimezone) {
									if (timezone.getUseAtr() == UseSetting.USE) {
										TimeLeavingWorkOutput timeLeavingWorkOutput = new TimeLeavingWorkOutput();
										timeLeavingWorkOutput.setWorkNo(new WorkNo(timezone.getWorkNo()));

										TimeActualStampOutPut attendanceTimeActualStampOutPut = new TimeActualStampOutPut();
										WorkStampOutPut actualStamp = new WorkStampOutPut();
										actualStamp.setTimeWithDay(timezone.getStart());

										TimeActualStampOutPut leaveTimeActualStampOutPut = new TimeActualStampOutPut();
										WorkStampOutPut leaveActualStamp = new WorkStampOutPut();
										leaveActualStamp.setTimeWithDay(timezone.getEnd());

										// 出勤系時刻を丸める
										Optional<WorkTimezoneCommonSet> workTimezoneCommonSet = GetCommonSet.workTimezoneCommonSet(require, companyId,
												workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode().v());
										WorkTimezoneStampSet stampSet = workTimezoneCommonSet.get().getStampSet();

										// 出勤
										RoundingSet atendanceRoundingSet = stampSet.getRoundingTime().getRoundingSets().stream()
												.filter(item -> item.getSection() == Superiority.ATTENDANCE).findFirst()
												.isPresent()
														? stampSet.getRoundingTime().getRoundingSets().stream()
																.filter(item -> item.getSection() == Superiority.ATTENDANCE)
																.findFirst().get()
														: null;

										int attendanceTimeAfterRouding = atendanceRoundingSet != null
												? this.roudingTime(timezone.getStart().v(),
														atendanceRoundingSet.getRoundingSet().getFontRearSection().value,
														new Integer(atendanceRoundingSet.getRoundingSet()
																.getRoundingTimeUnit().description).intValue())
												: timezone.getStart().v();

										actualStamp.setAfterRoundingTime(new TimeWithDayAttr(attendanceTimeAfterRouding));

										// 退勤
										RoundingSet leavingRoundingSet = stampSet.getRoundingTime().getRoundingSets().stream()
												.filter(item -> item.getSection() == Superiority.OFFICE_WORK).findFirst()
												.isPresent() ? stampSet.getRoundingTime().getRoundingSets().stream()
														.filter(item -> item.getSection() == Superiority.OFFICE_WORK)
														.findFirst().get() : null;

										int leaveTimeAfterRounding = leavingRoundingSet != null
												? this.roudingTime(timezone.getEnd().v(),
														leavingRoundingSet.getRoundingSet().getFontRearSection().value,
														new Integer(leavingRoundingSet.getRoundingSet()
																.getRoundingTimeUnit().description).intValue())
												: timezone.getEnd().v();

										leaveActualStamp.setAfterRoundingTime(new TimeWithDayAttr(leaveTimeAfterRounding));

										Optional<AffWorkplaceDto> affWorkplaceDto = this.affWorkplaceAdapter
												.findBySid(employeeID, day);

										if (affWorkplaceDto.isPresent()) {
											actualStamp.setLocationCode(null);
											leaveActualStamp.setLocationCode(null);
										}
										actualStamp.setStampSourceInfo(automaticStampSetDetailDto.getAttendanceStamp());
										leaveActualStamp.setStampSourceInfo(automaticStampSetDetailDto.getLeavingStamp());

										attendanceTimeActualStampOutPut.setStamp(actualStamp);
										leaveTimeActualStampOutPut.setStamp(leaveActualStamp);
										timeLeavingWorkOutput.setAttendanceStamp(attendanceTimeActualStampOutPut);
										timeLeavingWorkOutput.setLeaveStamp(leaveTimeActualStampOutPut);
										timeLeavingWorkTemps.add(timeLeavingWorkOutput);
									}
								}

							}
						}
//					}

//				}
			}
		}
			timeLeavingWorks = timeLeavingWorkTemps.stream().map(item -> {
				TimeActualStamp attendanceStamp = null;
				if (item.getAttendanceStamp() != null) {
					WorkStamp actualStamp = null;
					if (item.getAttendanceStamp().getActualStamp() != null) {
						actualStamp = new WorkStamp(
								item.getAttendanceStamp().getActualStamp().getTimeWithDay(),
								item.getAttendanceStamp().getActualStamp().getLocationCode(),
								item.getAttendanceStamp().getActualStamp().getStampSourceInfo());
					}

					WorkStamp stamp = new WorkStamp(
							item.getAttendanceStamp().getStamp().getTimeWithDay(),
							item.getAttendanceStamp().getStamp().getLocationCode(),
							item.getAttendanceStamp().getStamp().getStampSourceInfo());
					attendanceStamp = new TimeActualStamp(actualStamp, stamp,
							item.getAttendanceStamp().getNumberOfReflectionStamp());

				}

				TimeActualStamp leaveStamp = null;
				if (item.getLeaveStamp() != null) {
					WorkStamp leaveActualStampTemp = null;
					if (item.getLeaveStamp().getActualStamp() != null) {
						leaveActualStampTemp = new WorkStamp(
								item.getLeaveStamp().getActualStamp().getTimeWithDay(),
								item.getLeaveStamp().getActualStamp().getLocationCode(),
								item.getLeaveStamp().getActualStamp().getStampSourceInfo());
					}

					WorkStamp leaveStampTemp = new WorkStamp(
							item.getLeaveStamp().getStamp().getTimeWithDay(),
							item.getLeaveStamp().getStamp().getLocationCode(),
							item.getLeaveStamp().getStamp().getStampSourceInfo());

					leaveStamp = new TimeActualStamp(leaveActualStampTemp, leaveStampTemp,
							item.getLeaveStamp().getNumberOfReflectionStamp());
				}

				return new TimeLeavingWork(item.getWorkNo(), attendanceStamp == null ? null : attendanceStamp,
						leaveStamp);
			}).collect(Collectors.toList());
			automaticStampSetDetailDto.setTimeLeavingWorks(timeLeavingWorks);

			// calculate current time
			Calendar toDay = Calendar.getInstance();
			Date date2 = toDay.getTime();
			int hour = toDay.get(Calendar.HOUR_OF_DAY);
			int minute = toDay.get(Calendar.MINUTE);
			int currentMinuteOfDay = ((hour * 60) + minute);

			List<TimeLeavingWork> timeLeavingWorkList = new ArrayList<>();

			//timeLeavingWork　= セットする出退勤
			for (TimeLeavingWork timeLeavingWork : automaticStampSetDetailDto.getTimeLeavingWorks()) {

				TimeLeavingWork leavingStamp = null;
				//timeLeavingOptional = 実績の出退勤
				if (timeLeavingOptional.getTimeLeavingWorks() != null) {
					leavingStamp = timeLeavingOptional.getTimeLeavingWorks().stream()
							.filter(itemx -> itemx.getWorkNo().v().equals(timeLeavingWork.getWorkNo().v())).findFirst()
							.orElse(new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(),
									new TimeActualStamp()));
				} else {
					leavingStamp = new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(),
							new TimeActualStamp());
				}

				TimeActualStamp attendanceStamp = leavingStamp.getAttendanceStamp().orElse(null);
				TimeActualStamp leaveStamp = leavingStamp.getLeaveStamp().orElse(null);

				// 出勤反映 = true
				// 出勤に自動打刻セットする
				if (automaticStampSetDetailDto.getAttendanceReflectAttr() == UseAtr.USE) {

					// set attendance
					if (stampReflectionManagement.get()
							.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.SET_AUTO_STAMP
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& day.date().before(date2))
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& day.date().equals(date2) && timeLeavingWork.getAttendanceStamp().isPresent()
									&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()
									&& timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()
											.lessThanOrEqualTo(currentMinuteOfDay))) {

						if (timeLeavingOptional.getTimeLeavingWorks() == null || leavingStamp == null
								|| (!leavingStamp.getAttendanceStamp().isPresent())
								|| (leavingStamp.getAttendanceStamp().get().getStamp() == null)
								|| (!leavingStamp.getAttendanceStamp().get().getStamp().isPresent())
								|| (leavingStamp.getAttendanceStamp().get().getStamp().isPresent() && !leavingStamp
										.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent())) {
							attendanceStamp = this.attendanceStampInfor(timeLeavingWork);

						} else {
							WorkStamp stampInfor = leavingStamp.getAttendanceStamp().get().getStamp().get();
							if(stampInfor.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.DIRECT_BOUNCE
									 || stampInfor.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.AUTOMATIC_SET) {
								attendanceStamp = this.attendanceStampInfor(timeLeavingWork);
							}

						}
					}
				}

				// set leave
				// 退勤反映 = true
				if (automaticStampSetDetailDto.getRetirementAttr() == UseAtr.USE) {
					if (stampReflectionManagement.get()
							.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.SET_AUTO_STAMP
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& day.date().before(date2))
							|| (stampReflectionManagement.get()
									.getAutoStampForFutureDayClass() == AutoStampForFutureDayClass.DO_NOT_SET_AUTO_STAMP
									&& day.date().equals(date2) && timeLeavingWork.getLeaveStamp().isPresent()
									&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()
									&& timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()
											.lessThanOrEqualTo(currentMinuteOfDay))) {
						if (timeLeavingOptional.getTimeLeavingWorks() == null || leavingStamp == null
								|| (!leavingStamp.getLeaveStamp().isPresent())
								|| (leavingStamp.getLeaveStamp().get().getStamp() == null)
								|| (!leavingStamp.getLeaveStamp().get().getStamp().isPresent())
								|| (leavingStamp.getLeaveStamp().get().getStamp().isPresent() && !leavingStamp
										.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent())) {
							leaveStamp = this.leaveStampInfor(timeLeavingWork);

						} else {
							WorkStamp leaveWorkStamp = leavingStamp.getLeaveStamp().get().getStamp().get();
							if(leaveWorkStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.DIRECT_BOUNCE
									 || leaveWorkStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == TimeChangeMeans.AUTOMATIC_SET) {
								leaveStamp = this.leaveStampInfor(timeLeavingWork);
							}
						}
					}
				}

				leavingStamp = new TimeLeavingWork(timeLeavingWork.getWorkNo(), attendanceStamp, leaveStamp);
				// leavingStamp.setTimeLeavingWork(timeLeavingWork.getWorkNo(),
				// Optional.of(attendanceStamp), Optional.of(leaveStamp));
				timeLeavingWorkList.add(leavingStamp);
			}
			;
			timeLeavingOptional.setWorkTimes(new WorkTimes(1));
			//timeLeavingOptional.setEmployeeId(employeeID);
			//timeLeavingOptional.setYmd(day);
			timeLeavingOptional.setTimeLeavingWorks(timeLeavingWorkList);
		} else {
			timeLeavingOptional = null;
		}

		return timeLeavingOptional;
	}

	private TimeActualStamp attendanceStampInfor(TimeLeavingWork timeLeavingWork) {
		WorkStamp stamp = new WorkStamp(
				timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get(),
				timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode().isPresent()
						? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode()
								.get()
						: null,
				timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans());
		return new TimeActualStamp(null, stamp,
				timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp());
	}

	private TimeActualStamp leaveStampInfor(TimeLeavingWork timeLeavingWork) {
		WorkStamp stamp = new WorkStamp(
				timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get(),
				timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode().isPresent()
						? timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode()
								.get()
						: null,
				timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans());
		return new TimeActualStamp(null, stamp,
				timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp());
	}
	/**
	 * 加給設定を日別実績に反映する
	 *
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param workInfoOfDailyPerformanceUpdate
	 * @return
	 */
	public Optional<BonusPaySetting> reflectBonusSettingDailyPer(String companyId, String employeeId, GeneralDate day,
			WorkInfoOfDailyAttendance workInfoOfDailyPerformanceUpdate,
			AffiliationInforOfDailyAttd affiliationInforOfDailyPerfor, PeriodInMasterList periodInMasterList) {
		Optional<BonusPaySetting> bonusPaySetting = Optional.empty();
		Optional<MasterList> newMaster = Optional.empty();
		if(periodInMasterList !=null) {
			newMaster = periodInMasterList.getMasterLists().stream()
				.filter(item -> item.getDatePeriod().contains(day)).findFirst();
		}
		if (periodInMasterList == null || !newMaster.isPresent() || !newMaster.get().getBonusPaySettingOpt().isPresent()) {
			// reqList496
			// 職場IDと基準日から上位職場を取得する
//			List<String> workPlaceIdList = this.affWorkplaceAdapter.getUpperWorkplace(companyId,
//					affiliationInforOfDailyPerfor.getWplID(), day);
//
//			// 加給設定を取得する
//			bonusPaySetting = this.reflectBonusSetting(companyId, employeeId, day,
//					workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode() != null
//							? workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode().v() : null,
//					workPlaceIdList);

			return Optional.empty();
		} else {
			// 「期間内マスタ一覧」と「就業時間帯コード」から加給設定を取得する
			// 就業時間帯の加給設定を取得する
			// ドメインモデル「加給利用単位」を取得する
			Optional<BPUnitUseSetting> bPUnitUseSetting = this.bPUnitUseSettingRepository.getSetting(companyId);

			// 加給利用単位．個人使用区分
			if (bPUnitUseSetting.isPresent() && bPUnitUseSetting.get()
					.getWorkingTimesheetUseAtr() == nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr.USE) {
				if (workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode() != null) {
					// ドメインモデル「就業時間帯加給設定」を取得
					Optional<WorkingTimesheetBonusPaySetting> workingTimesheetBonusPaySetting = this.wTBonusPaySettingRepository
							.getWTBPSetting(companyId, new WorkingTimesheetCode(
									workInfoOfDailyPerformanceUpdate.getRecordInfo().getWorkTimeCode().v()));
					if (workingTimesheetBonusPaySetting.isPresent()) {
						Optional<BonusPaySetting> bonusPay = bPSettingRepository.getBonusPaySetting(companyId,
								workingTimesheetBonusPaySetting.get().getBonusPaySettingCode());
						if (!bonusPay.isPresent()) {
							List<MasterList> masterLists = periodInMasterList.getMasterLists();
							Optional<MasterList> newMasterLists = masterLists.stream()
									.filter(item -> item.getDatePeriod().contains(day)).findFirst();

							bonusPaySetting = newMasterLists.isPresent() ? newMasterLists.get().getBonusPaySettingOpt()
									: Optional.empty();

							return bonusPaySetting;
						} else {
							return bonusPay;
						}
					} else {
						// return fail
						List<MasterList> masterLists = periodInMasterList.getMasterLists();
						Optional<MasterList> newMasterLists = masterLists.stream()
								.filter(item -> item.getDatePeriod().contains(day)).findFirst();

						bonusPaySetting = newMasterLists.isPresent() ? newMasterLists.get().getBonusPaySettingOpt()
								: Optional.empty();

						return bonusPaySetting;
					}
				} else {
					// return fail
					List<MasterList> masterLists = periodInMasterList.getMasterLists();
					Optional<MasterList> newMasterLists = masterLists.stream()
							.filter(item -> item.getDatePeriod().contains(day)).findFirst();

					bonusPaySetting = newMasterLists.isPresent() ? newMasterLists.get().getBonusPaySettingOpt()
							: Optional.empty();

					return bonusPaySetting;
				}
			} else {
				// return fail
				List<MasterList> masterLists = periodInMasterList.getMasterLists();
				Optional<MasterList> newMasterLists = masterLists.stream()
						.filter(item -> item.getDatePeriod().contains(day)).findFirst();

				bonusPaySetting = newMasterLists.isPresent() ? newMasterLists.get().getBonusPaySettingOpt()
						: Optional.empty();

				return bonusPaySetting;
			}
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
		}
		return result.multiply(new BigDecimal(roundingTimeUnit)).intValue();
	}

	// 所属情報を反映する
	@Override
	public AffiliationInforState createAffiliationInforState(String companyId, String employeeId, GeneralDate day,
			EmployeeGeneralInfoImport generalInfoImport) {

		// employment
		Map<String, List<ExEmploymentHistItemImport>> mapEmploymentHist = generalInfoImport
				.getEmploymentHistoryImports().stream().collect(Collectors.toMap(
						ExEmploymentHistoryImport::getEmployeeId, ExEmploymentHistoryImport::getEmploymentItems));

		Optional<ExEmploymentHistItemImport> employmentHistItemImport = Optional.empty();
		List<ExEmploymentHistItemImport> exEmploymentHistItemImports = mapEmploymentHist.get(employeeId);
		if (exEmploymentHistItemImports != null) {
			employmentHistItemImport = exEmploymentHistItemImports.stream()
					.filter(empHistItem -> empHistItem.getPeriod().contains(day)).findFirst();
		}

		// classification
		Map<String, List<ExClassificationHistItemImport>> mapClassificationHist = generalInfoImport
				.getExClassificationHistoryImports().stream()
				.collect(Collectors.toMap(ExClassificationHistoryImport::getEmployeeId,
						ExClassificationHistoryImport::getClassificationItems));

		Optional<ExClassificationHistItemImport> classificationHistItemImport = Optional.empty();
		List<ExClassificationHistItemImport> exClassificationHistItemImports = mapClassificationHist.get(employeeId);
		if (exClassificationHistItemImports != null) {
			classificationHistItemImport = exClassificationHistItemImports.stream()
					.filter(item -> item.getPeriod().contains(day)).findFirst();
		}

		// Job title
		Map<String, List<ExJobTitleHistItemImport>> mapJobTitleHist = generalInfoImport
				.getExJobTitleHistoryImports().stream().collect(Collectors.toMap(ExJobTitleHistoryImport::getEmployeeId,
						ExJobTitleHistoryImport::getJobTitleItems));

		Optional<ExJobTitleHistItemImport> jobTitleHistItemImport = Optional.empty();
		List<ExJobTitleHistItemImport> exJobTitleHistItemImports = mapJobTitleHist.get(employeeId);
		if (exJobTitleHistItemImports != null) {
			jobTitleHistItemImport = exJobTitleHistItemImports.stream().filter(item -> item.getPeriod().contains(day))
					.findFirst();
		}

		// workPlace
		Map<String, List<ExWorkplaceHistItemImport>> mapWorkplaceHist = generalInfoImport
				.getExWorkPlaceHistoryImports().stream().collect(Collectors
						.toMap(ExWorkPlaceHistoryImport::getEmployeeId, ExWorkPlaceHistoryImport::getWorkplaceItems));

		Optional<ExWorkplaceHistItemImport> workplaceHistItemImport = Optional.empty();
		List<ExWorkplaceHistItemImport> exWorkplaceHistItemImports = mapWorkplaceHist.get(employeeId);
		if (exWorkplaceHistItemImports != null) {
			workplaceHistItemImport = exWorkplaceHistItemImports.stream().filter(item -> item.getPeriod().contains(day))
					.findFirst();
		}

		// WorkType
		Map<String, BusinessTypeOfEmployeeHis> map = generalInfoImport
				.getExWorkTypeHistoryImports().stream().collect(Collectors
						.toMap(c -> c.getEmployee().getSId(), x -> x));

		Optional<BusinessTypeOfEmployeeHis> worktypeHistItemImport = Optional.empty();
		BusinessTypeOfEmployeeHis exWorktypeHistItemImports = map.get(employeeId);
		if (exWorktypeHistItemImports != null) {
			if(exWorktypeHistItemImports.getHistory().span().contains(day)) {
				worktypeHistItemImport = Optional.of(exWorktypeHistItemImports);
			}
		}

		// Get Data
		List<ErrorMessageInfo> errMesInfos = new ArrayList<>();
		// 存在しない - no data
		if (!employmentHistItemImport.isPresent()) {
			ErrorMessageInfo employmentErrMes = new ErrorMessageInfo(companyId, employeeId, day,
					EnumAdaptor.valueOf(0, ExecutionContent.class),
					new ErrMessageResource("001"),
					new ErrMessageContent(TextResource.localize("Msg_426")));
			errMesInfos.add(employmentErrMes);
		}
		if (!workplaceHistItemImport.isPresent()) {
			ErrorMessageInfo employmentErrMes = new ErrorMessageInfo(companyId, employeeId, day,
					EnumAdaptor.valueOf(0, ExecutionContent.class),
					new ErrMessageResource("002"),
					new ErrMessageContent(TextResource.localize("Msg_427")));
			errMesInfos.add(employmentErrMes);
		}
		if (!classificationHistItemImport.isPresent()) {
			ErrorMessageInfo employmentErrMes = new ErrorMessageInfo(companyId, employeeId, day,
					EnumAdaptor.valueOf(0, ExecutionContent.class),
					new ErrMessageResource("003"),
					new ErrMessageContent(TextResource.localize("Msg_428")));
			errMesInfos.add(employmentErrMes);
		}
		if (!jobTitleHistItemImport.isPresent()) {
			ErrorMessageInfo employmentErrMes = new ErrorMessageInfo(companyId, employeeId, day,
					EnumAdaptor.valueOf(0, ExecutionContent.class),
					new ErrMessageResource("004"),
					new ErrMessageContent(TextResource.localize("Msg_429")));
			errMesInfos.add(employmentErrMes);
		}
		// 大塚モード
		if(!worktypeHistItemImport.isPresent() && AppContexts.optionLicense().customize().ootsuka() == true) {
			ErrorMessageInfo employmentErrMes = new ErrorMessageInfo(companyId, employeeId, day,
					EnumAdaptor.valueOf(0, ExecutionContent.class),
					// trong EA đang để リソースID　=　？？？, nên đang để tạm là 100
					new ErrMessageResource("100"),
					new ErrMessageContent(TextResource.localize("Msg_1010")));
			errMesInfos.add(employmentErrMes);
		}
		// 存在する - has data
		// 「2020/02/12」 追加　Tin
		if (employmentHistItemImport.isPresent() && workplaceHistItemImport.isPresent()
				&& classificationHistItemImport.isPresent() && jobTitleHistItemImport.isPresent()) {

			// get workPlaceGroupId
			// 特定期間の社員情報から該当の社員の職場グループIDを取得する
			Optional<String> workplaceGroupId = Optional.ofNullable(
						generalInfoImport.getEmpWorkplaceGroup()
							.getOrDefault( day, Collections.emptyMap() ).get( employeeId )
					);

			// get Emp License Info
			// 特定期間の社員情報から該当の社員の免許区分を取得する
			 Optional<LicenseClassification> nursingLicenseClass = Optional.empty();
			 Optional<Boolean> isNursingManager = Optional.empty();
			 if(generalInfoImport.getEmpLicense()!=null && !generalInfoImport.getEmpLicense().get(day).isEmpty()){
				 nursingLicenseClass = generalInfoImport.getEmpLicense().get(day).stream()
							.filter(i -> i.getEmpID().equals(employeeId)).findFirst().map(x -> x.getOptLicenseClassification())
							.orElse(Optional.empty());

				 isNursingManager = generalInfoImport.getEmpLicense().get(day).stream()
							.filter(i -> i.getEmpID().equals(employeeId)).findFirst().map(x -> x.getIsNursingManager())
							.orElse(Optional.empty());

				}


			return new AffiliationInforState(Collections.emptyList(),
					Optional.of(new AffiliationInforOfDailyAttd(
							new EmploymentCode(employmentHistItemImport.get().getEmploymentCode()),
							jobTitleHistItemImport.get().getJobTitleId(),
							workplaceHistItemImport.get().getWorkplaceId(),
							new ClassificationCode(classificationHistItemImport.get().getClassificationCode()),
							worktypeHistItemImport.isPresent() ? worktypeHistItemImport.map(c -> c.getEmployee().getBusinessTypeCode()) : Optional.empty(),
							Optional.empty(), // bonusPaySettingCode - thuât toán lấy master bên ngoài không lấy thằng này nên set empty
							workplaceGroupId,
							nursingLicenseClass,
							isNursingManager)),
					errMesInfos);
		} else {
			// #日別作成修正 2018/07/17 前川 隼大
			// 社員の日別実績のエラーを作成する
			// 大塚モードじゃない
				EmployeeDailyPerError employeeDailyPerError = new EmployeeDailyPerError(companyId, employeeId, day,
						new ErrorAlarmWorkRecordCode("S025"), new ArrayList<>());
				this.createEmployeeDailyPerError.createEmployeeError(employeeDailyPerError);

				return new AffiliationInforState(new ArrayList<>(), Optional.empty(), errMesInfos);
		}
	}

}
