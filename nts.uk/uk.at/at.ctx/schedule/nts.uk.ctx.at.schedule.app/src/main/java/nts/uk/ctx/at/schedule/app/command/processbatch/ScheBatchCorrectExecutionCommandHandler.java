/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
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
	
	
	/** The Constant NEXT_DAY_MONTH. */
	private static final int NEXT_DAY_MONTH = 1;
	
	/** The Constant DATA_EXECUTION. */
	private static final String DATA_EXECUTION = "DATA_EXECUTION";
			
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
		dto.setErrors(new ArrayList<>());
		dto.setWithError(WithError.NO_ERROR);
		
		setter.setData(DATA_EXECUTION, dto);
		
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
	private void registerProcess(String employeeId, GeneralDate baseDate){
		
		// call check schedule update
		Optional<String> optionalMessage = this.getCheckScheduleUpdate(employeeId, baseDate);
		
		// check exist error
		if(optionalMessage.isPresent()){
			
		}
	}
	
	/**
	 * Gets the check schedule update.
	 *
	 * @param employeeId the employee id
	 * @param baseDate the base date
	 * @return the check schedule update
	 */
	//03_スケジュールチェック
	private Optional<String> getCheckScheduleUpdate(String employeeId, GeneralDate baseDate) {

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

		// default not error
		return Optional.empty();
	}
}