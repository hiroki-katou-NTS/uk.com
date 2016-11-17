package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.ctx.pr.proto.dom.allot.CompanyAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSetting;
import nts.uk.ctx.pr.proto.dom.allot.PersonalAllotSettingRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateProcessingMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateProcessingMasterRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.PaymentDataHeaderDto;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.PaymentDataResult;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * GetPaymentDataQueryProcessor
 * 
 * @author vunv
 *
 */
@RequestScoped
public class GetPaymentDataQueryProcessor {

	private static final int PAY_BONUS_ATR = 0;

	/** PersonalPaymentSettingRepository */
	@Inject
	private PersonalAllotSettingRepository personalPSRepository;

	@Inject
	private CompanyAllotSettingRepository companyAllotSettingRepository;

	/** LayoutMasterRepository */
	@Inject
	private LayoutMasterRepository layoutMasterRepository;

	@Inject
	private LayoutMasterCategoryRepository layoutMasterCategoryRepository;

	@Inject
	private LayoutMasterLineRepository layoutMasterLineRepository;

	@Inject
	private LayoutMasterDetailRepository layoutMasterDetailRepository;

	@Inject
	private PaymentDateProcessingMasterRepository payDateMasterRepository;

	@Inject
	private PaymentDataRepository paymentDataRepository;

	/**
	 * get data detail
	 * 
	 * @param companyCode
	 *            code
	 * @return PaymentDataResult
	 */
	public PaymentDataResult find(int baseYM) {
		PaymentDataResult result = new PaymentDataResult();
		String companyCode = AppContexts.user().companyCode();
		String personId = AppContexts.user().personId();
		int startYM;
		String stmtCode = "";

		// Pay date master
		PaymentDateProcessingMaster payDateMaster = this.payDateMasterRepository.find(companyCode, PAY_BONUS_ATR).get();

		// get stmtCode
		Optional<PersonalAllotSetting> optpersonalPS = this.personalPSRepository.find(companyCode, personId,
				payDateMaster.getCurrentProcessingYm().v());
		if (optpersonalPS.isPresent()) {
			stmtCode = optpersonalPS.get().getPaymentDetailCode().v();
		} else {
			stmtCode = this.companyAllotSettingRepository.find(companyCode, baseYM).get().getPaymentDetailCode().v();
		}

		// get 明細書マスタ
		LayoutMaster layout = this.layoutMasterRepository.getLayout(companyCode, stmtCode, baseYM)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("対象データがありません。")));
		startYM = layout.getStartYM().v();

		// get header of payment
		Optional<PaymentDataHeaderDto> optPayHeader = this.getPaymentHeader(AppContexts.user(),
				layout.getStmtName().v(), payDateMaster);
		// Case: 「Update」
		if (optPayHeader.isPresent()) {
			result.setPaymenHeader(optPayHeader.get());
			// get detail of payment

		} else { // Case 「Insert」

			// get 明細書マスタカテゴリ
			List<LayoutMasterCategory> categories = this.layoutMasterCategoryRepository.getCategories(companyCode,
					layout.getStmtCode().v(), startYM);

			// get 明細書マスタ行
			List<LayoutMasterLine> lines = this.layoutMasterLineRepository.getLines(companyCode, stmtCode, startYM);

			List<LayoutMasterDetail> lineDetails = this.layoutMasterDetailRepository.getDetails(companyCode, stmtCode,
					startYM);

		}
		return null;
	}

	/**
	 * get data of payment header
	 * 
	 * @param companyCode
	 * @param personId
	 * @param payDateMaster
	 * @return
	 */
	private Optional<PaymentDataHeaderDto> getPaymentHeader(LoginUserContext user, String spcificationName,
			PaymentDateProcessingMaster payDateMaster) {
		Optional<Payment> payHeader = this.paymentDataRepository.find(user.companyCode(), user.personId(),
				payDateMaster.getProcessingNo().v(), PAY_BONUS_ATR, payDateMaster.getCurrentProcessingYm().v(), 0);

		return payHeader.map(d -> PaymentDataHeaderDto.fromDomain(d, spcificationName, user.employeeCode()));
	}

	private void getPaymentDetails() {

	}

}
