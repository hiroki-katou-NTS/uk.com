/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeBasicScheduleHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.ScheCreExeWorkTimeHandler;
import nts.uk.ctx.at.schedule.app.command.executionlog.internal.WorkTimeSetGetterCommand;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;;

/**
 * The Class ScheBatchCorrectExecutionCommandHandler.
 */
@Stateful
public class ScheBatchCorrectExecutionCommandHandler
		extends AsyncCommandHandler<ScheBatchCorrectSetCheckSaveCommand> {
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;
	
	/** The closure repository */
	@Inject
	private ClosureRepository closureRepository;
	
	/** The sc employment status adapter. */
	@Inject
	private ScEmploymentStatusAdapter scEmploymentStatusAdapter;
	
	/** The closure employment repository */
	@Inject
	private ClosureEmploymentRepository closureEmployment;
	
	/** The ScheCreExeWorkTimeHandler */
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	
	/** The ScheCreExeBasicScheduleHandler  */
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;
	
	/** The employee adapter*/
	@Inject
	private SCEmployeeAdapter scEmployeeAdapter;
	
	 /**  The employment adapter. */
	@Inject
	private ScEmploymentAdapter employmentAdapter;
	
	/** The Constant NEXT_DAY_MONTH. */
	private static final int NEXT_DAY_MONTH = 1;
	
	/** The Constant PREV_MONTH. */
	private static final int PREV_DAY_MONTH = -1;
	
	/** The Constant NUMBER_OF_SUCCESS. */
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	
	/** The Constant NUMBER_OF_ERROR. */
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	
	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;
	
	/** The Constant DAY_ONE. */
	private static final int DAY_ONE = 1;
	
	/** The Constant DATA_PREFIX. */
	private static final String DATA_PREFIX = "DATA_";
	
	/** The Constant MAX_ERROR_RECORD. */
	private static final int MAX_ERROR_RECORD = 5;
	
	/** Interrupt flag */
	private static boolean isInterrupted = false;
			
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.AsyncCommandHandler#handle(nts.arc.layer.app.
	 * command.CommandHandlerContext)
	 */
	@Override
	public void handle(CommandHandlerContext<ScheBatchCorrectSetCheckSaveCommand> context) {
		
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		ScheBatchCorrectSetCheckSaveCommand command = context.getCommand();

		// setup data object
		PerScheBatchCorrectProcessDto dto = new PerScheBatchCorrectProcessDto();
		dto.setEndTime(GeneralDateTime.now());
		dto.setStartTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.PROCESSING);
		
		// Creating list of errors
		List<ErrorContentDto> errorList = new ArrayList<>();
		
		dto.setErrors(errorList);
		dto.setWithError(WithError.NO_ERROR);
		
		int countSuccess = DEFAULT_VALUE;
		
		// Variable to count amount of list of error records sent to DB
		int errorRecordCount = DEFAULT_VALUE;
		
		setter.setData(NUMBER_OF_SUCCESS, countSuccess);
		setter.setData(NUMBER_OF_ERROR, DEFAULT_VALUE);
		
		// Set interrupt flag to false to start execution
		isInterrupted = false;
		
		// 選択されている社員ループ
		for (String employeeId : command.getEmployeeIds()) {
			// Stop if being interrupted
			if (isInterrupted) {
				break;
			}
			
			GeneralDate startDate = command.getStartDate();
			GeneralDate endDate = command.getEndDate();
			 	
			EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
			
			GeneralDate currentDateCheck = startDate;
			// 開始日から終了日までループ
			while (currentDateCheck.compareTo(endDate) <= 0) {
				Optional<String> optErrorMsg = registerProcess(companyId, command, employeeId, currentDateCheck);
				
				// Stop if being interrupted
				if (isInterrupted) {
					break;
				}
				
				if (optErrorMsg.isPresent()) {
					
					// Create and add error content to the errors list
					ErrorContentDto errorContentDto = new ErrorContentDto();
					errorContentDto.setMessage(optErrorMsg.get());
					errorContentDto.setEmployeeCode(employeeDto.getEmployeeCode());
					errorContentDto.setEmployeeName(employeeDto.getEmployeeName());
					errorContentDto.setDateYMD(currentDateCheck);
					
					// Add to error list (save to DB every 5 error records)
					if (errorList.size() >= MAX_ERROR_RECORD) {
						errorRecordCount++;
						setter.setData(DATA_PREFIX + errorRecordCount, dto);
						
						// Clear the list for the new batch of error record
						errorList.clear();
					}
					errorList.add(errorContentDto);
					setter.updateData(NUMBER_OF_ERROR, errorList.size()); // update the number of errors
					if (errorList.size() == 1) dto.setWithError(WithError.WITH_ERROR); // if there is even one error, output it
				} else {
					countSuccess++;
					setter.updateData(NUMBER_OF_SUCCESS, countSuccess);
				}
				//setter.updateData(DATA_EXECUTION, dto);
				
				// Add 1 more day to current day
				currentDateCheck = currentDateCheck.nextValue(true);
			}
		}
		
		// Send the last batch of errors if there is still records unsent
		if (!errorList.isEmpty()) {
			errorRecordCount++;
			setter.setData(DATA_PREFIX + errorRecordCount, dto);
		}
		
		dto.setEndTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.DONE);
		//setter.updateData(DATA_EXECUTION, dto);
	}
	
	public void interrupt() {
		isInterrupted = true;
	}
	
	/**
	 * 日付の存在チェック
	 * @param yearMonth the year month
	 * @param date the date
	 * @return calculated closure date
	 */
	private GeneralDate checkClosureDateTime(YearMonth yearMonth, int date) {
		GeneralDate startDate;
		// Checking by try creating a GeneralDate with calculated date
		// パラメータ「年月」にパラメータ「日」が存在するかチェック
		try {
			startDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), date);
		} catch (DateTimeException e) {
			// 存在しない場合
			startDate = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1).addDays(PREV_DAY_MONTH);
		}
		return startDate;
	}
	
	/**
	 * Next day.
	 *
	 * @param day the day
	 * @return the general date
	 */
	public GeneralDate nextDay(GeneralDate day) {
		return day.addDays(NEXT_DAY_MONTH);
	}

	/**
	 * Register process.
	 */
	// 登録処理
	private Optional<String> registerProcess(String companyId, ScheBatchCorrectSetCheckSaveCommand command, String employeeId, GeneralDate baseDate){
		
		// call check schedule update
		Optional<String> optionalMessage = this.getCheckScheduleUpdate(companyId, employeeId, baseDate);
		
		WorkTimeSetGetterCommand workTimeSetGetterCommand = new WorkTimeSetGetterCommand();
		workTimeSetGetterCommand.setCompanyId(companyId);
		workTimeSetGetterCommand.setWorktypeCode(command.getWorktypeCode());
		workTimeSetGetterCommand.setWorkingCode(command.getWorktimeCode());
		
		// check exist error
		if(!optionalMessage.isPresent()){
			// 勤務予定時間帯を補正する
			Optional<PrescribedTimezoneSetting> optPrescribedSetting = scheCreExeWorkTimeHandler.getScheduleWorkHour(workTimeSetGetterCommand);
			// call repository find basic schedule by id
			Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(employeeId, baseDate);
			
			// 登録メイン処理
			scheCreExeBasicScheduleHandler.registerBasicScheduleSaveCommand(optionalBasicSchedule, optPrescribedSetting, workTimeSetGetterCommand, 
					employeeId, baseDate);
		}
		else {
			return optionalMessage;
		}
		return Optional.empty();
	}
	
	/**
	 * Check closing period (締め期間チェック)
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public Optional<String> checkClosePeriod(String companyId, String employeeId, GeneralDate baseDate) {
		/**
		 * 特定の日付の締めを取得する
		 */
		// Imported（就業）「所属雇用履歴」を取得する
		Optional<EmploymentHistoryImported> employmentHisOptional = this.employmentAdapter.getEmpHistBySid(companyId, employeeId, baseDate);
		
		// 所属雇用履歴が取得できなかった場合
		if (!employmentHisOptional.isPresent()) {
			return Optional.of("Msg_303"); 
		}
		
		// ドメインモデル「雇用に紐づく就業締め」を取得する
		Optional<ClosureEmployment> optionalClosureEmployment = closureEmployment.findByEmploymentCD(companyId, employmentHisOptional.get().getEmploymentCode());
		
