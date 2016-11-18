package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
public class InsertPaymentDataCommandHandler extends CommandHandler<InsertPaymentDataCommand> {

	/** CompanyRepository */
	@Inject
	private PaymentDataRepository paymentDataRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertPaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		Payment payment = context.getCommand().toDomain(companyCode);
		payment.validate();

		this.paymentDataRepository.insertHeader(payment);
		
		this.insertDeductionItem(context.getCommand().getDetailDeductionItems().get(0).getCategoryAtr(), payment.getDetailDeductionItems());
		this.insertWithoutDeductionItem(context.getCommand().getDetailPaymentItems().get(0).getCategoryAtr(), payment.getDetailPaymentItems());
		this.insertWithoutDeductionItem(context.getCommand().getDetailPersonalTimeItems().get(0).getCategoryAtr(), payment.getDetailPersonalTimeItems());
		this.insertWithoutDeductionItem(context.getCommand().getDetailArticleItems().get(0).getCategoryAtr(), payment.getDetailArticleItems());
		

	}
	
	/**
	 * insert deduction detail items
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void insertDeductionItem(int categoryAtr, List<DetailDeductionItem> items) {
		this.paymentDataRepository.insertDeductionDetails(categoryAtr, items);
	}

	/**
	 * insert detail item without deduction
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void insertWithoutDeductionItem(int categoryAtr, List<DetailItem> items) {
		this.paymentDataRepository.insertDetails(categoryAtr, items);
	}
}
