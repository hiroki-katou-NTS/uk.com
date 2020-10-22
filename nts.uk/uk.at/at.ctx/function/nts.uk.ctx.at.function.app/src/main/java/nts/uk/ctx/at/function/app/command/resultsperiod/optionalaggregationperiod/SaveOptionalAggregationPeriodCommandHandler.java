package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * The Class SaveOptionalAggregationPeriodCommandHandler.
 */
@Stateless
public class SaveOptionalAggregationPeriodCommandHandler extends CommandHandler<OptionalAggregationPeriodCommand> {

	/** The repository. */
	@Inject
	private AnyAggrPeriodRepository repository;

	/**
	 * Handle.<br>
	 *	任意集計期間の登録処理
	 * @param context the context
	 */
	@Override
	protected void handle(CommandHandlerContext<OptionalAggregationPeriodCommand> context) {
		OptionalAggregationPeriodCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		AnyAggrPeriod domain = AnyAggrPeriod.createFromMemento(companyId, command);
		// 「任意集計期間」の重複をチェックする
		if (repository.checkExisted(companyId, domain.getAggrFrameCode().v())) {
			throw new BusinessException("Msg_3");
		}
		// 任意集計期間の登録処理
		repository.addAnyAggrPeriod(domain);
	}
	

}
