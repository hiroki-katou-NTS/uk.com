package nts.uk.ctx.pr.proto.app.command.paymentdata;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.CategoryCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.DetailItemCommandBase;
import nts.uk.ctx.pr.proto.app.command.paymentdata.base.LineCommandBase;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;
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
public class UpdatePaymentDataCommandHandler extends CommandHandler<UpdatePaymentDataCommand> {

	/** CompanyRepository */
	@Inject
	private PaymentDataRepository paymentDataRepository;

	@Inject
	private PaymentDateMasterRepository payDateMasterRepository;

	@Inject
	private ItemMasterRepository itemMasterRepository;

	/** PAY BONUS ATTRIBUTE FIXED */
	private static final int PAY_BONUS_ATR = 0;

	@Override
	protected void handle(CommandHandlerContext<UpdatePaymentDataCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		String personId = context.getCommand().getPaymentHeader().getPersonId();
		int baseYM = context.getCommand().getPaymentHeader().getProcessingYM();

		Payment payment = context.getCommand().toDomain(companyCode);
		LocalDate mPayDate = this.payDateMasterRepository
				.find(companyCode, payment.getPayBonusAtr().value, payment.getProcessingYM().v(),
						payment.getSparePayAtr().value, payment.getProcessingNo().v())
				.map(d -> d.getStandardDate()).orElse(LocalDate.now());

		payment.setStandardDate(mPayDate);

		payment.validate();

		// check data
		boolean isExistHeader = this.paymentDataRepository.isExistHeader(companyCode, personId, PAY_BONUS_ATR, baseYM);
		if (isExistHeader) {
			for (CategoryCommandBase cate : context.getCommand().getCategories()) {
				this.checkIfAllItemsExist(companyCode, personId, payment.getProcessingNo().v(), baseYM, cate.getLines(),
						payment.getPayBonusAtr(), payment.getSparePayAtr());
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
	private void checkIfAllItemsExist(String companyCode, String personId, int processingNo, int baseYM,
			List<LineCommandBase> lines, PayBonusAtr payBonusAtr, SparePayAtr sparePayAtr) {
		lines.stream().forEach(x -> {
			List<DetailItemCommandBase> items = x.getDetails().stream()
					.filter(t -> !StringUtil.isNullOrEmpty(t.getItemCode(), true) && t.isCreated())
					.collect(Collectors.toList());
			for (DetailItemCommandBase item : items) {
				boolean isExistDetails = this.paymentDataRepository.isExistDetail(companyCode, personId, processingNo,
						baseYM, item.getCategoryAtr(), item.getItemCode(), payBonusAtr.value, sparePayAtr.value);
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
		category.getLines().stream().forEach(x -> {
			List<DetailItemCommandBase> details = x.getDetails().stream()
					.filter(t -> !StringUtil.isNullOrEmpty(t.getItemCode(), true)).collect(Collectors.toList());
			for (DetailItemCommandBase item : details) {

				if ("".equals(item.getValue())) {
					throw new BusinessException("入力にエラーがあります。");
				}

				ItemMaster mItem = this.itemMasterRepository
						.find(payment.getCompanyCode().v(), item.getCategoryAtr(), item.getItemCode()).get();
				
				DetailItem detailItem = DetailItem.createFromJavaType(item.getItemCode(), item.getValue(),
						item.getCorrectFlag(), item.getSocialInsuranceAtr(), item.getLaborInsuranceAtr(),
						item.getCategoryAtr(), item.getDeductAtr(), item.getLinePosition(), item.getColumnPosition());

				detailItem.additionalInfo(mItem.getLimitMoney().v(), mItem.getFixedPaidAtr().value,
						mItem.getAvgPaidAtr().value, mItem.getItemAtr().value);
				detailItem.addCommuteData(item.getCommuteAllowTaxImpose(), item.getCommuteAllowMonth(), item.getCommuteAllowFraction());
				
				if (item.isCreated()) {
					this.paymentDataRepository.updateDetail(payment, detailItem);
				} else {
					this.paymentDataRepository.insertDetail(payment, detailItem);
				}
			}
		});
	}

}
