package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.BasicScheduleResetCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.CalculationCache;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicScheduleHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeMonthlyPatternHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTypeHandler;
import nts.uk.ctx.at.schedule.dom.adapter.employmentstatus.EmploymentInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.executionlog.CreateMethodAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ProcessExecutionAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.DateRegistedEmpSche;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.RegistrationListDateSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmpDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
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
//
//	/** The schedule execution log repository. */
//	@Inject
//	private ScheduleExecutionLogRepository scheduleExecutionLogRepository;
	
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
	
	public void execute(ScheduleCreatorExecutionCommand command, ScheduleExecutionLog scheduleExecutionLog,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context, String companyId, String exeId,
			DatePeriod period, CreateScheduleMasterCache masterCache, List<BasicSchedule> listBasicSchedule,
			final nts.arc.layer.app.command.AsyncCommandHandlerContext<nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand> asyncTask,
			Object companySetting, ScheduleCreator scheduleCreator) {
		RegistrationListDateSchedule registrationListDateSchedule = new RegistrationListDateSchedule(new ArrayList<>());
	
		ScheduleCreateContent content = command.getContent();
		
		if (masterCache.getListWorkingConItem().size() > 1) {
			// 労働条件が途中で変化するなら、計算キャッシュは利用しない
			// do not cache result of calculation if working condition of the employee is changed in the period
			CalculationCache.clear();
		} else {
			CalculationCache.initialize();
		}
		try {
			this.createSchedule(command, scheduleExecutionLog, context, period, masterCache, listBasicSchedule,
					companySetting, scheduleCreator, registrationListDateSchedule, content);
		} finally {
			CalculationCache.clear();
		}

		scheduleCreator.updateToCreated();
		this.scheduleCreatorRepository.update(scheduleCreator);

		// 暫定データを作成する (Tạo data tạm)
		registrationListDateSchedule.getRegistrationListDateSchedule().forEach(x -> {
			// アルゴリズム「暫定データの登録」を実行する(Thực hiện thuật toán [đăng ký data tạm]) 
			this.interimRemainDataMngRegisterDateChange.registerDateChange(companyId, x.getEmployeeId(), x.getListDate());
		});
		
	}

	private void createSchedule(
			ScheduleCreatorExecutionCommand command,
			ScheduleExecutionLog scheduleExecutionLog,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod period,
			CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule,
			Object companySetting,
			ScheduleCreator scheduleCreator,
			RegistrationListDateSchedule registrationListDateSchedule,
			ScheduleCreateContent content) {
		
		// 実施区分を判断, 処理実行区分を判断
		// EA No2115
		if (content.getImplementAtr() == ImplementAtr.RECREATE
				&& content.getReCreateContent().getProcessExecutionAtr() == ProcessExecutionAtr.RECONFIG) {
			BasicScheduleResetCommand commandReset = BasicScheduleResetCommand.create(
					command, companySetting, scheduleCreator, content);
			// スケジュールを再設定する (Thiết lập lại schedule)
			this.resetScheduleWithMultiThread(
					commandReset,
					context,
					period,
					masterCache.getEmpGeneralInfo(),
					masterCache.getListBusTypeOfEmpHis(),
					listBasicSchedule,
					registrationListDateSchedule);
		} else {
			// 入力パラメータ「作成方法区分」を判断-check parameter
			// CreateMethodAtr
			if (content.getCreateMethodAtr() == CreateMethodAtr.PERSONAL_INFO) {
				command.setCompanySetting(companySetting);
				this.createScheduleBasedPersonWithMultiThread(
						command,
						scheduleCreator,
						scheduleExecutionLog,
						context,
						period,
						masterCache,
						listBasicSchedule,
						registrationListDateSchedule);
			}
		}
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
	// スケジュールを再設定する
	private void resetScheduleWithMultiThread(BasicScheduleResetCommand command,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod targetPeriod,
			EmployeeGeneralInfoImported empGeneralInfo, List<BusinessTypeOfEmpDto> listBusTypeOfEmpHis,
			List<BasicSchedule> listBasicSchedule, RegistrationListDateSchedule registrationListDateSchedule) {
		
		// get info by context
		val asyncTask = context.asAsync();
		
		DateRegistedEmpSche dateRegistedEmpSche = new DateRegistedEmpSche(command.getEmployeeId(), new ArrayList<>());
		// loop start period date => end period date
		for(val toDate : targetPeriod.datesBetween()) {
			// 中断フラグを判断
			if (asyncTask.hasBeenRequestedToCancel()) {
				// ドメインモデル「スケジュール作成実行ログ」を更新する
				// TODO - hinh nhu chua lam
				asyncTask.finishedAsCancelled();
				break;
			}
			// ドメインモデル「勤務予定基本情報」を取得する
			// fix for response
			Optional<BasicSchedule> optionalBasicSchedule = listBasicSchedule.stream().filter(
					x -> (x.getEmployeeId().equals(command.getEmployeeId()) && x.getDate().compareTo(toDate) == 0))
					.findFirst();
			if (optionalBasicSchedule.isPresent()) {
				command.setWorkingCode(optionalBasicSchedule.get().getWorkTimeCode());
				command.setWorkTypeCode(optionalBasicSchedule.get().getWorkTypeCode());
				// 入力パラメータ「再作成区分」を判断
				// 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
				if (command.getReCreateAtr() == ReCreateAtr.ALL_CASE.value
						|| optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.UNSETTLED) {
					// 再設定する情報を取得する
					this.scheCreExeBasicScheduleHandler.resetAllDataToCommandSave(command, toDate, empGeneralInfo,
							listBusTypeOfEmpHis, listBasicSchedule, dateRegistedEmpSche);
				}
			}
		}
		
		if(dateRegistedEmpSche.getListDate().size() > 0){
			registrationListDateSchedule.getRegistrationListDateSchedule().add(dateRegistedEmpSche);
		}
	}
	
	/**
	 * tra ve true la muon ket thuc vong lap
	 * tra ve false la k chay cac xu ly ben duoi, sang object tiep theo
	 * 
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
	private boolean createScheduleBasedPersonOneDate(
			ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator,
			ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			GeneralDate dateInPeriod,
			CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {
		
		// 「社員の在職状態」から該当社員、該当日の在職状態を取得する
		// EA修正履歴　No2716
		List<EmploymentInfoImported> listEmploymentInfo = masterCache.getMapEmploymentStatus().get(creator.getEmployeeId());
		Optional<EmploymentInfoImported> optEmploymentInfo = Optional.empty();
		if (listEmploymentInfo != null) {
			optEmploymentInfo = listEmploymentInfo.stream()
					.filter(employmentInfo -> employmentInfo.getStandardDate().equals(dateInPeriod)).findFirst();
		}
		// status employment equal RETIREMENT (退職)
		if (!optEmploymentInfo.isPresent() || optEmploymentInfo.get().getEmploymentState() == ScheduleCreatorExecutionCommandHandler.RETIREMENT) {
			return true;
		}
		EmploymentInfoImported employmentInfo = optEmploymentInfo.get();
		// status employment equal BEFORE_JOINING (入社前)
		if (employmentInfo.getEmploymentState() == ScheduleCreatorExecutionCommandHandler.BEFORE_JOINING) {
			return false;
		}
		
		// 労働条件情報からパラメータ.社員ID、ループ中の対象日から該当する労働条件項目を取得する
		// EA修正履歴 No1830
		Optional<WorkCondItemDto> _workingConditionItem = masterCache.getListWorkingConItem().stream().filter(
				x -> x.getDatePeriod().contains(dateInPeriod) && creator.getEmployeeId().equals(x.getEmployeeId()))
				.findFirst();

		if (!_workingConditionItem.isPresent()) {
			String errorContent = this.internationalization.localize("Msg_602", "#KSC001_87").get();
			// ドメインモデル「スケジュール作成エラーログ」を登録する
			ScheduleErrorLog scheduleErrorLog = new ScheduleErrorLog(errorContent, command.getExecutionId(),
					dateInPeriod, creator.getEmployeeId());
			this.scheduleErrorLogRepository.add(scheduleErrorLog);
			return false; 
		}

		WorkCondItemDto workingConditionItem = _workingConditionItem.get();

		if (workingConditionItem.getScheduleManagementAtr() == ManageAtr.NOTUSE) {
			return false;
		}

		if (!workingConditionItem.getScheduleMethod().isPresent()) {
			return false;
		}

		WorkScheduleBasicCreMethod basicCreateMethod = workingConditionItem.getScheduleMethod().get()
				.getBasicCreateMethod();
		switch (basicCreateMethod) {
		case BUSINESS_DAY_CALENDAR:
			// アルゴリズム「営業日カレンダーで勤務予定を作成する」を実行する
			this.createWorkScheduleByBusinessDayCalenda(
					command,
					dateInPeriod,
					workingConditionItem,
					masterCache,
					listBasicSchedule,
					dateRegistedEmpSche,
					employmentInfo);
			return false;
		case MONTHLY_PATTERN:
			// アルゴリズム「月間パターンで勤務予定を作成する」を実行する
			// create schedule by monthly pattern
			this.scheCreExeMonthlyPatternHandler.createScheduleWithMonthlyPattern(
					command,
					dateInPeriod,
					workingConditionItem,
					masterCache,
					listBasicSchedule,
					dateRegistedEmpSche,
					employmentInfo);
			return false;
		case PERSONAL_DAY_OF_WEEK:
			// アルゴリズム「個人曜日別で勤務予定を作成する」を実行する
			// TODO
			// 対象外
			return false;
		default:
			return false;
		}
		
	}

	/**
	 * 個人情報をもとにスケジュールを作成する-Creates the schedule based person.
	 * 
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
	private void createScheduleBasedPersonWithMultiThread(
			ScheduleCreatorExecutionCommand command,
			ScheduleCreator creator,
			ScheduleExecutionLog domain,
			CommandHandlerContext<ScheduleCreatorExecutionCommand> context,
			DatePeriod targetPeriod,
			CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule,
			RegistrationListDateSchedule registrationListDateSchedule) {

//		ExecutorService executorService = Executors.newFixedThreadPool(20);
//		CountDownLatch countDownLatch = new CountDownLatch(betweenDates.size());
		DateRegistedEmpSche dateRegistedEmpSche = new DateRegistedEmpSche(creator.getEmployeeId(), new ArrayList<>());
		targetPeriod.datesBetween().forEach(dateInPeriod -> {
//			AsyncTask task = AsyncTask.builder().withContexts().keepsTrack(false).threadName(this.getClass().getName())
//					.build(() -> {
						boolean isEndLoop = this.createScheduleBasedPersonOneDate(
								command,
								creator,
								domain,
								context,
								dateInPeriod,
								masterCache,
								listBasicSchedule,
								dateRegistedEmpSche);
						if(isEndLoop) return;
						// // Count down latch.
//						countDownLatch.countDown();
		});

		if(dateRegistedEmpSche.getListDate().size() > 0){
			registrationListDateSchedule.getRegistrationListDateSchedule().add(dateRegistedEmpSche);
		}
//			executorService.submit(task);
//		});
//
//		// Wait for latch until finish.
//		try {
//			countDownLatch.await();
//		} catch (InterruptedException ie) {
//			throw new RuntimeException(ie);
//		} finally {
//			// // Force shut down executor services.
//			executorService.shutdown();
//		}

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

		// ドメインモデル「勤務予定基本情報」を取得する(lấy dữ liệu domain 「勤務予定基本情報」)
		// fix for response
		Optional<BasicSchedule> optionalBasicSchedule = listBasicSchedule.stream()
				.filter(x -> (x.getEmployeeId().equals(workingConditionItem.getEmployeeId())
						&& x.getDate().compareTo(dateInPeriod) == 0))
				.findFirst();

		if (optionalBasicSchedule.isPresent()) {
			BasicSchedule basicSchedule = optionalBasicSchedule.get();
			// checked2018
			// 登録前削除区分をTrue（削除する）とする
			command.setIsDeleteBeforInsert(true); // FIX BUG #87113
			// check parameter implementAtr recreate (入力パラメータ「実施区分」を判断)
			// 入力パラメータ「実施区分」を判断(kiểm tra parameter 「実施区分」)
			if (command.getContent().getImplementAtr().value == ImplementAtr.RECREATE.value) {
				this.createWorkScheduleByRecreate(command, dateInPeriod, basicSchedule, workingConditionItem,
						employmentInfo, masterCache, listBasicSchedule, dateRegistedEmpSche);
			}
		} else {
			// EA No1841
			ScheMasterInfo scheMasterInfo = new ScheMasterInfo(null);
			BasicSchedule basicSche = new BasicSchedule(null, scheMasterInfo);
			if (ImplementAtr.RECREATE == command.getContent().getImplementAtr()
					&& !this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command, dateInPeriod,
							basicSche, employmentInfo, workingConditionItem, masterCache)) {
				return;
			}

			// 登録前削除区分をTrue（削除する）とする
			// checked2018
			command.setIsDeleteBeforInsert(false); // FIX BUG #87113

			// not exist data basic schedule
			this.scheCreExeWorkTypeHandler.createWorkSchedule(command, dateInPeriod, workingConditionItem, masterCache,
					listBasicSchedule, dateRegistedEmpSche);
		}
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
	private void createWorkScheduleByRecreate(
			ScheduleCreatorExecutionCommand command,
			GeneralDate dateInPeriod,
			BasicSchedule basicSchedule,
			WorkCondItemDto workingConditionItem,
			EmploymentInfoImported employmentInfo,
			CreateScheduleMasterCache masterCache,
			List<BasicSchedule> listBasicSchedule,
			DateRegistedEmpSche dateRegistedEmpSche) {
		// 入力パラメータ「再作成区分」を判断 - check parameter ReCreateAtr onlyUnconfirm
		// 取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
		// (kiểm tra thông tin 「予定確定区分」 của domain 「勤務予定基本情報」)
		if (command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ALL_CASE
				|| basicSchedule.getConfirmedAtr().equals(ConfirmedAtr.UNSETTLED)) {
			// アルゴリズム「スケジュール作成判定処理」を実行する
			if (this.scheCreExeMonthlyPatternHandler.scheduleCreationDeterminationProcess(command, dateInPeriod,
					basicSchedule, employmentInfo, workingConditionItem, masterCache)) {
				this.scheCreExeWorkTypeHandler.createWorkSchedule(
						command,
						dateInPeriod,
						workingConditionItem,
						masterCache,
						listBasicSchedule,
						dateRegistedEmpSche);
			}
		}
	}



}
