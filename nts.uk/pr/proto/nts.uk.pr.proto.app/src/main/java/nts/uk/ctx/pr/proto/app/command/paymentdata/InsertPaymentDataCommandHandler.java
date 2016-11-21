package nts.uk.ctx.pr.proto.app.command.paymentdata;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
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

		this.paymentDataRepository.add(payment);
	}
}
