package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.DetailItemCommandBase;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
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
			this.isNotExistItem(companyCode, personId, baseYM,
					context.getCommand().getDetailPaymentItems());
			this.isNotExistItem(companyCode, personId, baseYM,
					context.getCommand().getDetailPersonalTimeItems());
			this.isNotExistItem(companyCode, personId, baseYM,
					context.getCommand().getDetailArticleItems());
			this.isNotExistItem(companyCode, personId, baseYM, context.getCommand().getDetailDeductionItems());
		} else {
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません"));
		}
		
		// update payment header
		this.paymentDataRepository.update(payment);

		this.registerDetail(payment, context.getCommand().getDetailPaymentItems());
		this.registerDetail(payment, context.getCommand().getDetailArticleItems());
		this.registerDetail(payment, context.getCommand().getDetailDeductionItems());
		this.registerDetail(payment, context.getCommand().getDetailPersonalTimeItems());
	}

	/**
	 * check item had registered is exist
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void isNotExistItem(String companyCode, String personId, int baseYM, List<DetailItemCommandBase> items) {
		for (DetailItemCommandBase item : items) {
			boolean isExistDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM,
					item.getCategoryAtr(), item.getItemCode());
			if (!isExistDetails) {
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません"));
			}
		}
	}

	/**
	 * Insert of update item data by status
	 * 
	 * @param items
	 * @param payment
	 */
	private void registerDetail(Payment payment, List<DetailItemCommandBase> items) {

		for (DetailItemCommandBase item : items) {

			DetailItem detailItem = DetailItem.createFromJavaType(
					item.getItemCode(), 
					item.getValue(),
					item.getCorrectFlag(),
					item.getSocialInsuranceAtr(),
					item.getLaborInsuranceAtr(),
					item.getCategoryAtr());
			
			if (item.isCreated()) {
				this.paymentDataRepository.updateDetail(payment, detailItem);
			} else {
				this.paymentDataRepository.insertDetail(payment, detailItem);
			}
		}
	}

}
