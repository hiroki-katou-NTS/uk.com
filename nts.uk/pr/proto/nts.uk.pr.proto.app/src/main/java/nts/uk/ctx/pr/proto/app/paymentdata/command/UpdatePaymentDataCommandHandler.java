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
		int baseYM  = context.getCommand().getProcessingYM();
		
		Payment payment = context.getCommand().toDomain(companyCode);
		payment.validate();

		boolean isExistHeader = this.paymentDataRepository.isExistHeader(companyCode,
				personId, PAY_BONUS_ATR, baseYM);
		if (isExistHeader) {
			this.updateDeductionItem(context.getCommand().getDetailDeductionItems().get(0).getCategoryAtr(), payment.getDetailDeductionItems());
			this.updateWithoutDeductionItem(context.getCommand().getDetailPaymentItems().get(0).getCategoryAtr(), payment.getDetailPaymentItems());
			this.updateWithoutDeductionItem(context.getCommand().getDetailPersonalTimeItems().get(0).getCategoryAtr(), payment.getDetailPersonalTimeItems());
			this.updateWithoutDeductionItem(context.getCommand().getDetailArticleItems().get(0).getCategoryAtr(), payment.getDetailArticleItems());
			
		}else {
			throw new BusinessException(new RawErrorMessage("ER026"));
		}
		this.paymentDataRepository.update(payment);

	}
	
	/**
	 * update deduction detail items
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void updateDeductionItem(int categoryAtr, List<DetailDeductionItem> items) {
//		boolean isExistDeductionDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM, context.getCommand().getDetailDeductionItems().get(0).getCategoryAtr());
		boolean isExistDeductionDetails = true;
		if (isExistDeductionDetails) {
			this.paymentDataRepository.updateDeductionDetails(categoryAtr, items);	
		}else {
			throw new BusinessException(new RawErrorMessage("ER026"));
		}
		
	}

	/**
	 * update detail item without deduction
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void updateWithoutDeductionItem(int categoryAtr, List<DetailItem> items) {
//		boolean isExistDeductionDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM, context.getCommand().getDetailDeductionItems().get(0).getCategoryAtr());
		boolean isExistDetails = true;
		if (isExistDetails) {
			this.paymentDataRepository.updateDetails(categoryAtr, items);	
		}else {
			throw new BusinessException(new RawErrorMessage("ER026"));
		}
	}

}
