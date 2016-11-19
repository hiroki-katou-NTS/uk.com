package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

/**
 * GetPaymentDataQueryProcessor
 * 
 * @author vunv
 *
 */
@RequestScoped
public class GetPaymentDataQueryProcessor {

	private static final int PAY_BONUS_ATR = 0;
	
	private static final int PROCESSING_NO = 0;

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

		// get 処理年月マスタ
		PaymentDateProcessingMaster payDateMaster = this.payDateMasterRepository.find(companyCode, PAY_BONUS_ATR, PROCESSING_NO).get();
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

		// get 明細書マスタカテゴリ
		List<LayoutMasterCategory> mCates = this.layoutMasterCategoryRepository.getCategories(companyCode,
				layout.getStmtCode().v(), startYM);

		// get 明細書マスタ行
		List<LayoutMasterLine> lines = this.layoutMasterLineRepository.getLines(companyCode, stmtCode, startYM);

		// get 明細書マスタ明細
		List<LayoutMasterDetail> lDetails = this.layoutMasterDetailRepository.getDetails(companyCode, stmtCode,
				startYM);

		// get header of payment
		Optional<Payment> optPHeader = this.paymentDataRepository.find(companyCode, query.getPersonId(),
				payDateMaster.getProcessingNo().v(), PAY_BONUS_ATR, payDateMaster.getCurrentProcessingYm().v(), 0);
		
		List<DetailItemDto> detailItems = null;
		if (optPHeader.isPresent()) {
			Payment payment = optPHeader.get();
			result.setPaymenHeader(new PaymentDataHeaderDto
					(
						payment.getDependentNumber().v(),
					    payment.getSpecificationCode().v(),
					    layout.getStmtName().v(), 
					    payment.getMakeMethodFlag().value,
					    query.getEmployeeCode(), 
					    payment.getComment().v())
					);
			
			detailItems = this.queryRepository
			.findAll(companyCode, query.getPersonId(), PAY_BONUS_ATR, payDateMaster.getCurrentProcessingYm().v());

		} else { 
			result.setPaymenHeader(new PaymentDataHeaderDto
			(
				null,
				layout.getStmtCode().v(),
			    layout.getStmtName().v(), 
			    null,
			    query.getEmployeeCode(), 
			    null
			));
		}
		
		result.setCategories(this.mergeDataToLayout(mCates, lines, lDetails, detailItems));
		return result;
	}

	
	/**
	 * merge data to layout master
	 * 
	 * @param mCates
	 * @param lines
	 * @param details
	 * @param datas
	 * @return
	 */
	private List<LayoutMasterCategoryDto> mergeDataToLayout(
			List<LayoutMasterCategory> mCates, List<LayoutMasterLine> lines, List<LayoutMasterDetail> details, List<DetailItemDto> datas) {
		List<LayoutMasterCategoryDto> categories = new ArrayList<>();
		for (LayoutMasterCategory category : mCates) {
			int ctAtr = category.getCtAtr().value;
			List<DetailItemDto> items = new ArrayList<>();
			for (LayoutMasterDetail item : details) {
				LayoutMasterLine mLine = lines.stream().filter(x -> x.getCategoryAtr().value == ctAtr
						&& x.getAutoLineId().equals(item.getAutoLineId())).findFirst().get();
				Optional<DetailItemDto> optItemDto = datas.stream().filter(x-> x.getCategoryAtr() == ctAtr && x.getItemCode().equals(item.getItemCode().v())).findFirst();
				
				Double value = null;
				if (optItemDto.isPresent()) {
					value = optItemDto.get().getValue();
				}
				
				DetailItemDto detailItem = DetailItemDto.fromDomain(
						ctAtr, item.getItemCode().v(),
						item.getItemAbName().v(), 
						value,
						DetailItemPositionDto
								.fromDomain(
										mLine.getLinePosition().v(), 
										item.getItemPosColumn().v()),
						Objects.isNull(value)? false: true		
						);
				
				items.add(detailItem);
			}
			categories.add(LayoutMasterCategoryDto.fromDomain(ctAtr, category.getCtgPos().v(), items));
		}
		return categories;
	}
}
