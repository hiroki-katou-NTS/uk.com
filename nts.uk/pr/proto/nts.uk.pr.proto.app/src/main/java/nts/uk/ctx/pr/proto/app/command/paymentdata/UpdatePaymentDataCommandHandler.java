package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.CategoryCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.DetailItemCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.LineCommandBase;
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
	
	/** PAY BONUS ATTRIBUTE FIXED */
	private static final int PAY_BONUS_ATR = 0;

	@Override
	protected void handle(CommandHandlerContext<UpdatePaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		String personId = context.getCommand().getPaymentHeader().getPersonId();
		int baseYM = context.getCommand().getPaymentHeader().getProcessingYM();
		
		
		Payment payment = context.getCommand().toDomain(companyCode);
		payment.validate();

		// check data
		boolean isExistHeader = this.paymentDataRepository.isExistHeader(companyCode, personId, PAY_BONUS_ATR, baseYM);
		if (isExistHeader) {
			for (CategoryCommandBase cate : context.getCommand().getCategories()) {
				this.checkIfAllItemsExist(companyCode, personId, baseYM,
						cate.getLines());
			}
		} else {
			throw new BusinessException("更新対象のデータが存在しません");
		}
		
		// update payment header
		this.paymentDataRepository.updateHeader(payment);

		// update detail
		for (CategoryCommandBase cate : context.getCommand().getCategories()) {
			this.registerDetail(payment, cate);
		}
	}

	/**
	 * check item had registered is exist
	 * 
	 * @param categoryAtr
	 * @param items
	 */
	private void checkIfAllItemsExist(String companyCode, String personId, int baseYM, List<LineCommandBase> lines) {
		lines.stream().forEach(x-> {
			List<DetailItemCommandBase> items = x.getDetails();
			for (DetailItemCommandBase item : items) {
				boolean isExistDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, baseYM,
						item.getCategoryAtr(), item.getItemCode());
				if (!isExistDetails) {
					throw new BusinessException(("更新対象のデータが存在しません"));
				}
			}
		});
		
	}

	/**
	 * Insert of update item data by status
	 * 
	 * @param items
	 * @param payment
	 */
	private void registerDetail(Payment payment, CategoryCommandBase category) {
		category.getLines().stream().forEach(x-> {
			for (DetailItemCommandBase item : x.getDetails()) {
				
				DetailItem detailItem = DetailItem.createFromJavaType(
						item.getItemCode(), 
						item.getValue(),
						item.getCorrectFlag(),
						item.getSocialInsuranceAtr(),
						item.getLaborInsuranceAtr(),
						item.getCategoryAtr(),
						item.getDeductAtr(),
						item.getItemAtr(),
						item.getLinePosition(),
						item.getColumnPosition()
						);
				
				if (item.isCreated()) {
						this.paymentDataRepository.updateDetail(payment, detailItem);	
				} else {
						this.paymentDataRepository.insertDetail(payment, detailItem);
				}
			}
		});
	}

}
