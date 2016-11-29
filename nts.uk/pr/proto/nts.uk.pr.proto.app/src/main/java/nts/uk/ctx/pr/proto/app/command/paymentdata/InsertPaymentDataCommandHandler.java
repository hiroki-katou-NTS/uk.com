package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
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

	@Inject
	private PaymentDateMasterRepository payDateMasterRepository;
	
	@Override
	protected void handle(CommandHandlerContext<InsertPaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		Payment payment = context.getCommand().toDomain(companyCode);	
		LocalDate mPayDate = this.payDateMasterRepository.find(
				companyCode, payment.getPayBonusAtr().value, payment.getProcessingYM().v(), 
				payment.getSparePayAtr().value, payment.getProcessingNo().v()).map(d-> d.getStandardDate()).orElse(LocalDate.now());
		
		payment.setStandardDate(mPayDate);
		payment.validate();

		this.paymentDataRepository.insertHeader(payment);
		// update detail
		this.registerDetail(payment, payment.getDetailPaymentItems());
		this.registerDetail(payment, payment.getDetailArticleItems());
		this.registerDetail(payment, payment.getDetailDeductionItems());
		this.registerDetail(payment, payment.getDetailPersonalTimeItems());
	}

	/**
	 * Insert of update item data by status
	 * 
	 * @param items
	 * @param payment
	 */
	private void registerDetail(Payment payment, List<DetailItem> details) {
		for (DetailItem item : details) {
			this.paymentDataRepository.insertDetail(payment, item);
		}
	}
}
