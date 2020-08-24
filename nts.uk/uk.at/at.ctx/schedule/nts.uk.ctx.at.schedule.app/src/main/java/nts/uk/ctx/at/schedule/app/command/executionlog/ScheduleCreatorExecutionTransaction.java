package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.task.parallel.ManagedParallelWithContext.ControlOption;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicScheduleResetCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicWorkSettingByClassificationGetterCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicWorkSettingByWorkplaceGetterCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.CalculationCache;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicScheduleHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicWorkSettingHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeErrorLogHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeMonthlyPatternHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTimeHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTypeHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheduleErrorLogGeterCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.WorkTimeConvertCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.WorkdayAttrByClassGetterCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.WorkdayAttrByWorkplaceGeterCommand;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortChildCareFrameDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.ShortWorkTimeDto;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkplaceHistItemImported;
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.CreationMethodClassification;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.DateRegistedEmpSche;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.RegistrationListDateSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule.CorrectWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ProcessingStatus;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatuTempo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ScheManaStatus;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkTypeHisItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkTypeHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ReflectWorkInforDomainService;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * ScheduleCreatorExecutionCommandHandlerから並列で実行されるトランザクション処理を担当するサービス
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ScheduleCreatorExecutionTransaction {

	/** The schedule creator repository. */
	@Inject
	private ScheduleCreatorRepository scheduleCreatorRepository;
	
	 /** The schedule execution log repository. */
	 @Inject
	 private ScheduleExecutionLogRepository scheduleExecutionLogRepository;

	/** The schedule error log repository. */
	@Inject
	private ScheduleErrorLogRepository scheduleErrorLogRepository;

	/** The sche cre exe work type handler. */
	@Inject
	private ScheCreExeWorkTypeHandler scheCreExeWorkTypeHandler;

	/** The sche cre exe basic schedule handler. */
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;

	@Inject
	private ScheCreExeMonthlyPatternHandler scheCreExeMonthlyPatternHandler;

	@Inject
	private I18NResourcesForUK internationalization;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private ManagedParallelWithContext managedParallelWithContext;
	
	public static int MAX_DELAY_PARALLEL = 0;
	
	@Inject
	private CorrectWorkSchedule correctWorkSchedule;
	
	@Inject
	private ReflectWorkInforDomainService inforDomainService; 
	
	// note 日別のコンバーターを作成する
	@Inject
	private DailyRecordConverter converter;
	
	@Inject
	private WorkScheduleRepository workScheduleRepository;
	
	@Inject
	private WorkTimeSettingService workTimeService;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private ScWorkplaceAdapter scWorkplaceAdapter;
	
	@Inject
	private ScheCreExeBasicWorkSettingHandler basicWorkSettingHandler;
	
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;
	
	@Inject
	private ClassifiBasicWorkRepository classificationBasicWorkRepository;
	
	@Inject
	private CompanyBasicWorkRepository companyBasicWorkRepository;
	
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;
	
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepository;
	
	@Inject
	private ScheCreExeWorkTimeHandler workTimeHandler;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	
	@Inject
	private BasicScheduleService basicScheduleService;


	public void execute(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, String companyId, String exeId,
			DatePeriod period, CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			final nts.arc.layer.app.command.AsyncCommandHandlerContext<nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand> asyncTask,
			Object companySetting, ScheduleCreator scheduleCreator, CacheCarrier carrier) {
		RegistrationListDateSchedule registrationListDateSchedule = new RegistrationListDateSchedule(new ArrayList<>());

		ScheduleCreateContent content = command.getContent();

		if (masterCache.getListWorkingConItem().size() > 1) {
			// note 労働条件が途中で変化するなら、計算キャッシュは利用しない
			// note do not cache result of calculation if working condition of the employee is
			// note changed in the period
			CalculationCache.clear();
		} else {
			CalculationCache.initialize();
		}
		try {
			// note実行区分をチェックする
			if(command.getContent().getImplementAtr() == ImplementAtr.DELETE_WORK_SCHEDULE) {
				// note勤務予定削除する
				this.deleteSchedule(scheduleCreator.getEmployeeId(),period);
			} else {
				// note勤務予定作成する  ↓
				this.createSchedule(command, scheduleExecutionLog, context, period, masterCache, listBasicSchedule,
					companySetting, scheduleCreator, registrationListDateSchedule, content, carrier);
				// note ----------↑
			}
		} finally {
			CalculationCache.clear();
		}

		scheduleCreator.updateToCreated();
		this.scheduleCreatorRepository.update(scheduleCreator);

		// note Đang không cần thiết vì trong mỗi xử lý đã thực hiện việc này
		// note 暫定データを作成する (Tạo data tạm)
// note		registrationListDateSchedule.getRegistrationListDateSchedule().forEach(x -> {
// note			// note アルゴリズム「暫定データの登録」を実行する(Thực hiện thuật toán [đăng ký data tạm]) 
// note			this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId, x.getEmployeeId(), x.getListDate());
// note		});
		
	}

	private void createSchedule(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod period,
			CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule, Object companySetting,
			ScheduleCreator scheduleCreator, RegistrationListDateSchedule registrationListDateSchedule,
			ScheduleCreateContent content, CacheCarrier carrier) {
		String companyId = AppContexts.user().companyId();
		// note 実施区分を判断, 処理実行区分を判断
		// note EA No2115
		// note 中断フラグを確認する
		// note if 中断
		if (content.getImplementAtr() == ImplementAtr.CREATE_WORK_SCHEDULE
				&& content.getReCreateContent().getProcessExecutionAtr() == ProcessExecutionAtr.RECONFIG) {
			BasicScheduleResetCommand commandReset = BasicScheduleResetCommand.create(command, companySetting,
					scheduleCreator, content);
// note			// note スケジュールを再設定する (Thiết lập lại schedule)
// note			// note ドメインモデル「スケジュール作成実行ログ」を更新する ở trong xử lý này
			this.resetScheduleWithMultiThread(commandReset, context, period, masterCache.getEmpGeneralInfo(),
					masterCache.getListBusTypeOfEmpHis(), listBasicSchedule, registrationListDateSchedule);
		} else {
			// note else 中断じゃない
			// note 入力パラメータ「作成方法区分」を判断-check parameter
			// note CreateMethodAtr
			if (content.getCreateMethodAtr() == CreateMethodAtr.PERSONAL_INFO) {
		command.setCompanySetting(companySetting);
		// note 勤務予定を作成する - return : ・勤務予定一覧 ・エラー一覧
		OutputCreateSchedule result = this.createScheduleBasedPersonWithMultiThread(command, scheduleCreator, scheduleExecutionLog, context,
				period, masterCache, listBasicSchedule, registrationListDateSchedule, carrier);
		
		// note Outputの勤務種類一覧を繰り返す
		this.managedParallelWithContext.forEach(ControlOption.custom().millisRandomDelay(MAX_DELAY_PARALLEL),
				result.getListWorkSchedule(), ws -> {
					// note 勤務予定を登録する
					boolean checkUpdate = this.workScheduleRepository.checkExits(ws.getEmployeeID(), ws.getYmd());
					if (checkUpdate) {
						this.workScheduleRepository.update(ws);
					} else {
						this.workScheduleRepository.insert(ws);
					}
					;
					// note 暫定データの登録
					this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId, ws.getEmployeeID(),
							Arrays.asList(ws.getYmd()));
				});

		// note エラー一覧を繰り返す
		this.managedParallelWithContext.forEach(ControlOption.custom().millisRandomDelay(MAX_DELAY_PARALLEL),
				result.getListError(), error -> {
					// note エラーを登録する
					error.setExecutionId(command.getExecutionId());
					this.scheduleErrorLogRepository.addByTransaction(error);
				});
				
			}
		}
	}
	
	// note ドメインモデル「スケジュール作成実行ログ」を更新する
	private void updateStatusScheduleExecutionLog(ScheduleExecutionLog domain, CompletionStatus completionStatus) {
		// note check exist data schedule error log
		domain.setCompletionStatus(completionStatus);
		domain.updateExecutionTimeEndToNow();
		this.scheduleExecutionLogRepository.update(domain);
	}
	
	// note 勤務予定削除
	private void deleteSchedule(String employeeId,DatePeriod period) {
		String companyId = AppContexts.user().companyId();
		// note勤務予定ドメインを削除する (TKT-TQP)
		// noteTODO: đang đợi Hiểu làm đề gọi vào
		workScheduleRepository.delete(employeeId, period);
//		// note暫定データの登録
//		this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId, employeeId, period.datesBetween());
		
	}

	/**
	 * Reset schedule.
	 *
	 * @param command
	 *            the command
	 * @param creator
	 *            the creator
	 * @param domain
	 *            the domain
	 */
	// note スケジュールを再設定する
	private void resetScheduleWithMultiThread(BasicScheduleResetCommand command,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod targetPeriod,
			EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis,
			List<BasicSchedule> listBasicSchedule, RegistrationListDateSchedule registrationListDateSchedule) {

		// note get info by context
		val asyncTask = context.asAsync();

		DateRegistedEmpSche dateRegistedEmpSche = new DateRegistedEmpSche(command.getEmployeeId(), new ArrayList<>());
		// note loop start period date => end period date
		for (val toDate : targetPeriod.datesBetween()) {
			// note 中断フラグを判断
			if (asyncTask.hasBeenRequestedToCancel()) {
				// note ドメインモデル「スケジュール作成実行ログ」を更新する (update)
				this.updateStatusScheduleExecutionLog(context.getCommand().getScheduleExecutionLog(),CompletionStatus.INTERRUPTION);
				
				asyncTask.finishedAsCancelled();
				break;
			}
			// note ドメインモデル「勤務予定基本情報」を取得する
			// note fix for response
			Optional<BasicSchedule> optionalBasicSchedule = listBasicSchedule.stream().filter(
					x -> (x.getEmployeeId().equals(command.getEmployeeId()) && x.getDate().compareTo(toDate) == 0))
					.findFirst();
			if (optionalBasicSchedule.isPresent()) {
				command.setWorkingCode(optionalBasicSchedule.get().getWorkTimeCode());
				command.setWorkTypeCode(optionalBasicSchedule.get().getWorkTypeCode());
				// note 入力パラメータ「再作成区分」を判断
				// note 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
				if (command.getReCreateAtr() == ReCreateAtr.ALL_CASE.value
						|| optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.UNSETTLED) {
					// note 再設定する情報を取得する
					this.scheCreExeBasicScheduleHandler.resetAllDataToCommandSave(command, toDate, empGeneralInfo,
							listBusTypeOfEmpHis, listBasicSchedule, dateRegistedEmpSche);
				}
			}
		}

		if (dateRegistedEmpSche.getListDate().size() > 0) {
			registrationListDateSchedule.getRegistrationListDateSchedule().add(dateRegistedEmpSche);
		}
	}

	/**
	 * tra ve true la muon ket thuc vong lap tra ve false la k chay cac xu ly ben
	 * duoi, sang object tiep theo 日のデータを用意する
	 * hàm này là code của bác Bình - để riêng ra và viết 1 hàm mới
	 * @param command
	 * @param creator
	 * @param domain
	 * @param context
	 * @param dateInPeriod
	 * @param masterCache
	 * @param listBasicSchedule
	 * @param dateRegistedEmpSche
	 * @return
	 */

	/**
	 * 「パラメータ」 ・社員の在職状態一覧 ・労働条件一覧 ・実施区分 「Output」 ・データ（処理状態付き）
	 */
	private boolean createScheduleBasedPersonOneDate(ScheduleCreatorExecutionCommand command, ScheduleCreator creator,
			ScheduleExecutionLog domain, CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod targetPeriod, GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {

		// note 「社員の在職状態」から該当社員、該当日の在職状態を取得する
		// note EA修正履歴 No2716
		List<EmploymentInfoImported> listEmploymentInfo = masterCache.getMapEmploymentStatus()
				.get(creator.getEmployeeId());
		Optional<EmploymentInfoImported> optEmploymentInfo = Optional.empty();
		// note Đoạn này không biết có cần dùng hay không
		if (listEmploymentInfo != null) {
			optEmploymentInfo = listEmploymentInfo.stream()
					.filter(employmentInfo -> employmentInfo.getStandardDate().equals(dateInPeriod)).findFirst();
		}
		// note データ（処理状態付き）を生成して返す
		// note if 退職、取得できない
		// note status employment equal RETIREMENT (退職)
		if (!optEmploymentInfo.isPresent()
				|| optEmploymentInfo.get().getEmploymentState() == ScheduleCreatorExecutionCommandHandler.RETIREMENT) {
			/**
			 * return (chưa làm) 社員の当日在職状態＝Null 社員の当日労働条件＝Null エラー＝Null 勤務予定＝Null
			 * 処理状態＝処理終了する
			 */
			return true;
		}
		EmploymentInfoImported employmentInfo = optEmploymentInfo.get();
		// note データ（処理状態付き）を生成して返す
		// note if 入社前OR出向中
		// note status employment equal BEFORE_JOINING (入社前) or equal ON_LOAN (出向中)
		if (employmentInfo.getEmploymentState() == ScheduleCreatorExecutionCommandHandler.BEFORE_JOINING
				|| employmentInfo.getEmploymentState() == ScheduleCreatorExecutionCommandHandler.ON_LOAN) {
			/**
			 * return (chưa làm) 社員の当日在職状態＝Null 社員の当日労働条件＝Null エラー＝Null 勤務予定＝Null 処理状態＝次の日へ
			 */
			return false;
		}

		// note if 以外
		// note 労働条件情報からパラメータ.社員ID、ループ中の対象日から該当する労働条件項目を取得する
		// note EA修正履歴 No1830
		Optional<WorkCondItemDto> _workingConditionItem = masterCache.getListWorkingConItem().stream().filter(
				x -> x.getDatePeriod().contains(dateInPeriod) && creator.getEmployeeId().equals(x.getEmployeeId()))
				.findFirst();
		// note if 取得失敗
		// note データ（処理状態付き）を生成して返す (chưa làm)
		/**
		 * return 社員の当日在職状態＝Null 社員の当日労働条件＝Null エラー＝エラー内容 勤務予定＝Null 処理状態＝次の日へ（エラーあり）
		 * 実行ID = Null
		 * 
		 * 会社ID = 入力パラメータ. 会社ID 年月日 = 入力パラメータ. 対象日 エラー内容 = #Msg_602# {0}：#KSC001_87
		 */
		if (!_workingConditionItem.isPresent()) {
			String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
			// note ドメインモデル「スケジュール作成エラーログ」を登録する
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
					dateInPeriod, creator.getEmployeeId());
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			return false;
		}

		// note if 取得できた
		WorkCondItemDto workingConditionItem = _workingConditionItem.get();
		// note 「労働条件項目. 予定管理区分」を確認する
		if (workingConditionItem.getScheduleManagementAtr() == ManageAtr.NOTUSE) {
			// note データ（処理状態付き）を生成して返す (chưa làm)
			/**
			 * return 社員の当日在職状態＝Null 社員の当日労働条件＝Null エラー＝エラー内容 勤務予定＝Null 処理状態＝次の日へ
			 */
			return false;
		}

		if (!workingConditionItem.getScheduleMethod().isPresent()) {
			return false;
		}
		// note ドメイン「勤務予定」を取得する 
		WorkScheduleBasicCreMethod basicCreateMethod = workingConditionItem.getScheduleMethod().get()
				.getBasicCreateMethod();
		switch (basicCreateMethod) {
		case BUSINESS_DAY_CALENDAR:
			// note アルゴリズム「営業日カレンダーで勤務予定を作成する」を実行する
			this.createWorkScheduleByBusinessDayCalenda(command, dateInPeriod, workingConditionItem, masterCache,
					listBasicSchedule, dateRegistedEmpSche, employmentInfo);
			return false;
		case MONTHLY_PATTERN:
			// note アルゴリズム「月間パターンで勤務予定を作成する」を実行する
			// note create schedule by monthly pattern
			this.scheCreExeMonthlyPatternHandler.createScheduleWithMonthlyPattern(command, dateInPeriod,
					workingConditionItem, masterCache, listBasicSchedule, dateRegistedEmpSche, employmentInfo);
			return false;
		case PERSONAL_DAY_OF_WEEK:
			// note アルゴリズム「個人曜日別で勤務予定を作成する」を実行する
			// note TODO
			// note 対象外
			return false;
		default:
			return false;
		}

	}
	
	/**
	 * 日のデータを用意する - method
	 * 「パラメータ」 ・社員の在職状態一覧 ・労働条件一覧 ・実施区分 
	 * 「Output」 ・データ（処理状態付き）
	 */
	private DataProcessingStatusResult createScheduleBasedPersonOneDate_New(ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator, ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, GeneralDate dateInPeriod,
			CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {

		String CID = AppContexts.user().companyId();

		// note 「社員の在職状態」から該当社員、該当日の在職状態を取得する
		// note EA修正履歴 No2716
		List<ScheManaStatuTempo> listEmploymentInfo = masterCache.getListManaStatuTempo();
		Optional<ScheManaStatuTempo> optEmploymentInfo = Optional.empty();

		if (listEmploymentInfo != null) {
			optEmploymentInfo = listEmploymentInfo.stream()
					.filter(employmentInfo -> employmentInfo.getDate().equals(dateInPeriod)).findFirst();
		}
		// note データ（処理状態付き）を生成して返す
		// note if 退職、取得できない
		// note status employment equal RETIREMENT (退職)
		if (!optEmploymentInfo.isPresent()
				|| optEmploymentInfo.get().getScheManaStatus().value == ScheManaStatus.NOT_ENROLLED.value) {

			// note return 社員の当日在職状態＝Null, 社員の当日労働条件＝Null, エラー＝Null, 勤務予定＝Null, 処理状態＝処理終了する
			DataProcessingStatusResult result = new DataProcessingStatusResult(null, null,
					ProcessingStatus.valueOf(ProcessingStatus.END_PROCESS.value), null, null, null);
			return result;
		}
		ScheManaStatuTempo employmentInfo = optEmploymentInfo.get();
		// note 
		// note if 入社前OR出向中
		// note status employment equal BEFORE_JOINING (入社前) or equal ON_LOAN (出向中) (thay đổi enum)
		if (employmentInfo.getScheManaStatus() == ScheManaStatus.INVALID_DATA
				|| employmentInfo.getScheManaStatus() == ScheManaStatus.DO_NOT_MANAGE_SCHEDULE) {

			// note return 社員の当日在職状態＝Null 社員の当日労働条件＝Null エラー＝Null 勤務予定＝Null 処理状態＝次の日へ
			DataProcessingStatusResult result = new DataProcessingStatusResult(null, null,
					ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY.value), null, null, null);
			return result;
		}

		// note if 以外
		// note 労働条件情報からパラメータ.社員ID、ループ中の対象日から該当する労働条件項目を取得する
		// note EA修正履歴 No1830
		Optional<WorkCondItemDto> _workingConditionItem = masterCache.getListWorkingConItem().stream().filter(
				x -> x.getDatePeriod().contains(dateInPeriod) && creator.getEmployeeId().equals(x.getEmployeeId()))
				.findFirst();
		// note if 取得失敗
		if (!_workingConditionItem.isPresent()) {
			String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
			// note ドメインモデル「スケジュール作成エラーログ」を登録する
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod,
					creator.getEmployeeId());
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			DataProcessingStatusResult result = new DataProcessingStatusResult(null, scheduleErrorLog,
					ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY_WITH_ERROR.value), null, null, null);
			return result;
		}

		// note if 取得できた
		WorkCondItemDto workingConditionItem = _workingConditionItem.get();
		// note 「労働条件項目. 予定管理区分」を確認する
		// note if 予定管理しない
		if (workingConditionItem.getScheduleManagementAtr() == ManageAtr.NOTUSE) {

			String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
			// note ドメインモデル「スケジュール作成エラーログ」を登録する
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod,
					creator.getEmployeeId());
			// note return 社員の当日在職状態＝Null, 社員の当日労働条件＝Null, エラー＝エラー内容, 勤務予定＝Null, 処理状態＝次の日へ
			DataProcessingStatusResult result = new DataProcessingStatusResult(CID, scheduleErrorLog,
					ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY.value), null, null, null);
			return result;
		}

		// note ドメイン「勤務予定」を取得する
		Optional<WorkSchedule> workSchedule = workScheduleRepository.get(command.getEmployeeId(), dateInPeriod);

		// note if 取得できる
		if (workSchedule.isPresent()) {
			// note 勤務予定作成する
			if (command.getContent().getImplementAtr().value == ImplementAtr.CREATE_WORK_SCHEDULE.value) {
				// note 
				return new DataProcessingStatusResult(CID, null,
						ProcessingStatus.valueOf(ProcessingStatus.NORMAL_PROCESS.value), workSchedule.get(),
						workingConditionItem, employmentInfo);
			}

			// note 新規のみ作成する
			if (command.getContent().getImplementAtr().value == ImplementAtr.CREATE_NEW_ONLY.value) {
				// note 
				return new DataProcessingStatusResult(CID, null,
						ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY.value), null, null, null);
			}
		}

		// note else 取得できない
		// note 空の勤務予定を作成する
		// note データ（処理状態付き）を生成して返す
		return new DataProcessingStatusResult(CID, null,
				ProcessingStatus.valueOf(ProcessingStatus.NORMAL_PROCESS.value), 
				new WorkSchedule(command.getEmployeeId(), dateInPeriod, ConfirmedATR.UNSETTLED, 
						new WorkInfoOfDailyAttendance(new WorkInformation("", ""), 
								new WorkInformation("", ""), 
								CalculationState.No_Calculated, NotUseAttribute.Not_use, NotUseAttribute.Not_use, 
								nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek.valueOf(dateInPeriod.dayOfWeek() - 1), new ArrayList<>()), 
						null, 
						new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty())
				, workingConditionItem,
				employmentInfo);

	}

	/**
	 * 個人情報をもとにスケジュールを作成する-Creates the schedule based person.
	 * 勤務予定を作成する
	 * @param command
	 * @param creator
	 * @param domain
	 * @param context
	 * @param dateAfterCorrection
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 * @param listWorkType
	 * @param listWorkTimeSetting
	 * @param listBusTypeOfEmpHis
	 * @param allData
	 * @param mapFixedWorkSetting
	 * @param mapFlowWorkSetting
	 * @param mapDiffTimeWorkSetting
	 */
	private OutputCreateSchedule createScheduleBasedPersonWithMultiThread(ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator, ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod targetPeriod,
			CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			RegistrationListDateSchedule registrationListDateSchedule, CacheCarrier carrier) {
		
		// note 空の勤務予定一覧を作成する 
		List<WorkSchedule> listWorkSchedule = new ArrayList<>();
		// note 空のエラー一覧を作成する
		List<ScheduleErrorLog> listError = new ArrayList<>();
		
		DateRegistedEmpSche dateRegistedEmpSche = new DateRegistedEmpSche(creator.getEmployeeId(), new ArrayList<>());
		// note 入力パラメータ「対象開始日」から「対象終了日」をループ処理する
		AtomicBoolean checkEndProcess = new AtomicBoolean(false);
		// note 対象期間を繰り返す
		targetPeriod.datesBetween().forEach(dateInPeriod -> {
			if(checkEndProcess.get()) {
				return;
			}
// note 		xu ly của bac Binh, nhung khong thay trong EA nen dang comment vao			
// note			boolean isEndLoop = this.createScheduleBasedPersonOneDate(command, creator, domain, context, targetPeriod, dateInPeriod,
// note					masterCache, listBasicSchedule, dateRegistedEmpSche);
// note			if (isEndLoop)
// note				return;
			
			// note 勤務予定反映する
			// note 「パラメータ」 ・パラメータ（Temporary） ・勤務ペアリスト ・勤務種類コード ・就業時間帯コード ・年月日
			// note ・勤務サイクルコード・スタート勤務サイクル ・勤務サイクルスタート位置 ・休日優先方法 ・個人スケジュール休日パターン設定
			// note 「Output」 ・勤務予定 ・エラー ・処理状態/
			
			// note Output。処理状態を確認する (call to method 処理状態を確認する)
			OutputCreateScheduleOneDate createScheduleOneDate = this.reflectWorkSchedule(command, creator, domain,
					context,targetPeriod, dateInPeriod, masterCache, listBasicSchedule, dateRegistedEmpSche, carrier);
			switch(createScheduleOneDate.getProcessingStatus()) {
			case NEXT_DAY:// note 次の日へ
				break;
			case END_PROCESS:// note 処理終了する
				checkEndProcess.set(true);
				break;
			case NEXT_DAY_WITH_ERROR:// note 次の日へ（エラーあり）
				listError.add(createScheduleOneDate.getScheduleErrorLog());
				break;
			default:// note 処理正常
				// note 勤務予定を補正する - call 勤務予定を補正する
				WorkSchedule workSchedule = correctWorkSchedule.correctWorkSchedule(
						createScheduleOneDate.getWorkSchedule(), creator.getEmployeeId(), dateInPeriod);

				// note 補正済みの勤務予定を勤務予定一覧に入れる
				listWorkSchedule.add(workSchedule);
				break;
			}
			
		});

		if (dateRegistedEmpSche.getListDate().size() > 0) {
			registrationListDateSchedule.getRegistrationListDateSchedule().add(dateRegistedEmpSche);
		}
		// note勤務予定一覧、エラー一覧を返す
		return new OutputCreateSchedule(listWorkSchedule, listError);

	}

	/**
	 * 営業日カレンダーで勤務予定を作成する
	 * 
	 * Creates the work schedule by business day calendar.
	 * 
	 * @param command
	 * @param workingConditionItem
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 */
	private void createWorkScheduleByBusinessDayCalenda(ScheduleCreatorExecutionCommand command,
			GeneralDate dateInPeriod, WorkCondItemDto workingConditionItem, CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule, DateRegistedEmpSche dateRegistedEmpSche,
			EmploymentInfoImported employmentInfo) {

		// note ドメインモデル「勤務予定基本情報」を取得する(lấy dữ liệu domain 「勤務予定基本情報」)
		// note fix for response
		Optional<BasicSchedule> optionalBasicSchedule = listBasicSchedule.stream()
				.filter(x -> (x.getEmployeeId().equals(workingConditionItem.getEmployeeId())
						&& x.getDate().compareTo(dateInPeriod) == 0))
				.findFirst();

		if (optionalBasicSchedule.isPresent()) {
			BasicSchedule basicSchedule = optionalBasicSchedule.get();
			// note checked2018
			// note 登録前削除区分をTrue（削除する）とする
			// note command.setIsDeleteBeforInsert(true); // note FIX BUG #87113
			// note check parameter implementAtr recreate (入力パラメータ「実施区分」を判断)
			// note 入力パラメータ「実施区分」を判断(kiểm tra parameter 「実施区分」)
			if (command.getContent().getImplementAtr().value == ImplementAtr.CREATE_WORK_SCHEDULE.value) {
				this.createWorkScheduleByRecreate(command, dateInPeriod, basicSchedule, workingConditionItem,
						employmentInfo, masterCache, listBasicSchedule, dateRegistedEmpSche);
			}
		} else {
			// note EA No1841
			ScheMasterInfo scheMasterInfo = new ScheMasterInfo(null);
			BasicSchedule basicSche = new BasicSchedule(null, scheMasterInfo);
			if (ImplementAtr.CREATE_WORK_SCHEDULE == command.getContent().getImplementAtr()
					&& !this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command, dateInPeriod,
							basicSche, employmentInfo, workingConditionItem, masterCache)) {
				return;
			}

			// note 登録前削除区分をTrue（削除する）とする
			// note checked2018
			// note command.setIsDeleteBeforInsert(false); // note FIX BUG #87113

			// note not exist data basic schedule
			this.scheCreExeWorkTypeHandler.createWorkSchedule(command, dateInPeriod, workingConditionItem, masterCache,
					listBasicSchedule, dateRegistedEmpSche);
		}
	}
	
	
	/**
	 * 勤務予定反映する
	 * @param result
	 * @return
	 */
	private OutputCreateScheduleOneDate reflectWorkSchedule(ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator, ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod targetPeriod,
			GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche, CacheCarrier carrier) {

		OutputCreateScheduleOneDate createScheduleOneDate = new OutputCreateScheduleOneDate();
		IntegrationOfDaily integrationOfDaily = null;
		// note 日のデータを用意する
		DataProcessingStatusResult result = this.createScheduleBasedPersonOneDate_New(command, creator, domain, context,
				dateInPeriod, masterCache, listBasicSchedule, dateRegistedEmpSche);
		// note Output。処理状態を確認する
		// note if 以外
		if (result.getProcessingStatus().value != ProcessingStatus.NORMAL_PROCESS.value) {
			// note 「処理状態」、「勤務予定」、「エラー」を返す
			createScheduleOneDate = new OutputCreateScheduleOneDate(null, result.getErrorLog(),
					ProcessingStatus.valueOf(result.getProcessingStatus().value));
			return createScheduleOneDate;
		} else {
			// note 日別のコンバーターを作成する
			integrationOfDaily = new IntegrationOfDaily(result.getWorkSchedule().getEmployeeID(), result.getWorkSchedule().getYmd(),result.getWorkSchedule().getWorkInfo(), null,
					result.getWorkSchedule().getAffInfo(), Optional.empty(), new ArrayList<>(), Optional.empty(),
					result.getWorkSchedule().getLstBreakTime(), result.getWorkSchedule().getOptAttendanceTime(),
					result.getWorkSchedule().getOptTimeLeaving(), result.getWorkSchedule().getOptSortTimeWork(),
					Optional.empty(), Optional.empty(), Optional.empty(), result.getWorkSchedule().getLstEditState(),
					Optional.empty(), new ArrayList<>());
			// note // note 勤務予定。編集状態一覧から項目IDを取得する - TQP
			List<Integer> attendanceItemIdList = integrationOfDaily.getEditState().stream()
					.map(editState -> editState.getAttendanceItemId()).distinct().collect(Collectors.toList());

			DailyRecordToAttendanceItemConverter itemConverter = converter.createDailyConverter()
					.setData(integrationOfDaily).completed();
			List<ItemValue> listItemValue = itemConverter.convert(attendanceItemIdList);

			// note 勤務予定のデータをコンバーターに入れる
			// note convert data from CreateScheduleMasterCache to EmployeeGeneralInfoImport
			EmployeeGeneralInfoImport generalInfoImport = this.convertEmployeeGeneral(masterCache);

			// note 所属情報を反映する
			AffiliationInforState inforState = inforDomainService.createAffiliationInforState(command.getCompanyId(),
					command.getEmployeeId(), dateInPeriod, generalInfoImport);
			
			// note Outputを確認する
			// note if エラーあり
			if (!inforState.getErrorNotExecLogID().isEmpty()) {
				// note 「処理状態、「エラー」を返す」、「勤務予定」 
				ErrorMessageInfo errorMessageInfo = inforState.getErrorNotExecLogID().get(0);
				ScheduleErrorLog errorLog = new ScheduleErrorLog(errorMessageInfo.getMessageError().v(), null,
						errorMessageInfo.getProcessDate(), errorMessageInfo.getEmployeeID());
				createScheduleOneDate = new OutputCreateScheduleOneDate(null, errorLog,
						ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY_WITH_ERROR.value));
				return createScheduleOneDate;
			}
			if(inforState.getAffiliationInforOfDailyPerfor().isPresent()) {
				integrationOfDaily.setAffiliationInfor(inforState.getAffiliationInforOfDailyPerfor().get());
			}

			// note 勤務情報・勤務時間を用意する ↓
			Map<GeneralDate, WorkInformation> results = new HashMap<>();
			PrepareWorkOutput prepareWorkOutput = this.getListTimeZone(command, creator, domain, context, targetPeriod, dateInPeriod,
					masterCache, listBasicSchedule, dateRegistedEmpSche, results, carrier);

			// note Outputを確認する
			if (prepareWorkOutput.getExecutionLog().isPresent()) {
				// note 「勤務予定」、「エラー」を返す」、「処理状態」
				createScheduleOneDate = new OutputCreateScheduleOneDate(null, prepareWorkOutput.getExecutionLog().get(),
						ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY_WITH_ERROR.value));
			} else {
				WorkInformation information = new WorkInformation(prepareWorkOutput.getInformation().getWorkTimeCode(), prepareWorkOutput.getInformation().getWorkTypeCode());

				// note 勤務情報が正常な状態かをチェックする - xử lý tiếp theo call đến method ở dưới
				createScheduleOneDate = this.putDataWorkschedule(information, prepareWorkOutput, integrationOfDaily, command, dateInPeriod,
						prepareWorkOutput.getScheduleTimeZone(), attendanceItemIdList, itemConverter, listItemValue);
			}
		}
		return createScheduleOneDate;
	}
	
	/**
	 * 勤務情報・勤務時間を用意する
	 * @param command
	 * @param creator
	 * @param domain
	 * @param context
	 * @param targetPeriod
	 * @param dateInPeriod
	 * @param masterCache
	 * @param listBasicSchedule
	 * @param dateRegistedEmpSche
	 * @param results
	 * @param carrier
	 * @return
	 */
	public PrepareWorkOutput getListTimeZone(ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator, ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, DatePeriod targetPeriod,
			GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche,Map<GeneralDate, WorkInformation> results, CacheCarrier carrier) {
		// note 勤務情報を取得する ↓
					PrepareWorkOutput output = this.getWorkInfo(command, creator, domain, context, targetPeriod, dateInPeriod,
							masterCache, listBasicSchedule, dateRegistedEmpSche, results, carrier);

					// note Outputを確認する
					PrepareWorkOutput prepareWorkOutput = new PrepareWorkOutput(null, null, null, Optional.empty());
						if (output.getExecutionLog().isPresent()) {
							// note エラーあり
							prepareWorkOutput = new PrepareWorkOutput(null, null, new ArrayList<ShortWorkTimeDto>(), output.getExecutionLog());
									return prepareWorkOutput;
							// notethrow new BusinessException(output.getExecutionLog().get().getErrorContent());
						} else {
							
							// note Nullの場合
							// note 勤務予定時間帯を取得する
							List<TimezoneUse> lstTimeZone = new ArrayList<>();
							if (output.getInformation().getWorkTimeCode() == null) {
								// note 勤務予定時間帯を返す
								lstTimeZone = new ArrayList<>();
							} else {
								// note Nullではない場合
								// note 所定時間帯を取得する
								PredetermineTimeSetForCalc getTimezone = workTimeService.getPredeterminedTimezone(
										AppContexts.user().companyId(), output.getInformation().getWorkTimeCode().v(),
										output.getInformation().getWorkTypeCode().v(), null);
								// note 取得できた所定時間帯Listを絞り込みする
								// note 勤務予定時間帯を返す
								lstTimeZone = getTimezone.getTimezones().stream().filter(x -> x.getUseAtr() == UseSetting.USE)
										.collect(Collectors.toList());
							}
							
							// note -----------↑
							// note 「パラメータ（Temporary）。社員の短時間勤務一覧」から該当の短時間勤務を取得する
							// note 勤務情報、勤務予定時間帯、社員の短時間勤務を返す
							 prepareWorkOutput = new PrepareWorkOutput(output == null ? null : output.getInformation(), lstTimeZone,
									masterCache.getListShortWorkTimeDto(),
									output == null ?  Optional.empty() : output.getExecutionLog() != null && output.getExecutionLog().isPresent() ? output.getExecutionLog() : Optional.empty(),
									output == null ?  Optional.empty() : output.getWorkType() != null && output.getWorkType().isPresent() ? output.getWorkType() : Optional.empty());
						}
						return prepareWorkOutput;
	}

	
	public OutputCreateScheduleOneDate putDataWorkschedule(WorkInformation information,
			PrepareWorkOutput prepareWorkOutput, IntegrationOfDaily integrationOfDaily,
			ScheduleCreatorExecutionCommand command, GeneralDate dateInPeriod, List<TimezoneUse> lstTimeZone,
			List<Integer> attendanceItemIdList, DailyRecordToAttendanceItemConverter itemConverter,
			List<ItemValue> listItemValue) {
		// note 勤務情報が正常な状態かをチェックする
		
		WorkInformation.Require require = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository, workTimeSettingService, basicScheduleService);
		ErrorStatusWorkInfo checkErrorCondition = information.checkErrorCondition(require);

		// note 正常の場合
		if (checkErrorCondition.value == ErrorStatusWorkInfo.NORMAL.value) {
			// note 取得した情報をもとに「勤務予定」を入れる (TKT-TQP)
			Optional<WorkTypeSet> workTypeSet = prepareWorkOutput.getWorkType().isPresent() ? prepareWorkOutput.getWorkType().get().getWorkTypeSetList().stream()
					.filter(x -> x.getCompanyId().equals(command.getCompanyId())
							&& x.getWorkTypeCd().equals(prepareWorkOutput.getInformation().getWorkTypeCode()))
					.findFirst() : Optional.empty();
			// note 勤務情報。勤務実績の勤務情報。勤務種類 = 処理中の勤務種類コード & 勤務情報。勤務実績の勤務情報。就業時間帯 =処理中の 就業時間帯コード
			integrationOfDaily.getWorkInformation().setRecordInfo(new WorkInformation(prepareWorkOutput.getInformation().getWorkTimeCode(), prepareWorkOutput.getInformation().getWorkTypeCode()));
			// note 出勤打刻自動セット ~ 出勤時刻を直行とする
			integrationOfDaily.getWorkInformation().setGoStraightAtr(
					EnumAdaptor.valueOf(workTypeSet.isPresent() ? workTypeSet.get().getAttendanceTime().value : 0, NotUseAttribute.class));
			// note 退勤打刻自動セット ~ 退勤打刻自動セット
			integrationOfDaily.getWorkInformation().setBackStraightAtr(
					EnumAdaptor.valueOf(workTypeSet.isPresent() ? workTypeSet.get().getTimeLeaveWork().value : 0, NotUseAttribute.class));
			// note 勤務情報。勤務予定時間帯。勤務No = 取得した所定時間帯. 勤務NO
			// note 勤務情報。勤務予定時間帯。出勤 = 取得した所定時間帯. 開始
			// note 勤務情報。勤務予定時間帯。退勤 = 取得した所定時間帯. 終了
			integrationOfDaily.getWorkInformation().setScheduleTimeSheets(lstTimeZone.stream().map(
					mapper -> new ScheduleTimeSheet(mapper.getWorkNo(), mapper.getStart().v(), mapper.getEnd().v()))
					.collect(Collectors.toList()));
			// note 短時間勤務。時間帯。育児介護区分 = 取得した短時間勤務. 育児介護区分
			for (ShortWorkTimeDto shortWork : prepareWorkOutput.getLstWorkTimeDto()) {
				for (ShortChildCareFrameDto shortChild : shortWork.getLstTimeSlot()) {
					ShortWorkingTimeSheet timeSheet = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(shortChild.getTimeSlot()),
							EnumAdaptor.valueOf(shortWork.getChildCareAtr().value, ChildCareAttribute.class),
							shortChild.getStartTime(), shortChild.getEndTime());
					List<ShortWorkingTimeSheet> lstSheets = new ArrayList<>();
					lstSheets.add(timeSheet);
					ShortTimeOfDailyAttd shortTime = new ShortTimeOfDailyAttd(lstSheets);
					integrationOfDaily.setShortTime(Optional.ofNullable(shortTime));
					
				}
			}
			;

			// note 勤務予定から日別勤怠（Work）に変換する - TQP - đã thực hiện convert từ phía trên
			// note 編集状態あり
			if (!attendanceItemIdList.isEmpty()) {
				// note 手修正項目のデータを元に戻す - TQP
				// note 取得できた日別勤怠（Work）から勤務予定に変換する - TQP
				integrationOfDaily = this.restoreData(itemConverter, integrationOfDaily, listItemValue);
			}
			// note Đang để tạm 1 phần tử trong DailyRecordToAttendanceItemConverter nên Tín bảo
			// note để tạm là UNSETTLED
			WorkSchedule workSchedule = new WorkSchedule(integrationOfDaily.getEmployeeId(),
					integrationOfDaily.getYmd(), ConfirmedATR.UNSETTLED, integrationOfDaily.getWorkInformation(),
					integrationOfDaily.getAffiliationInfor(), integrationOfDaily.getBreakTime(),
					integrationOfDaily.getEditState(), integrationOfDaily.getAttendanceLeave(),
					integrationOfDaily.getAttendanceTimeOfDailyPerformance(), integrationOfDaily.getShortTime());

			// note 「処理状態」、「勤務予定」、「エラー」を返す - TQP
			// note // note 編集状態なし
			return new OutputCreateScheduleOneDate(workSchedule, null, ProcessingStatus.NORMAL_PROCESS);

		} else {
			// note 正常以外
			ScheduleErrorLog errorLog = null;
			switch (checkErrorCondition.value) {
			// note 勤務情報のエラー状態.勤務種類が削除された
			case 4: {
				String errorContent = this.internationalization.localize("Msg_590", "#Msg_590").get();
				errorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod, command.getEmployeeId());
				break;
			}
			// note 就業時間帯が不要なのに設定されている
			case 3: {
				String errorContent = this.internationalization.localize("Msg_434", "#Msg_434").get();
				errorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod, command.getEmployeeId());
				break;
			}
			// note 就業時間帯が必須なのに設定されていない
			case 2: {
				String errorContent = this.internationalization.localize("Msg_435", "#Msg_435").get();
				errorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod, command.getEmployeeId());
				break;
			}
			// note 就業時間帯が削除された
			case 5: {
				String errorContent = this.internationalization.localize("Msg_591", "#Msg_591").get();
				errorLog = new ScheduleErrorLog(errorContent, null, dateInPeriod, command.getEmployeeId());
				break;
			}
			}
			return new OutputCreateScheduleOneDate(null, errorLog, ProcessingStatus.valueOf(ProcessingStatus.NEXT_DAY_WITH_ERROR.value));
		}

	}
	
	/**
	 * 手修正項目のデータを元に戻す
	 * @param converter
	 * @param integrationOfDaily
	 * @param listItemValue
	 */
	public IntegrationOfDaily restoreData(DailyRecordToAttendanceItemConverter converter,
			IntegrationOfDaily integrationOfDaily, List<ItemValue> listItemValue) {
		converter.setData(integrationOfDaily);
		converter.merge(listItemValue);
		return converter.toDomain();
	}
	
	
	/**
	 * 勤務情報を取得する
	 * @param command
	 * @param creator
	 * @param domain
	 * @param context
	 * @param dateInPeriod
	 * @param masterCache
	 * @param listBasicSchedule
	 * @param dateRegistedEmpSche
	 * @param results
	 * @return
	 */
	public PrepareWorkOutput getWorkInfo(ScheduleCreatorExecutionCommand command, ScheduleCreator creator,
			ScheduleExecutionLog domain, CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod targetPeriod, GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule, DateRegistedEmpSche dateRegistedEmpSche,
			Map<GeneralDate, WorkInformation> results, CacheCarrier carrier) {
		String cid = command.getCompanyId();
		PrepareWorkOutput prepareWorkOutput = null;
		// note パラメータの勤務Mapを確認する
		// note データあり
		if (results.get(dateInPeriod) != null) {
			return new PrepareWorkOutput(results.get(dateInPeriod), null, null, Optional.empty());
		};
		Optional<ScheManaStatuTempo> optEmploymentInfo = Optional.empty();
		if (!masterCache.getListManaStatuTempo().isEmpty()) { // note lấy dữ liệu theo ngày
			optEmploymentInfo = masterCache.getListManaStatuTempo().stream()
					.filter(employmentInfo -> employmentInfo.getDate().equals(dateInPeriod)).findFirst();
		}
		// note データなし
		// note 社員の在職状態を確認する 
		// note if 休職中、休業中
		// note if 休職中
		if (optEmploymentInfo.get().getScheManaStatus() == ScheManaStatus.ON_LEAVE) {
			// note 休業区分の勤務種類コードを取得する(lấy dữ liệu worktype của 休業区分)
			// note Input 会社ID, 廃止区分=廃止しない, 勤務種類の分類＝休職, 勤務の単位 = 1日
			List<WorkType> lstWorkType = workTypeRepository.findWorkOneDay(cid,
					DeprecateClassification.NotDeprecated.value, WorkTypeUnit.OneDay.value,
					WorkTypeClassification.LeaveOfAbsence.value);
			// note Filter by 勤務の単位 = 1日
			lstWorkType = lstWorkType.stream()
					.map(x -> new WorkType(cid, x.getWorkTypeCode(), x.getWorkTypeSetList().stream()
							.filter(y -> y.getWorkAtr().value == WorkAtr.OneDay.value).collect(Collectors.toList())))
					.collect(Collectors.toList());
			if (lstWorkType.isEmpty()) {
				String errorContent = this.internationalization.localize("Msg_601", "#Msg_601").get();
				ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, command.getExecutionId(), dateInPeriod,
						command.getEmployeeId());
				// note ドメインモデル「スケジュール作成エラーログ」を返す
				return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
			} else {
				// note 勤務情報を返す
				// note ・勤務種類コード＝取得した勤務種類コード
				// note ・就業時間帯コード＝Null
				WorkInformation workInformation = new WorkInformation(null, lstWorkType.get(0).getWorkTypeCode());
				return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
			}

		}

		// note if 休業中
		if (optEmploymentInfo.get().getScheManaStatus() == ScheManaStatus.CLOSED) {
			// note 休業区分の勤務種類コードを取得する(lấy dữ liệu worktype của 休業区分)
			// note Input 会社ID, 廃止区分=廃止しない, 勤務種類の分類＝休業, 勤務の単位 = 1日
			List<WorkType> lstWorkType = workTypeRepository.findWorkOneDay(command.getCompanyId(),
					DeprecateClassification.NotDeprecated.value, WorkTypeUnit.OneDay.value,
					WorkTypeClassification.Closure.value);
			// note Filter by 勤務の単位 = 1日
			lstWorkType = lstWorkType.stream()
					.map(x -> new WorkType(cid, x.getWorkTypeCode(), x.getWorkTypeSetList().stream()
							.filter(y -> y.getCloseAtr().value == CloseAtr.PRENATAL.value
									&& y.getWorkAtr().value == WorkAtr.OneDay.value)
							.collect(Collectors.toList())))
					.collect(Collectors.toList());

			if (lstWorkType.isEmpty()) {
				String errorContent = this.internationalization.localize("Msg_601", "#Msg_601").get();
				ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, command.getExecutionId(), dateInPeriod,
						command.getEmployeeId());
				// note ドメインモデル「スケジュール作成エラーログ」を返す
				return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
			} else {
				// note 勤務情報を返す
				// note ・勤務種類コード＝取得した勤務種類コード
				// note ・就業時間帯コード＝Null
				WorkInformation workInformation = new WorkInformation(null, lstWorkType.get(0).getWorkTypeCode());
				return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
			}
		}

		// note if 予定管理する
		PrepareWorkOutput workOutput = new PrepareWorkOutput(null, null, null, Optional.empty());
		if (optEmploymentInfo.get().getScheManaStatus() == ScheManaStatus.SCHEDULE_MANAGEMENT) {
		// note 入力パラメータ「作成方法区分」を確認する	
		workOutput = this.personalScheduleCopy(command, carrier, targetPeriod, dateInPeriod, masterCache, prepareWorkOutput);
		}
		return workOutput;
	}
	
	/**
	 * 入力パラメータ「作成方法区分」を確認する (tiếp tục xử lý 勤務情報を取得する nhưng dài quá nên tách ra)
	 * @param command
	 * @param carrier
	 * @param targetPeriod
	 * @param dateInPeriod
	 * @param masterCache
	 * @param prepareWorkOutput
	 * @return
	 */
	private PrepareWorkOutput personalScheduleCopy(ScheduleCreatorExecutionCommand command, CacheCarrier carrier,
			DatePeriod targetPeriod, GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache,
			PrepareWorkOutput prepareWorkOutput) {
		// note 入力パラメータ「作成方法区分」を確認する
		// note đang để tạm
		// note if 個人スケジュールコピー
		if (command.getContent().getCreateMethodAtr().value == CreationMethodClassification.COPY_PAST_SCHEDULE.value) {
			this.copyPastSchedule(command, carrier, targetPeriod, dateInPeriod, masterCache, prepareWorkOutput);
		}

		// note đang để tạm chưa phải làm
		// note if 作成方法（参照先）
		// note	if(command.getClassification() == CreationMethodClassification.CREATE_METHOD_SPEC) {
		// note	パラメータ。作成参照先を確認する TQP
		// note	masterCache.getListWorkingConItem();
		// note	}

		// note đang để tạm
		// note if 個人情報
		if (command.getContent().getCreateMethodAtr().value == CreationMethodClassification.PERSONAL_INFO.value) {
			// note 「労働条件。予定作成方法。 基本作成方法」を確認する
			Optional<WorkCondItemDto> itemDto = masterCache.getListWorkingConItem().stream()
					.filter(x -> x.getDatePeriod().contains(dateInPeriod)).findFirst();
			// note call 営業日カレンダーで勤務予定作成する, 月間パターンで勤務予定を作成する, 個人曜日別で勤務予定作成する
			prepareWorkOutput = this.getPersonalInfo(itemDto, command, dateInPeriod, masterCache);
		}
		
		if(prepareWorkOutput.getExecutionLog().isPresent()) {
			return new PrepareWorkOutput(null, null, null, prepareWorkOutput.getExecutionLog());
		}
		WorkTypeCode worktypeCode = prepareWorkOutput.getInformation() == null ? null : prepareWorkOutput.getInformation().getWorkTypeCode();
		WorkTimeCode workTimeCode = prepareWorkOutput.getInformation() == null ? null : prepareWorkOutput.getInformation().getWorkTimeCode();

		// note 勤務種類一覧から変換した勤務種類コードと一致する情報を取得する
		List<WorkType> workTypes = masterCache.getListWorkType().stream().sorted(Comparator.comparing(WorkType::getWorkTypeCode)).collect(Collectors.toList());
		Optional<WorkType> workType = Optional.empty();
		if(worktypeCode != null) {
			workType = workTypes.stream()
				.filter(x -> x.getWorkTypeCode().v().equals(worktypeCode.v())).findFirst();
		}
		if (workType.isPresent()) {
			// note 就業時間帯一覧から変換した就業時間帯コードと一致する情報を取得する
			Optional<WorkTimeSetting> workTime = workTimeCode != null ? masterCache.getListWorkTimeSetting().stream()
					.filter(x -> x.getWorktimeCode().v().equals(workTimeCode.v())).findFirst() : Optional.empty();
			// note 就業時間帯コード＜＞Null AND就業時間帯を取得できない
			if (workTimeCode != null && !workTime.isPresent()) {
				// note スケジュール作成ログを作成して返す
				String errorContent = this.internationalization.localize("Msg_591", "#Msg_591").get();
				ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, null, dateInPeriod,
						command.getEmployeeId());
				return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
			}
			// note 勤務情報を返す
			WorkInformation workInformation = new WorkInformation(workTimeCode, worktypeCode);
			return new PrepareWorkOutput(workInformation, null, null, Optional.empty(), workType);

		}
		// note スケジュール作成ログを作成して返す
		String errorContent = this.internationalization.localize("Msg_590", "#Msg_590").get();
		ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, null, dateInPeriod, command.getEmployeeId());
		return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
	}
	
	/**
	 * 
	 * @param command
	 * @param carrier
	 * @param targetPeriod
	 * @param dateInPeriod
	 * @param masterCache
	 * @param prepareWorkOutput
	 * @return
	 */
	private PrepareWorkOutput copyPastSchedule(ScheduleCreatorExecutionCommand command, CacheCarrier carrier,
			DatePeriod targetPeriod, GeneralDate dateInPeriod, CreateScheduleMasterCache masterCache,
			PrepareWorkOutput prepareWorkOutput) {

		List<WorkSchedule> workScheduleRepo = new ArrayList<>();
		List<WorkSchedule> workSchedules = carrier.get("勤務予定", () -> new ArrayList<WorkSchedule>());
		// note 勤務予定をコピーして作成する
		// note コピー元勤務予定一覧キャッシュを確認する

		// note データなし
		if (workSchedules.isEmpty()) {

			int daysToAdd = targetPeriod.datesBetween().size() - 1;

			// note 勤務予定一覧を取得する
			// note コピー日数は、「入力パラメータ. 対象開始日、対象終了日」から求める
			DatePeriod dateCopy = new DatePeriod(command.getContent().getCopyStartDate(),
					command.getContent().getCopyStartDate().addDays(daysToAdd));
			for (GeneralDate date : dateCopy.datesBetween()) {
				Optional<WorkSchedule> schedule = workScheduleRepository.get(command.getEmployeeId(), date);
				if (schedule.isPresent()) {
					workScheduleRepo.add(schedule.get());
				}
			}

			if (workScheduleRepo.isEmpty()) {
				// note ドメインモデル「スケジュール作成エラーログ」を作成する
				String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
				ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, command.getExecutionId(), dateInPeriod,
						command.getEmployeeId());
				return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
			} else {

				// note 取得した勤務予定一覧をメモリにキャッシュする chưa làm
				workSchedules = carrier.get("勤務予定", () -> workScheduleRepo);
			}
		}
		// note コピー元対象日を計算する
		DatePeriod targetPeriodCopy = new DatePeriod(dateInPeriod, targetPeriod.start());
		GeneralDate dateTargetCopy = command.getContent().getCopyStartDate()
				.addDays(targetPeriodCopy.datesBetween().size() - 1);
		workSchedules = workSchedules.stream().filter(x -> x.getYmd().equals(dateTargetCopy))
				.collect(Collectors.toList());

		if (workSchedules.isEmpty()) {
			// note ドメインモデル「スケジュール作成エラーログ」を作成する
			String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
			ScheduleErrorLog scheExeLog = new ScheduleErrorLog(errorContent, command.getExecutionId(), dateInPeriod,
					command.getEmployeeId());
			return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheExeLog));
		} else {
			WorkInformation workInformation = new WorkInformation(
					workSchedules.get(0).getWorkInfo().getRecordInfo().getWorkTimeCode(),
					workSchedules.get(0).getWorkInfo().getRecordInfo().getWorkTypeCode());
			return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
		}
	}
	
	/**
	 * 
	 * @param itemDto
	 * @param command
	 * @param dateInPeriod
	 * @param masterCache
	 * @return
	 */
	private PrepareWorkOutput getPersonalInfo(Optional<WorkCondItemDto> itemDto, ScheduleCreatorExecutionCommand command, GeneralDate dateInPeriod,
			CreateScheduleMasterCache masterCache) {
		if (itemDto.isPresent() && itemDto.get().getScheduleMethod().isPresent()) {
			// note 営業日カレンダー
			if(itemDto.get().getScheduleMethod().get().getBasicCreateMethod() == WorkScheduleBasicCreMethod.BUSINESS_DAY_CALENDAR) {
				// note 営業日カレンダーで勤務予定作成する TQP
				// note パラメータ。作成参照先を確認する
				// note Emptyの場合
				// noteif(masterCache.getListWorkingConItem().isEmpty()) {
					
					// note  xử lý 「基本勤務設定」を取得する(lấy thông tin 「基本勤務設定」)
					Optional<BasicWorkSetting> basicWorkSetting = this.getBasicWorkSetting(command, masterCache, itemDto, dateInPeriod, 
							masterCache.getEmpGeneralInfo().getClassificationDto(), masterCache.getEmpGeneralInfo().getWorkplaceDto());
					
					// note 勤務種類一覧から勤務種類を取得する
					List<WorkType> lstWorkTypes = masterCache.getListWorkType();
					Optional<WorkType> workType = lstWorkTypes.stream().filter(x-> x.getWorkTypeCode().v().equals(basicWorkSetting.get().getWorktypeCode().v())).findFirst();
					
					// note 「就業時間帯コード」を取得する
					WorkingCode workTimeCode = this.getWorkingCode(command,masterCache, itemDto, basicWorkSetting.isPresent() ? basicWorkSetting.get().getWorkingCode() : null, workType.isPresent() ? workType.get() : null, dateInPeriod);
					
					// note 「勤務種類コード」、「就業時間帯コード」を返す
					WorkInformation workInformation =  new WorkInformation(workTimeCode == null ? null : workTimeCode.v(), workType.isPresent() ? workType.get().getWorkTypeCode().v() : null);
					return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
					
				// note} 
// note				else {
// note					// note Emptyじゃない場合 - phần này lần này chưa phải làm
// note					// note 基本勤務設定を取得する
// note				}
			}
			
			// note 月間パターン
			if(itemDto.get().getScheduleMethod().get().getBasicCreateMethod() == WorkScheduleBasicCreMethod.MONTHLY_PATTERN) {
				// note 月間パターンで勤務予定を作成する TQP
				// note 「労働条件項目．月間パターン」をチェックする
				// note Nullでない　場合
				if (itemDto.get().getMonthlyPattern().isPresent()) {

					// note ドメインモデル「月間勤務就業設定」を取得する
					Optional<WorkMonthlySetting> monthlySetting = workMonthlySettingRepository.findById(
							command.getCompanyId(), itemDto.get().getMonthlyPattern().get().v(), dateInPeriod);
					
					// note 対象日の「月間勤務就業設定」があるかチェックする
					// note 存在する場合
					if (monthlySetting.isPresent()) {
						
						// note 勤務種類一覧から勤務種類を取得する
						List<WorkType> lstWorkTypes = masterCache.getListWorkType();
						lstWorkTypes = lstWorkTypes.stream().filter(x-> x.getWorkTypeCode().v().equals(monthlySetting.get().getWorkTypeCode().v())).collect(Collectors.toList());
						Optional<WorkType> workType = lstWorkTypes.stream().filter(x-> x.getWorkTypeCode().v().equals(monthlySetting.get().getWorkTypeCode().v())).findFirst();
						
						// note 「就業時間帯コード」を取得する
						WorkingCode workTimeCode = this.getWorkingCode(command, masterCache, itemDto, new WorkingCode(monthlySetting.get().getWorkingCode().v()), workType.isPresent() ? workType.get() : null, dateInPeriod);
						
						WorkInformation workInformation =  new WorkInformation(workTimeCode != null ? workTimeCode.v() : null, workType.isPresent() ? workType.get().getWorkTypeCode().v() : null);
						return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
					}
				}
				// note Null の場合 - if !itemDto.get().getMonthlyPattern().isPresent()
				// note 存在しない場合 - if (!monthlySetting.isPresent())
				String errorContent = this.internationalization.localize("Msg_604", "#Msg_604").get();
				ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
						dateInPeriod, command.getEmployeeId());
				return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheduleErrorLog));
			}

			// note 個人曜日別 
			if(itemDto.get().getScheduleMethod().get().getBasicCreateMethod() == WorkScheduleBasicCreMethod.PERSONAL_DAY_OF_WEEK) {
				
				// note 個人曜日別で勤務予定作成する TQP
				// note 入力パラメータの「年月日」から「曜日」を求める
				DayOfWeek dayOfWeek = dateInPeriod.dayOfWeekEnum();
				
				// note 「労働条件。曜日別勤務」を確認する
				// note if 取得できない
				if (itemDto.get().getWorkDayOfWeek() == null || !this.checkDayOfWeek(itemDto.get().getWorkDayOfWeek(), dateInPeriod)) {
					
					// note ドメインモデル「スケジュール作成エラーログ」を返す
					String errorContent = this.internationalization.localize("Msg_594", "#Msg_594").get();
					ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, null,
							dateInPeriod, command.getEmployeeId());
					return new PrepareWorkOutput(null, null, null, Optional.ofNullable(scheduleErrorLog));
				} else {
					
					WorkInformation workInformation = null;
					// note 「労働条件。曜日別勤務」から「単一日勤務予定」を取得する
					Optional<SingleDaySchedule> daySchedule = this.getDaySchedule(itemDto, dayOfWeek);
				     // note データある
				    if (daySchedule.isPresent()) {
				    	workInformation =  new WorkInformation(daySchedule.get().getWorkTimeCode().get(), daySchedule.get().getWorkTypeCode().get());
				    } else {
				    	// note データがない
				    	// note 「個人勤務日区分別勤務」。休日時を取得する
				    	workInformation =  new WorkInformation(itemDto.get().getWorkCategory().getHolidayTime().getWorkTimeCode().isPresent() ? itemDto.get().getWorkCategory().getHolidayTime().getWorkTimeCode().get() : null, 
				    			itemDto.get().getWorkCategory().getHolidayTime().getWorkTypeCode().get());
				    }
				    return new PrepareWorkOutput(workInformation, null, null, Optional.empty());
				}
			}
		}
		return null;
	}
	
	public boolean checkDayOfWeek(PersonalDayOfWeek workDayOfWeek, GeneralDate date) {
		List<Optional<SingleDaySchedule>> schedules = new ArrayList<>();
		schedules.add(0, workDayOfWeek.getMonday());
		schedules.add(1, workDayOfWeek.getTuesday());
		schedules.add(2, workDayOfWeek.getWednesday());
		schedules.add(3, workDayOfWeek.getThursday());
		schedules.add(4, workDayOfWeek.getFriday());
		schedules.add(5, workDayOfWeek.getSaturday());
		schedules.add(6, workDayOfWeek.getSunday());
		
		if(schedules.get(date.dayOfWeek() - 1).isPresent()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param itemDto
	 * @param dayOfWeek
	 * @return
	 */
	private Optional<SingleDaySchedule> getDaySchedule(Optional<WorkCondItemDto> itemDto, DayOfWeek dayOfWeek){
		Optional<SingleDaySchedule> daySchedule = Optional.empty();
	     switch (dayOfWeek.value) {
           case 1: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getMonday();
               break;
           }
           case 2: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getTuesday();
               break;
           }
           case 3: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getWednesday();
               break;
           }
           case 4: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getThursday();
               break;
           }
           case 5: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getFriday();
               break;
           }
           case 6: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getSaturday();
               break;
           }
           case 7: {
           	daySchedule = itemDto.get().getWorkDayOfWeek().getSunday();
               break;
           }
	     }
	     
	     return daySchedule;
	}
	
	/**
	 * 
	 * @param command
	 * @param masterCache
	 * @param itemDto
	 * @param workingCode
	 * @param workType
	 * @param dateInPeriod
	 * @return
	 */
	private WorkingCode getWorkingCode(ScheduleCreatorExecutionCommand command,
			CreateScheduleMasterCache masterCache,Optional<WorkCondItemDto> itemDto, WorkingCode workingCode, WorkType workType, GeneralDate dateInPeriod) {
		WorkingCode workTimeCode = null;
		// note 入力パラメータ「就業時間帯の参照先」を判断(kiểm tra parameter 就業時間帯の参照先」)
		TimeZoneScheduledMasterAtr workplaceHistItem = itemDto.get().getScheduleMethod().get()
				.getWorkScheduleBusCal().get().getReferenceWorkingHours();
			
			// note if 個人勤務日別
			if(workplaceHistItem.value == TimeZoneScheduledMasterAtr.PERSONAL_WORK_DAILY.value) {
				
				// note 個人勤務日別をもとに「就業時間帯コード」を変換する - chưa tìm được thuật toán này
				TimeZoneScheduledMasterAtr referenceWorkingHours = itemDto.get().getScheduleMethod().get()
				.getWorkScheduleBusCal().get().getReferenceWorkingHours();
				ScheduleErrorLogGeterCommand logGeterCommand = new ScheduleErrorLogGeterCommand(command.getExecutionId(), command.getCompanyId(), dateInPeriod);
				WorkTimeConvertCommand timeConvertCommand = new WorkTimeConvertCommand(command.getEmployeeId(), logGeterCommand, referenceWorkingHours.value, workType == null ? null : workType.getWorkTypeCode().v(),workingCode == null ? null : workingCode.v());
				workTimeCode = new WorkingCode(workTimeHandler.getWorkTimeZoneCodeInOffice(timeConvertCommand, masterCache.getListWorkingConItem()));
				return workTimeCode;
			}
			
			// note if 個人曜日別
			if(workplaceHistItem.value == TimeZoneScheduledMasterAtr.PERSONAL_DAY_OF_WEEK.value) {
				// note 個人曜日別をもとに就業時間帯コードを変換する - chưa tìm được thuật toán này
				TimeZoneScheduledMasterAtr referenceWorkingHours = itemDto.get().getScheduleMethod().get()
						.getWorkScheduleBusCal().get().getReferenceWorkingHours();
						ScheduleErrorLogGeterCommand logGeterCommand = new ScheduleErrorLogGeterCommand(command.getExecutionId(), command.getCompanyId(), dateInPeriod);
						WorkTimeConvertCommand timeConvertCommand = new WorkTimeConvertCommand(command.getEmployeeId(), logGeterCommand, referenceWorkingHours.value, workType == null ? null : workType.getWorkTypeCode().v(), workingCode == null ? null : workingCode.v());
						workTimeCode = new WorkingCode(workTimeHandler.getWorkTimeZoneCodeInOfficeDayOfWeek(timeConvertCommand, masterCache.getListWorkingConItem()));
						return workTimeCode;
			}
			
		// note if マスタ参照区分に従う
		// note 入力パラメータ.就業時間帯コードを使う
			workTimeCode = workingCode;
		return workTimeCode;
	}
	
	/**
	 * 基本勤務設定を取得する
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定処理.作成処理.アルゴリズム.勤務予定処理.勤務予定作成する.勤務予定作成共通処理.個人情報をもとに勤務予定を作成する.営業日カレンダーで勤務予定作成する.基本勤務設定を取得する.基本勤務設定を取得する
	 * @param command ・実行ID  ・会社ID  ・社員ID
	 * @param masterSche 
	 * @param itemDto ・営業日カレンダーの参照先	・基本勤務の参照先
	 * @param dateInPeriod
	 * @param mapClassificationHist 特定期間の社員情報
	 * @param mapWorkplaceHist 特定期間の社員情報
	 * @return
	 */
	private Optional<BasicWorkSetting> getBasicWorkSetting(ScheduleCreatorExecutionCommand command, CreateScheduleMasterCache masterSche, Optional<WorkCondItemDto> itemDto, GeneralDate dateInPeriod,
			List<ExClassificationHistoryImported> mapClassificationHist,
			List<ExWorkPlaceHistoryImported> mapWorkplaceHist) {
		WorkScheduleMasterReferenceAtr workplaceHistItem = itemDto.get().getScheduleMethod().get()
				.getWorkScheduleBusCal().get().getReferenceBusinessDayCalendar();
		ScheduleErrorLogGeterCommand geterCommand = new ScheduleErrorLogGeterCommand(command.getExecutionId(),
				command.getCompanyId(), dateInPeriod);
		// note điều kiện 会社の場合 - Đang để tạm ntn vì enum 営業日カレンダーの参照先 chưa được update
		if(workplaceHistItem.value != WorkScheduleMasterReferenceAtr.WORK_PLACE.value && 
				workplaceHistItem.value != WorkScheduleMasterReferenceAtr.CLASSIFICATION.value) {
			// note xử lý ドメインモデル「会社営業日カレンダー日次」を取得する(lấy dữ liệu domain 「会社営業日カレンダー日次」)
			Optional<CalendarCompany> optionalCalendarCompany = this.calendarCompanyRepository
					.findCalendarCompanyByDate(command.getCompanyId(), dateInPeriod);
			
			// note xử lý ドメインモデル「全社基本勤務設定」を取得する
			Optional<CompanyBasicWork> optionalCompanyBasicWork = this.companyBasicWorkRepository
					.findById(command.getCompanyId(), optionalCalendarCompany.get().getWorkingDayAtr().value);
			
			// note if 取得できない
			if(!optionalCompanyBasicWork.isPresent()) {
				this.scheCreExeErrorLogHandler.addError(geterCommand, command.getEmployeeId(), "Msg_589");
				return Optional.empty();
			}
			BasicWorkSettingByClassificationGetterCommand settingByClassification = new BasicWorkSettingByClassificationGetterCommand(
					command.getEmployeeId(), geterCommand, null,
					optionalCalendarCompany.get().getWorkingDayAtr().value);
			Optional<BasicWorkSetting> basicWorkSetting = basicWorkSettingHandler
					.getBasicWorkSettingByClassification(settingByClassification);
			BasicWorkSetting setting = new BasicWorkSetting(basicWorkSetting.get().getWorktypeCode(), basicWorkSetting.get().getWorkingCode(), basicWorkSetting.get().getWorkdayDivision());
			return Optional.ofNullable(setting);
			
		} else {
		// note check 基本勤務の参照先 is 職場 (referenceBusinessDayCalendar is WORKPLACE)
		if (workplaceHistItem.value == WorkScheduleMasterReferenceAtr.WORK_PLACE.value) {

			// note EA No1683
			if (mapWorkplaceHist != null) {
				// note 「特定期間の社員情報。職場履歴一覧」から該当社員、該当日の職場情報を取得する
				List<ExWorkPlaceHistoryImported> lstWorkplaceHistItem = mapWorkplaceHist.stream().filter(predicate-> predicate.getEmployeeId().equals(command.getEmployeeId())).collect(Collectors.toList());
				
				List<ExWorkplaceHistItemImported> lstItem =	lstWorkplaceHistItem.get(0).getWorkplaceItems().stream().filter(x-> x.getPeriod().contains(dateInPeriod)).collect(Collectors.toList());
				ExWorkPlaceHistoryImported optWorkplaceHistItem = new ExWorkPlaceHistoryImported(command.getEmployeeId(),lstItem);
				if (optWorkplaceHistItem != null) {

					// note [No.571]職場の上位職場を基準職場を含めて取得する
					List<String> workplaceIds = this.scWorkplaceAdapter.getWorkplaceIdAndUpper(command.getCompanyId(),
							dateInPeriod, optWorkplaceHistItem.getWorkplaceItems().get(0).getWorkplaceId());

					// note 職場の稼働日区分を取得する
					WorkdayAttrByWorkplaceGeterCommand workdayDivisions = new WorkdayAttrByWorkplaceGeterCommand(
							command.getEmployeeId(), new ScheduleErrorLogGeterCommand(command.getExecutionId(),
									command.getCompanyId(), dateInPeriod),
							workplaceIds);

					workdayDivisions.setWorkplaceIds(workplaceIds);
					// note return basic work setting
					Optional<Integer> workdayDivision = basicWorkSettingHandler
							.getWorkdayDivisionByWkp(workdayDivisions);
					
					// note xử lý 職場の基本勤務設定を取得する
					BasicWorkSettingByWorkplaceGetterCommand commandGetter = new BasicWorkSettingByWorkplaceGetterCommand(
							command.getEmployeeId(), geterCommand, workplaceIds, workdayDivision.get());
					Optional<BasicWorkSetting> basicWorkSetting = basicWorkSettingHandler
							.getBasicWorkSettingByWorkplace(commandGetter);

					// note 取得した「基本勤務設定」を返す
					return basicWorkSetting;
				}
			}
			// note add log error employee => 602
			// note 取得できない
			this.scheCreExeErrorLogHandler.addError(geterCommand, command.getEmployeeId(), "Msg_602", "#Com_Workplace");

		} else {
			// note if 分類の場合
			// note xử lý 営業日カレンダーの参照先 is 分類
			// note referenceBusinessDayCalendar is CLASSIFICATION
			if (mapClassificationHist != null) {
				// note xử lý 「特定期間の社員情報。雇用履歴一覧」から該当社員、該当日の分類情報を取得する
				Optional<ExClassificationHistoryImported> optClassificationHistItem = mapClassificationHist.stream()
						.filter(predicate-> predicate.getEmployeeId().equals(command.getEmployeeId()))
						.map(x -> new ExClassificationHistoryImported(command.getEmployeeId(),
								x.getClassificationItems().stream().filter(
										y -> y.getPeriod().contains(dateInPeriod))
										.collect(Collectors.toList())))
						.findFirst();
				// note if 取得できる
				if (optClassificationHistItem.isPresent()) {
					// note xử lý 分類の稼働日区分を取得する
					WorkdayAttrByClassGetterCommand baseGetter = new WorkdayAttrByClassGetterCommand(
							command.getEmployeeId(), geterCommand,
							optClassificationHistItem.get().getClassificationItems().get(0).getClassificationCode());
					Optional<Integer> workdayDivision = basicWorkSettingHandler.getWorkdayDivisionByClass(baseGetter);
					// note gọi đến ドメインモデル「分類基本勤務設定」を取得する
					Optional<ClassificationBasicWork> optionalClassificationBasicWork = this.classificationBasicWorkRepository
							.findById(command.getCompanyId(), baseGetter.getClassificationCode(),
									workdayDivision.get());

					// note if 取得できない
					if (!optionalClassificationBasicWork.isPresent()) {
						// note xử lý ドメインモデル「全社基本勤務設定」を取得する
						Optional<CompanyBasicWork> optionalCompanyBasicWork = this.companyBasicWorkRepository
								.findById(command.getCompanyId(), workdayDivision.get());

						// note if 取得できない
						if (!optionalCompanyBasicWork.isPresent()) {
							this.scheCreExeErrorLogHandler.addError(geterCommand, command.getEmployeeId(), "Msg_589");
							return Optional.empty();
						}
					}

					// note 取得できる
					// note 取得した「基本勤務設定」を返す
					BasicWorkSettingByClassificationGetterCommand settingByClassification = new BasicWorkSettingByClassificationGetterCommand(
							command.getEmployeeId(), geterCommand, baseGetter.getClassificationCode(),
							workdayDivision.get());
					Optional<BasicWorkSetting> basicWorkSetting = basicWorkSettingHandler
							.getBasicWorkSettingByClassification(settingByClassification);
					return basicWorkSetting;
				}
			}
			// note add log error employee => 602
			// note 取得できない
			this.scheCreExeErrorLogHandler.addError(geterCommand, command.getEmployeeId(), "Msg_602", "#Com_Class");
		}}
		// note return default optional
		return Optional.empty();
	}

	/**
	 * Creates the work schedule by recreate.
	 * 
	 * @param command
	 * @param basicSchedule
	 * @param workingConditionItem
	 * @param optEmploymentInfo
	 * @param empGeneralInfo
	 * @param mapEmploymentStatus
	 * @param listWorkingConItem
	 */
	private void createWorkScheduleByRecreate(ScheduleCreatorExecutionCommand command, GeneralDate dateInPeriod,
			BasicSchedule basicSchedule, WorkCondItemDto workingConditionItem, EmploymentInfoImported employmentInfo,
			CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {
		// note 入力パラメータ「再作成区分」を判断 - check parameter ReCreateAtr onlyUnconfirm
		// note 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
		// note (kiểm tra thông tin 「予定確定区分」 của domain 「勤務予定基本情報」)
		if (command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ALL_CASE
				|| basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {
			// note アルゴリズム「スケジュール作成判定処理」を実行する
			if (this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command, dateInPeriod,
					basicSchedule, employmentInfo, workingConditionItem, masterCache)) {
				this.scheCreExeWorkTypeHandler.createWorkSchedule(command, dateInPeriod, workingConditionItem,
						masterCache, listBasicSchedule, dateRegistedEmpSche);
			}
		}
	}
	
	private EmployeeGeneralInfoImport convertEmployeeGeneral(CreateScheduleMasterCache masterCache) {
		EmployeeGeneralInfoImport generalInfoImport = new EmployeeGeneralInfoImport(
				masterCache.getEmpGeneralInfo().getEmploymentDto().stream()
						.map(mapper -> new ExEmploymentHistoryImport(mapper.getEmployeeId(),
								mapper.getEmploymentItems().stream()
										.map(x -> new ExEmploymentHistItemImport(x.getHistoryId(), x.getPeriod(),
												x.getEmploymentCode()))
										.collect(Collectors.toList())))
						.collect(Collectors.toList()),
				masterCache.getEmpGeneralInfo().getClassificationDto().stream()
						.map(mapper -> new ExClassificationHistoryImport(mapper.getEmployeeId(),
								mapper.getClassificationItems().stream()
										.map(x -> new ExClassificationHistItemImport(x.getHistoryId(),
												x.getPeriod(), x.getClassificationCode()))
										.collect(Collectors.toList())))
						.collect(Collectors.toList()),
				masterCache.getEmpGeneralInfo().getJobTitleDto().stream()
						.map(mapper -> new ExJobTitleHistoryImport(mapper.getEmployeeId(),
								mapper.getJobTitleItems().stream()
										.map(x -> new ExJobTitleHistItemImport(x.getHistoryId(), x.getPeriod(),
												x.getJobTitleId()))
										.collect(Collectors.toList())))
						.collect(Collectors.toList()),
				masterCache.getEmpGeneralInfo().getWorkplaceDto().stream()
						.map(mapper -> new ExWorkPlaceHistoryImport(mapper.getEmployeeId(),
								mapper.getWorkplaceItems().stream()
										.map(x -> new ExWorkplaceHistItemImport(x.getHistoryId(), x.getPeriod(),
												x.getWorkplaceId()))
										.collect(Collectors.toList())))
						.collect(Collectors.toList()),
				masterCache.getListBusTypeOfEmpHis().stream()
						.map(mapper -> new ExWorkTypeHistoryImport(mapper.getCompanyId(), mapper.getEmployeeId(),mapper.getHistoryId(),
								new DatePeriod(mapper.getStartDate(), mapper.getEndDate()),
								mapper.getBusinessTypeCd()))
						.collect(Collectors.toList()));
		return generalInfoImport;
		
	}
	
	@AllArgsConstructor
	public static class WorkInformationImpl implements WorkInformation.Require{

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
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd,
				String workTypeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
		
	}

}

