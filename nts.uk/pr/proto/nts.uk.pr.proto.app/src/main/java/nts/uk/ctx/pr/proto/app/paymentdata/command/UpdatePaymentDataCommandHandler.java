package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailDeductionItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Command Handler: add payment data.
 * 
 * @author vunv
 *
 */
@RequestScoped
@Transactional
public class UpdatePaymentDataCommandHandler extends CommandHandler<UpdatePaymentDataCommand> {

	/** CompanyRepository */
	@Inject
	private PaymentDataRepository paymentDataRepository;

	private static final int PAY_BONUS_ATR = 0;

	@Override
	protected void handle(CommandHandlerContext<UpdatePaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		String personId = context.getCommand().getPersonId();
		int baseYM = context.getCommand().getProcessingYM();

		Payment payment = context.getCommand().toDomain(companyCode);
		payment.validate();

		boolean isExistHeader = this.paymentDataRepository.isExistHeader(companyCode, personId, PAY_BONUS_ATR, baseYM);
		if (isExistHeader) {
			this.isExistWithoutDeductionItem(companyCode, personId, baseYM, payment.getDetailPaymentItems());
			this.isExistWithoutDeductionItem(companyCode, personId, baseYM, payment.getDetailPersonalTimeItems());
			this.isExistWithoutDeductionItem(companyCode, personId, baseYM, payment.getDetailArticleItems());
			this.isExistDeductionItem(companyCode, personId, baseYM, payment.getDetailDeductionItems());
		} else {
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません"));
		}
		this.paymentDataRepository.update(payment);

	}

	/**
	 * update detail item without deduction
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void isExistWithoutDeductionItem(String companyCode, String personId, int baseYM, List<DetailItem> items) {
		for (DetailItem item : items) {
			boolean isExistDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM,
					item.getCategoryAttribute().value, item.getItemCode().v());
			if (!isExistDetails) {
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません"));
			}
		}
	}

	/**
	 * update deduction detail items
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void isExistDeductionItem(String companyCode, String personId, int baseYM,
			List<DetailDeductionItem> items) {
		for (DetailDeductionItem item : items) {
			boolean isExistDeductionDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM,
					item.getCategoryAttribute().value, item.getItemCode().v());
			if (!isExistDeductionDetails) {
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません"));
			}
		}
	}

}
