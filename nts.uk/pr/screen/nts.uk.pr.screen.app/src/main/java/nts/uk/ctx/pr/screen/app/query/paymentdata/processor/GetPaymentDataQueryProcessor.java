package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.pr.screen.app.query.paymentdata.query.PaymentDataQuery;
import nts.uk.ctx.pr.screen.app.query.paymentdata.repository.PaymentDataQueryRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemDto;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.DetailItemPositionDto;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.LayoutMasterCategoryDto;
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

	@Inject
	private PaymentDataQueryRepository queryRepository;

	/**
	 * get data detail
	 * 
	 * @param companyCode
	 *            code
	 * @return PaymentDataResult
	 */
	public PaymentDataResult find(PaymentDataQuery query) {
		PaymentDataResult result = new PaymentDataResult();
		String companyCode = AppContexts.user().companyCode();
		int startYM;
		String stmtCode = "";

		// Pay date master
		PaymentDateProcessingMaster payDateMaster = this.payDateMasterRepository.find(companyCode, PAY_BONUS_ATR).get();
		startYM = payDateMaster.getCurrentProcessingYm().v();

		// get stmtCode
		Optional<PersonalAllotSetting> optpersonalPS = this.personalPSRepository.find(companyCode, query.getPersonId(),
				payDateMaster.getCurrentProcessingYm().v());
		if (optpersonalPS.isPresent()) {
			stmtCode = optpersonalPS.get().getPaymentDetailCode().v();
		} else {
			stmtCode = this.companyAllotSettingRepository.find(companyCode, startYM).get().getPaymentDetailCode().v();
		}

		// get 明細書マスタ
		LayoutMaster layout = this.layoutMasterRepository.getLayout(companyCode, startYM, stmtCode)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("対象データがありません。")));
		startYM = layout.getStartYM().v();

		// get header of payment
		Optional<PaymentDataHeaderDto> optPayHeader = this.getPaymentHeader(AppContexts.user(),
				layout.getStmtName().v(), payDateMaster);
		// Case: 「Has Data」
		if (optPayHeader.isPresent()) {
			result.setPaymenHeader(optPayHeader.get());
			// get detail of payment
			result.setCategories(this.getPaymentDetails(AppContexts.user(), payDateMaster.getCurrentProcessingYm().v(),
					stmtCode, startYM));

		} else { // Case 「No Data」
			// get 明細書マスタカテゴリ
			List<LayoutMasterCategory> masterCategories = this.layoutMasterCategoryRepository.getCategories(companyCode,
					layout.getStmtCode().v(), startYM);

			// get 明細書マスタ行
			List<LayoutMasterLine> lines = this.layoutMasterLineRepository.getLines(companyCode, stmtCode, startYM);

			List<LayoutMasterDetail> lineDetails = this.layoutMasterDetailRepository.getDetails(companyCode, stmtCode,
					startYM);

			List<LayoutMasterCategoryDto> categories = new ArrayList<>();
			for (LayoutMasterCategory category : masterCategories) {
				int categoryAtr = category.getCtAtr().value;
				List<DetailItemDto> items = new ArrayList<>();

				for (LayoutMasterDetail item : lineDetails) {
					LayoutMasterLine masterLine = lines.stream().filter(x -> x.getCategoryAtr().value == categoryAtr
							&& x.getAutoLineId().equals(item.getAutoLineId())).findFirst().get();
					DetailItemDto detailItem = DetailItemDto.fromDomain(categoryAtr, item.getItemCode().v(),
							item.getItemAbName().v(), null, 0, 0, 0, DetailItemPositionDto
									.fromDomain(masterLine.getLinePosition().v(), item.getItemPosColumn().v()));
					items.add(detailItem);
				}
				categories.add(LayoutMasterCategoryDto.fromDomain(categoryAtr, category.getCtgPos().v(), items));
			}
			result.setCategories(categories);
		}
		return result;
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

	private List<LayoutMasterCategoryDto> getPaymentDetails(LoginUserContext user, int processingYM, String stmtCode,
			int startYM) {

		Map<Integer, List<DetailItemDto>> mapCategoryOfItems = this.queryRepository
				.findAll(user.companyCode(), user.personId(), PAY_BONUS_ATR, processingYM).stream()
				.collect(Collectors.groupingBy(x -> x.getCategoryAtr()));

		// get 明細書マスタカテゴリ
		List<LayoutMasterCategory> masterCategories = this.layoutMasterCategoryRepository
				.getCategories(user.companyCode(), stmtCode, startYM);

		List<LayoutMasterCategoryDto> cResult = new ArrayList<>();
		for (Map.Entry<Integer, List<DetailItemDto>> categoryItem : mapCategoryOfItems.entrySet()) {
			LayoutMasterCategory category = masterCategories.stream()
					.filter(x -> x.getCtAtr().value == categoryItem.getKey()).findFirst().get();

			cResult.add(LayoutMasterCategoryDto.fromDomain(categoryItem.getKey(), category.getCtgPos().v(),
					categoryItem.getValue()));
		}

		return cResult;

	}

}
