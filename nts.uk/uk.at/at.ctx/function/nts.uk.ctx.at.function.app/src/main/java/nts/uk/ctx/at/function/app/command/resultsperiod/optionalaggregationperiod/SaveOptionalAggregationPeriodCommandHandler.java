package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveOptionalAggregationPeriodCommandHandler.
 */
@Stateless
public class SaveOptionalAggregationPeriodCommandHandler extends CommandHandler<OptionalAggregationPeriodCommand> {

	/** The repository. */
	@Inject
	private OptionalAggrPeriodRepository repository;

	/**
	 * Handle.
	 *	任意集計期間の登録処理
	 * @param context the context
	 */
	@Override
	protected void handle(CommandHandlerContext<OptionalAggregationPeriodCommand> context) {
		OptionalAggregationPeriodCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		OptionalAggrPeriod domain = new OptionalAggrPeriod(companyId, command.getAggrFrameCode(), command.getOptionalAggrName(), command.getStartDate(), command.getStartDate());
		// 「任意集計期間」の重複をチェックする
		if (repository.checkExit(companyId, domain.getAggrFrameCode().v())) {
			throw new BusinessException("Msg_3");
		}
		// 任意集計期間の登録処理
		repository.addOptionalAggrPeriod(domain);
	}
	

}
