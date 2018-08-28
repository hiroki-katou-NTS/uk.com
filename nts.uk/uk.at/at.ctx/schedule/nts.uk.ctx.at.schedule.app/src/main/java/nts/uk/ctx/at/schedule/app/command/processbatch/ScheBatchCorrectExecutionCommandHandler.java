/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.schedule.dom.executionlog.CompletionStatus;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;;

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
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private InterimRemainOffMonthProcess interimRemainOffMonthProcess;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
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
		
		// Get work type
		WorkType workType = workTypeRepository.findByPK(companyId, command.getWorktypeCode()).get();
		
		GeneralDate startDate = command.getStartDate();
		GeneralDate endDate = command.getEndDate();
		DatePeriod period = new DatePeriod(startDate, endDate);
		
		// 選択されている社員ループ
		for (String employeeId : command.getEmployeeIds()) {
			 	
			EmployeeDto employeeDto = scEmployeeAdapter.findByEmployeeId(employeeId);
			
			GeneralDate currentDateCheck = startDate;
			// 開始日から終了日までループ
			while (currentDateCheck.compareTo(endDate) <= 0) {
				// check is client submit cancel ［中断］(Interrupt)
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					
					return;
				}
				
				Optional<String> optErrorMsg = registerProcess(companyId, command, employeeId, currentDateCheck, workType);
				
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
			
			// 暫定データを作成する
			interimRemainDataMngRegisterDateChange.registerDateChange(companyId, employeeId, period.datesBetween());
			//Map<GeneralDate, DailyInterimRemainMngData> mapInterimData = interimRemainOffMonthProcess.monthInterimRemainData(companyId, employeeId, period);
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
	private Optional<String> registerProcess(String companyId, ScheBatchCorrectSetCheckSaveCommand command, String employeeId, GeneralDate baseDate, WorkType workType){
		
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
			scheCreExeBasicScheduleHandler.registerBasicScheduleSaveCommand(companyId, optionalBasicSchedule, optPrescribedSetting, workTimeSetGetterCommand, 
					employeeId, baseDate, workType);
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
		
		// ドメインモデル「締め」を取得する
		Optional<Closure> optionalClosure = closureRepository.findById(companyId, optionalClosureEmployment.get().getClosureId());
		
		/** 
		 * 当月の期間を算出する
		 */
		DatePeriod closurePeriod = closureService.getClosurePeriod(optionalClosure.get().getClosureId().value, optionalClosure.get().getClosureMonth().getProcessingYm());
			
		/**
		 *  Check processing date (処理中年月日)
		 */
		// 処理中年月日 < Outputの開始年月日
		if (baseDate.compareTo(closurePeriod.start()) < 0) {
			return Optional.of("Msg_673");
		}
		
		return Optional.empty();
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