//		if (!optionalClosureEmployment.isPresent()) {
//			return Optional.of("Msg_557");
//		}
		
		// ドメインモデル「締め」を取得する
		Optional<Closure> optionalClosure = closureRepository.findById(companyId, optionalClosureEmployment.get().getClosureId());
		
//		if (!optionalClosure.isPresent()) {
//			return Optional.of("Msg_557");
//		}
		
		/** 
		 * 当月の期間を算出する
		 */
		// ドメインモデル「締め変更履歴」取得
		Optional<ClosureHistory> optionalClosureHistory = closureRepository.findBySelectedYearMonth(companyId, optionalClosure.get().getClosureId().value, 
				optionalClosure.get().getClosureMonth().getProcessingYm().v());
		
		// 締め期間
		GeneralDate startDate;
		GeneralDate endDate;
		
		// 締め変更履歴と当月をチェックする
//		if (optionalClosureHistory.get().getStartYearMonth().compareTo(optionalClosure.get().getClosureMonth().getProcessingYm()) != 0) {
			// 当月　≠　締め変更履歴．開始年月
			
			// 日付の末日とするをチェック
			if (optionalClosureHistory.get().getClosureDate().getLastDayOfMonth()) { // 末日である 
				YearMonth closureYearMonth = optionalClosure.get().getClosureMonth().getProcessingYm();
				
				// First day of current month
				startDate = GeneralDate.ymd(closureYearMonth.year(), closureYearMonth.month(), 1);
				
				// Add 1 more month then find the previous day to get the last day of current month
				endDate = GeneralDate.ymd(closureYearMonth.year(), closureYearMonth.month(), 1).addMonths(NEXT_DAY_MONTH).addDays(PREV_DAY_MONTH);
			}
			else { // 末日ではない
				// アルゴリズム「日付の存在チェック」を実行する
				// 算出日を締め期間の開始年月日に設定する
				startDate = checkClosureDateTime(optionalClosure.get().getClosureMonth().getProcessingYm().addMonths(PREV_DAY_MONTH), optionalClosureHistory.get().toClosureDate() + 1);
				
				// アルゴリズム「日付の存在チェック」を実行する
				// 算出日を締め期間の終了年月日に設定する
				endDate = checkClosureDateTime(optionalClosure.get().getClosureMonth().getProcessingYm(), optionalClosureHistory.get().toClosureDate());
			}
