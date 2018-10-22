package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateStatementItemDataCommandHandler extends CommandHandler<StatementItemDataCommand> {
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
	private ValidateStatementItemData validateStatementItemData;

	@Override
	protected void handle(CommandHandlerContext<StatementItemDataCommand> context) {
		val command = context.getCommand();
		validateStatementItemData.validate(command);
		
		String cid = AppContexts.user().companyId();
		String itemNameCd = command.getItemNameCd();
		val statementItem = command.getStatementItem();
		
	
		// ドメインモデル「明細書項目」を新規追加する
		if (statementItem != null) {
			statementItemRepository.update(new StatementItem(cid, statementItem.getCategoryAtr(),
					statementItem.getItemNameCd(), statementItem.getDefaultAtr(),
					statementItem.getValueAtr(), statementItem.getDeprecatedAtr(),
					statementItem.getSocialInsuaEditableAtr(), statementItem.getIntergrateCd()));
		}
		val categoryAtr = EnumAdaptor.valueOf(statementItem.getCategoryAtr(), CategoryAtr.class);
		switch (categoryAtr) {
		case PAYMENT_ITEM:
			// ドメインモデル「支給項目設定」を新規追加する
			val paymentItem = command.getPaymentItemSet();
			if (paymentItem != null) {
				paymentItemSetRepository.add(command.toPaymentItemSet(cid));
			}
			break;

		case DEDUCTION_ITEM:
			// ドメインモデル「控除項目設定」を新規追加する
			val deductionItem = command.getDeductionItemSet();
			if (deductionItem != null) {
				deductionItemSetRepository.add(command.toDeductionItemSet(cid));
			}
			break;

		case ATTEND_ITEM:
			// ドメインモデル「勤怠項目設定」を新規追加する
			val timeItem = command.getTimeItemSet();
			if (timeItem != null) {
				timeItemSetRepository.update(command.toTimeItemSet(cid));
			}
			break;

		case REPORT_ITEM:
			break;
		case OTHER_ITEM:
			break;
		}

		if (categoryAtr == CategoryAtr.PAYMENT_ITEM || categoryAtr == CategoryAtr.DEDUCTION_ITEM
				|| categoryAtr == CategoryAtr.ATTEND_ITEM || categoryAtr == CategoryAtr.REPORT_ITEM) {
			// ドメインモデル「明細項目の表示設定」を新規追加する
			val statementDisplay = command.getStatementItemDisplaySet();
			if (statementDisplay != null) {
				statementItemDisplaySetRepository.update(new StatementItemDisplaySet(cid, categoryAtr.value, itemNameCd,
						statementDisplay.getZeroDisplayAtr(), statementDisplay.getItemNameDisplay()));
			}
		}

		// ドメインモデル「明細書項目名称」を新規追加する
		val statementItemName = command.getStatementItemName();
		if (statementItemName != null) {
			statementItemNameRepository.update(new StatementItemName(cid, categoryAtr.value, itemNameCd, statementItemName.getName(),
					statementItemName.getShortName(), statementItemName.getOtherLanguageName(),
					statementItemName.getEnglishName()));
		}

	}
}
