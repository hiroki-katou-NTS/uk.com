package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemMonthlyWorkScheduleSaveHandler.
 */
@Stateless
public class OutputItemMonthlyWorkScheduleSaveHandler extends CommandHandler<OutputItemMonthlyWorkScheduleCommand>{

	/** The repository. */
	@Inject
	private OutputItemMonthlyWorkScheduleRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<OutputItemMonthlyWorkScheduleCommand> context) {
		OutputItemMonthlyWorkScheduleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		OutputItemMonthlyWorkSchedule domain = new OutputItemMonthlyWorkSchedule(command);
		// Get employee by command
		Optional<String> employeeId = !StringUtil.isNullOrEmpty(command.getEmployeeID(), false)
										? Optional.of(command.getEmployeeID())
										: Optional.empty();
		if (command.isNewMode()) {
			Optional<OutputItemMonthlyWorkSchedule> oDomain = repository.findBySelectionAndCidAndSidAndCode(command.getItemSelectionEnum()
					, companyId
					, command.getItemCode().v()
					, employeeId);
			if (oDomain.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			//新規モードの場合
			repository.add(domain);
		} else {
			//更新モードの場合
			repository.update(domain);
		}
		
	}

}
