package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemMonthlyWorkScheduleDeleteHandler.
 */
@Stateless
public class OutputItemMonthlyWorkScheduleDeleteHandler extends CommandHandler<OutputItemMonthlyWorkScheduleDeleteCommand> {

	/** The repository. */
	@Inject
	OutputItemMonthlyWorkScheduleRepository repository;

	@Override
	protected void handle(CommandHandlerContext<OutputItemMonthlyWorkScheduleDeleteCommand> context) {
		OutputItemMonthlyWorkScheduleDeleteCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		repository.deleteBySelectionAndCidAndSidAndCode(command.getItemSelectionEnum(), companyId, command.getItemCode().v(), employeeId);
	}

	/**
	   *  出力項目を削除する(Delete output items)
	 * @param itemSelectionEnum
	 * @param itemCode
	 * @param sId
	 */
//	public void delete(ItemSelectionEnum itemSelectionEnum, String itemCode, String sId) {
//		// Get employeeId
//		Optional<String> employeeId = !StringUtil.isNullOrEmpty(sId, false)
//										? Optional.of(sId)
//										: Optional.empty();
//		String companyId = AppContexts.user().companyId();
//		repository.deleteBySelectionAndCidAndSidAndCode(itemSelectionEnum, companyId, itemCode, employeeId);
//	}
	
}