//		}
//		else {
//			// 当月　＝　締め変更履歴．開始年月
//			
//			// アルゴリズム「締め日変更時の期間を算出」を実行する
//			ClosurePeriod closurePeriod = calculateClosurePeriod(companyId, optionalClosure.get(), optionalClosureHistory.get());
//			
//			// Set to start date and end date
//			startDate = closurePeriod.getStartTime();
//			endDate = closurePeriod.getEndTime();
//		}
		
		/**
		 *  Check processing date (処理中年月日)
		 */
		// 処理中年月日 < Outputの開始年月日
		if (baseDate.compareTo(startDate) < 0) {
			return Optional.of("Msg_673");
		}
		
		return Optional.empty();
	}
	
	/**
	 * 締め日変更時の期間を算出.
	 *
	 * @param companyId the company id
	 * @param closure the closure
	 * @param closureHistoryCurrentMonth the closure history current month
	 * @return closure period (start time + end time)
	 */
	private ClosurePeriod calculateClosurePeriod(String companyId, Closure closure, ClosureHistory closureHistoryCurrentMonth) {
		ClosurePeriod closurePeriod = new ClosurePeriod();
		
		// 「当月-1」を含む締め変更履歴を取得する
		Optional<ClosureHistory> optionalClosureHistoryPrevMonth = closureRepository.findBySelectedYearMonth(companyId, closure.getClosureId().value, 
				closure.getClosureMonth().getProcessingYm().addMonths(PREV_DAY_MONTH).v());
		ClosureHistory closureHistoryPrevMonth = optionalClosureHistoryPrevMonth.get();
		
		GeneralDate startDate;
		GeneralDate endDate;
		
		// 「当月」の締め変更履歴．締め日と「当月-1」の締め変更履歴を比較する
		if (closureHistoryPrevMonth.getClosureDate().getClosureDay().compareTo(closureHistoryCurrentMonth.getClosureDate().getClosureDay()) <= 0) {
			// 「当月」の締め変更履歴．締め日　≦　「当月-1」の締め変更履歴．締め日
			
			// アルゴリズム「日付の存在チェック」を実行する (input: month-1, (month-1).(day+1) )
			startDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm().addMonths(PREV_DAY_MONTH), closureHistoryPrevMonth.getClosureDate().getClosureDay().v() + DAY_ONE);
			
			// アルゴリズム「日付の存在チェック」を実行する (input: month, month.day)
			endDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm(), DAY_ONE);
		}
		else {
			// 「当月」の締め変更履歴．締め日　＞　「当月-1」の締め変更履歴．締め日
				
			// 締め．当月．締め日変更区分をチェックする
			if (closure.getClosureMonth().getClosureClassification().get().equals(ClosureClassification.ClassificationClosingBefore)) {
				// 締め日変更前期間
				
				// アルゴリズム「日付の存在チェック」を実行する (input: month-1, (month-1).(day+1) )
				startDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm().addMonths(PREV_DAY_MONTH), closureHistoryPrevMonth.getClosureDate().getClosureDay().v() + DAY_ONE);
				
				// アルゴリズム「日付の存在チェック」を実行する (input: month, month.day)
				endDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm(), DAY_ONE);
			}
			else {
				// 締め日変更後期間
				
				// アルゴリズム「日付の存在チェック」を実行する (input: month, (month-1).(day+1) )
				startDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm(), closureHistoryPrevMonth.getClosureDate().getClosureDay().v() + DAY_ONE);
				
				// アルゴリズム「日付の存在チェック」を実行する (input: month, day)
				endDate = checkClosureDateTime(closure.getClosureMonth().getProcessingYm(), closureHistoryCurrentMonth.getClosureDate().getClosureDay().v());
			}
		}
		
		closurePeriod.setStartTime(startDate);
		closurePeriod.setEndTime(endDate);
		
		return closurePeriod;
	}
	
	/**
	 * Check employment status (before joining, retired) - 入社前、退職後チェック
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	private Optional<String> checkStatusEmployment(String employeeId, GeneralDate baseDate) {
		// 「在職状態を取得」を取得する
		EmploymentStatusDto employmentStatusDto = scEmploymentStatusAdapter.getStatusEmployment(employeeId, baseDate);
		
		switch (employmentStatusDto.getStatusOfEmployment()) 
		{
			case ScheCreExeWorkTimeHandler.BEFORE_JOINING:
				return Optional.of("Msg_674");
			case ScheCreExeWorkTimeHandler.RETIREMENT:
				return Optional.of("Msg_675");
			default:
				break;
		}
		return Optional.empty();
	}
	
	/**
	 * Gets the check schedule update.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the check schedule update
	 */
	//03_スケジュールチェック
	private Optional<String> getCheckScheduleUpdate(String companyId, String employeeId, GeneralDate baseDate) {

		// call repository find basic schedule by id
		Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(employeeId, baseDate);

		// check data not exist
		if (!optionalBasicSchedule.isPresent()) {
			return Optional.of("Msg_557");
		}
		
		// data exist => check 確定区分 is 確定済み
		if (optionalBasicSchedule.isPresent()
				&& optionalBasicSchedule.get().getConfirmedAtr() == ConfirmedAtr.CONFIRMED) {
			return Optional.of("Msg_558");
		}
		
		// 締め期間チェック
		Optional<String> optionalClosingPeriodMessage = checkClosePeriod(companyId, employeeId, baseDate);
		if (optionalClosingPeriodMessage.isPresent()) {
			return optionalClosingPeriodMessage;
		}
		
		// 「在職状態を取得」を取得する
		Optional<String> optionalStatusEmploymentMessage = checkStatusEmployment(employeeId, baseDate);
		if (optionalStatusEmploymentMessage.isPresent()) {
			return optionalStatusEmploymentMessage;
		}
		
		// default not error
		return Optional.empty();
	}
}