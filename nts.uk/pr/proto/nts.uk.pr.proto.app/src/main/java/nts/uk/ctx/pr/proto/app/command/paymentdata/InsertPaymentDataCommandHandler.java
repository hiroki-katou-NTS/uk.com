package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.CategoryCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.DetailItemCommandBase;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
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
public class InsertPaymentDataCommandHandler extends CommandHandler<InsertPaymentDataCommand> {

	/** CompanyRepository */
	@Inject
	private PaymentDataRepository paymentDataRepository;

	@Inject
	private ItemMasterRepository itemMasterRepository;

	@Override
	protected void handle(CommandHandlerContext<InsertPaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		Payment payment = context.getCommand().toDomain(companyCode);
		payment.validate();

		this.paymentDataRepository.insertHeader(payment);
		// update detail
		List<ItemMaster> mItems = this.itemMasterRepository.findAll(companyCode);
		for (CategoryCommandBase cate : context.getCommand().getCategories()) {
			this.registerDetail(payment, cate, mItems);
		}
	}
	
	/**
	 * Insert of update item data by status
	 * 
	 * @param items
	 * @param payment
	 */
	private void registerDetail(Payment payment, CategoryCommandBase category, List<ItemMaster> mItems) {
		category.getLines().stream().forEach(x-> {
		for (DetailItemCommandBase item : x.getDetails()) {
			
			ItemMaster mItem = mItems.stream().filter(m-> m.getItemCode().equals(item.getItemCode()) && m.getCategoryAtr().value == item.getCategoryAtr())
					.findFirst().get();
			DetailItem detailItem = DetailItem.createFromJavaType(
					item.getItemCode(), 
					item.getValue(),
					item.getCorrectFlag(),
					item.getSocialInsuranceAtr(),
					item.getLaborInsuranceAtr(),
					item.getCategoryAtr(),
					mItem.getDeductAttribute().value
					);
			this.paymentDataRepository.insertDetail(payment, detailItem);
		}
	});
	}
}
