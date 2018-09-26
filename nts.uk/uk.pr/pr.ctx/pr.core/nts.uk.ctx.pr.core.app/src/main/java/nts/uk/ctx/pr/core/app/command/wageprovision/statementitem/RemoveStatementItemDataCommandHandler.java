package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemNameRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveStatementItemDataCommandHandler extends CommandHandler<StatementItemDataCommand> {
	@Inject
	private StatementItemRepository statementItemRepository;
	@Inject
	private StatementItemNameRepository statementItemNameRepository;
	@Inject
	private PaymentItemSetRepository paymentItemSetRepository;
	@Inject
	private DeductionItemSetRepository deductionItemSetRepository;
	@Inject
	private TimeItemSetRepository timeItemSetRepository;
	@Inject
	private StatementItemDisplaySetRepository statementItemDisplaySetRepository;
	@Inject
	private ItemRangeSetRepository itemRangeSetRepository;
	@Inject
	private SetPeriodCycleRepository setPeriodCycleRepository;
	@Inject
	private BreakdownItemSetRepository breakdownItemSetRepository;

	@Override
	protected void handle(CommandHandlerContext<StatementItemDataCommand> context) {
		val command = context.getCommand();
		String cid = AppContexts.user().companyId();

		val statementItem = command.getStatementItem();
		String salaryItemId = command.getSalaryItemId();
		statementItemRepository.remove(cid, statementItem.getCategoryAtr(), statementItem.getItemNameCd(),
				salaryItemId);
		statementItemNameRepository.remove(cid, salaryItemId);

		switch (EnumAdaptor.valueOf(command.getStatementItem().getCategoryAtr(), CategoryAtr.class)) {
		case PAYMENT_ITEM:
			paymentItemSetRepository.remove(cid, salaryItemId);
			setPeriodCycleRepository.remove(salaryItemId);
			breakdownItemSetRepository.removeAll(salaryItemId);
			break;
		case DEDUCTION_ITEM:
			deductionItemSetRepository.remove(cid, salaryItemId);
			setPeriodCycleRepository.remove(salaryItemId);
			breakdownItemSetRepository.removeAll(salaryItemId);
			break;
		case ATTEND_ITEM:
			timeItemSetRepository.remove(cid, salaryItemId);
			break;
		case REPORT_ITEM:
			break;
		case OTHER_ITEM:
			break;

		}

		itemRangeSetRepository.remove(cid, salaryItemId);
		statementItemDisplaySetRepository.remove(cid, salaryItemId);
	}
}
