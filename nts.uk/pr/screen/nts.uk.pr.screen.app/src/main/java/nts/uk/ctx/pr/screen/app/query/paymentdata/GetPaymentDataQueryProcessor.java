package nts.uk.ctx.pr.screen.app.query.paymentdata;

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

		// get 処理年月マスタ
		PaymentDateProcessingMaster payDateMaster = this.payDateMasterRepository.find(companyCode, PAY_BONUS_ATR, PROCESSING_NO).get();
		int processingYM = payDateMaster.getCurrentProcessingYm().v();

		// get stmtCode
		String stmtCode = this.personalPSRepository.find(companyCode, query.getPersonId(),
				payDateMaster.getCurrentProcessingYm().v())
				.map(o -> o.getPaymentDetailCode().v())
				.orElseGet(() -> {
					return this.companyAllotSettingRepository.find(companyCode, processingYM).get().getPaymentDetailCode().v();
				});

		// get 明細書マスタ
		LayoutMaster layout = this.layoutMasterRepository.getLayout(companyCode, processingYM, stmtCode)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("対象データがありません。")));
		int startYM = layout.getStartYM().v();

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
		
		List<DetailItemDto> detailItems = getDetailItems(query, result, companyCode, payDateMaster, layout, optPHeader);
		
		result.setCategories(mergeDataToLayout(mCates, lines, lDetails, detailItems));
		return result;
	}


	private List<DetailItemDto> getDetailItems(PaymentDataQuery query, PaymentDataResult result, String companyCode,
			PaymentDateProcessingMaster payDateMaster, LayoutMaster layout, Optional<Payment> optPHeader) {
		
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
			
			return this.queryRepository
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
			
			return new ArrayList<>();
		}

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
	private static List<LayoutMasterCategoryDto> mergeDataToLayout(
			List<LayoutMasterCategory> mCates, List<LayoutMasterLine> lines,
			List<LayoutMasterDetail> details, List<DetailItemDto> datas) {
		
		List<LayoutMasterCategoryDto> categories = new ArrayList<>();
		
		for (LayoutMasterCategory category : mCates) {
			int ctAtr = category.getCtAtr().value;
			List<DetailItemDto> items = new ArrayList<>();
			for (LayoutMasterDetail item : details) {
				DetailItemDto detailItem = createDetailItemDto(lines, datas, ctAtr, item);
				items.add(detailItem);
			}
			
			categories.add(LayoutMasterCategoryDto.fromDomain(ctAtr, category.getCtgPos().v(), items));
		}
		
		return categories;
	}


	private static DetailItemDto createDetailItemDto(List<LayoutMasterLine> lines, List<DetailItemDto> datas, int ctAtr,
			LayoutMasterDetail item) {
		LayoutMasterLine mLine = lines.stream()
				.filter(x -> x.getCategoryAtr().value == ctAtr && x.getAutoLineId().equals(item.getAutoLineId()))
				.findFirst().get();
		
		Double value = datas.stream()
				.filter(x-> x.getCategoryAtr() == ctAtr && x.getItemCode().equals(item.getItemCode().v()))
				.findFirst()
				.map(d -> d.getValue())
				.orElse(null);
		
		DetailItemDto detailItem = DetailItemDto.fromDomain(
				ctAtr, item.getItemCode().v(),
				item.getItemAbName().v(), 
				value,
				DetailItemPositionDto.fromDomain(
						mLine.getLinePosition().v(), 
						item.getItemPosColumn().v()),
				value != null
				);
		return detailItem;
	}
}
